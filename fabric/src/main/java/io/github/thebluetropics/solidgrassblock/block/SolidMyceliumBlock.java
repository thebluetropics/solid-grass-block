package io.github.thebluetropics.solidgrassblock.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Objects;

public class SolidMyceliumBlock extends Block {
  public SolidMyceliumBlock(Settings settings) {
    super(settings);
  }

  @Override
  public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
    if (Objects.equals(random.nextInt(10), 0)) {
      world.addParticle(
        ParticleTypes.MYCELIUM,
        (double) pos.getX() + random.nextDouble(),
        (double) pos.getY() + 1.1,
        (double) pos.getZ() + random.nextDouble(),
        0.0,
        0.0,
        0.0
      );
    }
  }
}
