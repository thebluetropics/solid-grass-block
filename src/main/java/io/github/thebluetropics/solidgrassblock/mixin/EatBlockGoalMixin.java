package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.block.SolidGrassBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(EatBlockGoal.class)
public class EatBlockGoalMixin {
	private static final Predicate<BlockState> IS_EDIBLE = (state) -> state.is(BlockTags.EDIBLE_FOR_SHEEP);

	@Shadow
	private Mob mob;

	@Shadow
	private Level level;

	// Allow mobs to start eating Solid Grass Block
	@Inject(
		method = "canUse",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Mob;blockPosition()Lnet/minecraft/core/BlockPos;"
		),
		cancellable = true
	)
	private void canUse(CallbackInfoReturnable<Boolean> info) {
		final var pos = this.mob.blockPosition();
		if (!IS_EDIBLE.test(this.level.getBlockState(pos))) {
			var lowerState = this.level.getBlockState(pos.below());
			if (lowerState.is(ModBlocks.SOLID_GRASS_BLOCK) && !lowerState.getValue(SolidGrassBlock.EATEN)) {
				info.setReturnValue(true);
			}
		}
	}

	// Set `eaten` block state to `true` after being eaten by mobs
	@Inject(
		method = "tick()V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/BlockPos;below()Lnet/minecraft/core/BlockPos;"
		),
		cancellable = true
	)
	private void tick(CallbackInfo info) {
		final var blockPos = this.mob.blockPosition().below();
		final var blockState = this.level.getBlockState(blockPos);

		if (blockState.is(ModBlocks.SOLID_GRASS_BLOCK)) {
			if (((ServerLevel) this.level).getGameRules().get(GameRules.MOB_GRIEFING)) {
        this.level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, Block.getId(ModBlocks.SOLID_GRASS_BLOCK.defaultBlockState()));
				this.level.setBlock(blockPos, blockState.setValue(SolidGrassBlock.EATEN, true), 2);
			}
			this.mob.ate();
			info.cancel();
		}
	}
}
