package io.github.thebluetropics.solidgrassblock.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class SolidNyliumBlock extends Block implements BonemealableBlock {
	public SolidNyliumBlock(Properties properties) {
		super(properties);
	}

	@Override
	public Type getType() {
		return Type.NEIGHBOR_SPREADER;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return level.getBlockState(pos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		var upperPos = pos.above();
		var generator = level.getChunkSource().getGenerator();
		var configuredFeatures = level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE);

		if (state.is(Blocks.CRIMSON_NYLIUM) || state.is(ModBlocks.SOLID_CRIMSON_NYLIUM)) {
			this.place(configuredFeatures, NetherFeatures.CRIMSON_FOREST_VEGETATION_BONEMEAL, level, generator, random, upperPos);
		}

		if (state.is(Blocks.WARPED_NYLIUM) || state.is(ModBlocks.SOLID_WARPED_NYLIUM)) {
			this.place(configuredFeatures, NetherFeatures.WARPED_FOREST_VEGETATION_BONEMEAL, level, generator, random, upperPos);
			this.place(configuredFeatures, NetherFeatures.NETHER_SPROUTS_BONEMEAL, level, generator, random, upperPos);
			if (random.nextInt(8) == 0) {
				this.place(configuredFeatures, NetherFeatures.TWISTING_VINES_BONEMEAL, level, generator, random, upperPos);
			}
		}
	}

	private void place(
		final Registry<ConfiguredFeature<?, ?>> configuredFeatures,
		final ResourceKey<ConfiguredFeature<?, ?>> id,
		final ServerLevel level,
		final ChunkGenerator generator,
		final RandomSource random,
		final BlockPos pos
	) {
		if (level.isInsideBuildHeight(pos)) {
			configuredFeatures.get(id).ifPresent((feature) -> feature.value().place(level, generator, random, pos));
		}
	}
}
