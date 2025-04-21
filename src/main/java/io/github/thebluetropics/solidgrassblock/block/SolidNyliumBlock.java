package io.github.thebluetropics.solidgrassblock.block;

import io.github.thebluetropics.solidgrassblock.helper.BlockStateHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NetherConfiguredFeatures;

import java.util.Objects;

public class SolidNyliumBlock extends Block implements Fertilizable {
	public SolidNyliumBlock(Settings settings) {
		super(settings);
	}

	@Override
	public FertilizableType getFertilizableType() {
		return FertilizableType.NEIGHBOR_SPREADER;
	}

	@Override
	public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
		return world.getBlockState(pos.up()).isAir();
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos blockPos, BlockState blockState) {
		BlockPos upperBlockPos = blockPos.up();

		var chunkGenerator = world.getChunkManager().getChunkGenerator();
		var configuredFeatureRegistry = world.getRegistryManager().get(RegistryKeys.CONFIGURED_FEATURE);

		if (BlockStateHelper.isOf(blockState, Blocks.CRIMSON_NYLIUM, ModBlocks.SOLID_CRIMSON_NYLIUM)) {
			this.generate(configuredFeatureRegistry, NetherConfiguredFeatures.CRIMSON_FOREST_VEGETATION_BONEMEAL, world, chunkGenerator, random, upperBlockPos);
		}

		if (BlockStateHelper.isOf(blockState, Blocks.WARPED_NYLIUM, ModBlocks.SOLID_WARPED_NYLIUM)) {
			this.generate(configuredFeatureRegistry, NetherConfiguredFeatures.WARPED_FOREST_VEGETATION_BONEMEAL, world, chunkGenerator, random, upperBlockPos);
			this.generate(configuredFeatureRegistry, NetherConfiguredFeatures.NETHER_SPROUTS_BONEMEAL, world, chunkGenerator, random, upperBlockPos);

			if (Objects.equals(random.nextInt(8), 0)) {
				this.generate(configuredFeatureRegistry, NetherConfiguredFeatures.TWISTING_VINES_BONEMEAL, world, chunkGenerator, random, upperBlockPos);
			}
		}
	}

	private void generate(Registry<ConfiguredFeature<?, ?>> registry, RegistryKey<ConfiguredFeature<?, ?>> key, ServerWorld world, ChunkGenerator chunkGenerator, Random random, BlockPos pos) {
		registry.getEntry(key).ifPresent(entry -> entry.value().generate(world, chunkGenerator, random, pos));
	}

	@Override
	protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!canSolidNyliumSurvive(state, world, pos)) {
			world.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
		}
	}

	/** Checks whether a nylium block can survive */
	private static boolean canNyliumSurvive(BlockState state, WorldView world, BlockPos pos) {
		var upperPos = pos.up();
		var upperState = world.getBlockState(upperPos);

		int realisticOpacity = ChunkLightProvider.getRealisticOpacity(
			world,
			state,
			pos,
			upperState,
			upperPos,
			Direction.UP,
			upperState.getOpacity(world, upperPos)
		);

		return realisticOpacity < world.getMaxLightLevel();
	}

	/**  Checks whether a solid nylium block can survive */
	private static boolean canSolidNyliumSurvive(BlockState state, WorldView world, BlockPos pos) {
		for (Direction direction : Direction.values()) {
			var checkPos = pos.offset(direction);
			var checkState = world.getBlockState(pos.offset(direction));

			int realisticOpacity = ChunkLightProvider.getRealisticOpacity(
				world,
				state,
				pos,
				checkState,
				checkPos,
				direction,
				checkState.getOpacity(world, checkPos)
			);

			if (realisticOpacity < world.getMaxLightLevel() && checkState.getFluidState().getLevel() < 8) {
				return true;
			}
		}

		return false;
	}
}
