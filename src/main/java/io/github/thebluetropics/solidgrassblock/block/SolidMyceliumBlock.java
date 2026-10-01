package io.github.thebluetropics.solidgrassblock.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SnowyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;

public class SolidMyceliumBlock extends Block {
	public SolidMyceliumBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(10) == 0) {
			level.addParticle(
				ParticleTypes.MYCELIUM,
				(double)pos.getX() + random.nextDouble(),
				(double)pos.getY() + 1.1,
				(double)pos.getZ() + random.nextDouble(),
				0.0,
				0.0,
				0.0
			);
		}
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		// Turns into dirt
		if (!canSolidMyceliumSurvive(state, level, pos)) {
			level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
			return;
		}

		if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
			var spreadBlockState = Blocks.MYCELIUM.defaultBlockState();
			for(int i = 0; i < 4; ++i) {
				var testPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
				if (level.getBlockState(testPos).is(Blocks.DIRT) && canSpread(spreadBlockState, level, testPos)) {
					level.setBlockAndUpdate(
						testPos,
						spreadBlockState.setValue(SnowyBlock.SNOWY, level.getBlockState(testPos.above()).is(BlockTags.SNOW))
					);
				}
			}
		}
	}

	public static boolean canMyceliumSurvive(final BlockState state, final LevelReader level, final BlockPos pos) {
		var upperState = level.getBlockState(pos.above());

		if (upperState.is(Blocks.SNOW) && upperState.getValue(SnowLayerBlock.LAYERS) == 1) {
			return true;
		}

		if (upperState.getFluidState().isFull()) {
			return false;
		}

		return LightEngine.getLightBlockInto(state, upperState, Direction.UP, upperState.getLightDampening()) < 15;
	}

	public static boolean canSolidMyceliumSurvive(final BlockState state, final LevelReader level, final BlockPos pos) {
		for (Direction dir : Direction.values()) {
			if (!level.getBlockState(pos.relative(dir)).isSolid()) {
				return true;
			}
		}

		return false;
	}

	public static boolean canSpread(final BlockState blockState, final LevelReader world, final BlockPos pos) {
		return canMyceliumSurvive(blockState, world, pos) && !world.getFluidState(pos.above()).is(FluidTags.WATER);
	}
}
