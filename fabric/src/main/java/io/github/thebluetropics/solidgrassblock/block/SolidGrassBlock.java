package io.github.thebluetropics.solidgrassblock.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class SolidGrassBlock extends GrassBlock {
  public SolidGrassBlock(Settings settings) {
    super(settings);
  }

  @Override
  protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) { /* ... */ }
}
