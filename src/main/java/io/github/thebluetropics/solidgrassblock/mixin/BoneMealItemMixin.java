package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.block.SolidGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
	private static Vec3 getRandomSpeedRanges(final RandomSource random) {
		return new Vec3(Mth.nextDouble(random, -0.5, 0.5), Mth.nextDouble(random, -0.5, 0.5), Mth.nextDouble(random, -0.5, 0.5));
	}

	@Inject(at = @At("HEAD"), method = "useOn", cancellable = true)
	private void useOn(final UseOnContext context, CallbackInfoReturnable<InteractionResult> info) {
		var level = context.getLevel();
		var pos = context.getClickedPos();
		var state = level.getBlockState(pos);

		// Turn dirt block into grass block
		if (context.getClickedFace().equals(Direction.UP) && state.is(Blocks.DIRT)) {
			level.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
			ParticleUtils.spawnParticlesOnBlockFace(level, pos, ParticleTypes.HAPPY_VILLAGER, ConstantInt.of(15), Direction.UP, () -> getRandomSpeedRanges(level.getRandom()), 0.55);
			level.playLocalSound(pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			context.getItemInHand().shrink(1);
			info.setReturnValue(InteractionResult.SUCCESS);
		}

		// Turn grass block into solid grass block
		if (context.getClickedFace().getAxis().isHorizontal() && state.is(Blocks.GRASS_BLOCK)) {
			level.setBlockAndUpdate(pos, ModBlocks.SOLID_GRASS_BLOCK.defaultBlockState());
			ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.HAPPY_VILLAGER, ConstantInt.of(15));
			level.playLocalSound(pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			context.getItemInHand().shrink(1);
			info.setReturnValue(InteractionResult.SUCCESS);
		}

		// Turn podzol into solid podzol
		if (context.getClickedFace().getAxis().isHorizontal() && state.is(Blocks.PODZOL)) {
			level.setBlockAndUpdate(pos, ModBlocks.SOLID_PODZOL.defaultBlockState());
			ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.HAPPY_VILLAGER, ConstantInt.of(15));
			level.playLocalSound(pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			context.getItemInHand().shrink(1);
			info.setReturnValue(InteractionResult.SUCCESS);
		}

		// Turn mycelium into solid mycelium
		if (context.getClickedFace().getAxis().isHorizontal() && state.is(Blocks.MYCELIUM)) {
			level.setBlockAndUpdate(pos, ModBlocks.SOLID_MYCELIUM.defaultBlockState());
			ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.HAPPY_VILLAGER, ConstantInt.of(15));
			level.playLocalSound(pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			context.getItemInHand().shrink(1);
			info.setReturnValue(InteractionResult.SUCCESS);
		}

		// Turn crimson nylium into solid crimson nylium
		if (context.getClickedFace().getAxis().isHorizontal() && state.is(Blocks.CRIMSON_NYLIUM)) {
			level.setBlockAndUpdate(pos, ModBlocks.SOLID_CRIMSON_NYLIUM.defaultBlockState());
			ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.HAPPY_VILLAGER, ConstantInt.of(15));
			level.playLocalSound(pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			context.getItemInHand().shrink(1);
			info.setReturnValue(InteractionResult.SUCCESS);
		}

		// Turn warped nylium into solid crimson warped
		if (context.getClickedFace().getAxis().isHorizontal() && state.is(Blocks.WARPED_NYLIUM)) {
			level.setBlockAndUpdate(pos, ModBlocks.SOLID_WARPED_NYLIUM.defaultBlockState());
			ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.HAPPY_VILLAGER, ConstantInt.of(15));
			level.playLocalSound(pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			context.getItemInHand().shrink(1);
			info.setReturnValue(InteractionResult.SUCCESS);
		}

		// Regrow eaten solid grass block
		if (context.getClickedFace() == Direction.UP && state.is(ModBlocks.SOLID_GRASS_BLOCK)) {
			if (state.getValue(SolidGrassBlock.EATEN)) {
				level.setBlockAndUpdate(pos, state.setValue(SolidGrassBlock.EATEN, false));
				ParticleUtils.spawnParticlesOnBlockFace(level, pos, ParticleTypes.HAPPY_VILLAGER, ConstantInt.of(15), Direction.UP, () -> getRandomSpeedRanges(level.getRandom()), 0.55);
				level.playLocalSound(pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
				context.getItemInHand().shrink(1);
				info.setReturnValue(InteractionResult.SUCCESS);
			}
		}
	}
}
