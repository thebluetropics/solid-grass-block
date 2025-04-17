package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.api.block.ModifiedGrassBlock;
import io.github.thebluetropics.solidgrassblock.tag.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(EatGrassGoal.class)
public class EatGrassGoalMixin {
	private static final Predicate<BlockState> SHORT_GRASS_PREDICATE = BlockStatePredicate.forBlock(Blocks.SHORT_GRASS);

	@Shadow
	private MobEntity mob;

	@Shadow
	private World world;

	/// Allow mobs to start eating custom grass blocks.
	@Inject(
		method = "canStart()Z",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/mob/MobEntity;getBlockPos()Lnet/minecraft/util/math/BlockPos;"
		),
		cancellable = true
	)
	private void canStart(CallbackInfoReturnable<Boolean> info) {
		var blockPos = this.mob.getBlockPos();

		if (!SHORT_GRASS_PREDICATE.test(this.world.getBlockState(blockPos))) {
			var lowerBlockPos = blockPos.down();
			var lowerBlockState = this.world.getBlockState(lowerBlockPos);

			if (lowerBlockState.isIn(ModBlockTags.GRASS_BLOCK)) {
				if (lowerBlockState.getBlock() instanceof ModifiedGrassBlock block) {
					info.setReturnValue(block.canMobsEat(lowerBlockState));
				}

				info.setReturnValue(true);
			}
		}
	}

	/// Set `eaten` block state to `true` after being eaten by mobs.
	@Inject(
		method = "tick()V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/math/BlockPos;down()Lnet/minecraft/util/math/BlockPos;"
		),
		cancellable = true
	)
	private void tick(CallbackInfo info) {
		var blockPos = this.mob.getBlockPos().down();
		var blockState = this.world.getBlockState(blockPos);

		// For custom grass blocks
		if (blockState.isIn(ModBlockTags.GRASS_BLOCK)) {

			if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
				if (blockState.getBlock() instanceof ModifiedGrassBlock block) {
					this.world.setBlockState(blockPos, block.getEatenState(), Block.NOTIFY_LISTENERS);
				} else {
					this.world.setBlockState(blockPos, Blocks.DIRT.getDefaultState(), Block.NOTIFY_LISTENERS);
				}
			}

			this.mob.onEatingGrass();
			info.cancel();
		}
	}
}
