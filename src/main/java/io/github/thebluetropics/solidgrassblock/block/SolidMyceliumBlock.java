package io.github.thebluetropics.solidgrassblock.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

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

  @Override
  protected void randomTick(BlockState blockState, ServerWorld world, BlockPos blockPos, Random random) {
    var upperBlockPos = blockPos.up();

    if (!canSolidMyceliumSurvive(blockState, world, blockPos)) {
      world.setBlockState(blockPos, Blocks.DIRT.getDefaultState(), Block.NOTIFY_ALL);
      return;
    }

    if (world.getLightLevel(upperBlockPos.up()) >= 9) {
      var spreadBlockState = Blocks.MYCELIUM.getDefaultState();

      for (int i = 0; i < 4; i++) {
        var targetBlockPos = blockPos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);

        if (world.getBlockState(targetBlockPos).isOf(Blocks.DIRT) && canSpread(spreadBlockState, world, targetBlockPos)) {
          world.setBlockState(
            targetBlockPos,
            spreadBlockState.with(SnowyBlock.SNOWY, world.getBlockState(targetBlockPos.up()).isOf(Blocks.SNOW))
          );
        }
      }
    }
  }

  /** Checks whether a regular <b>Mycelium Block</b> can survive. */
  public static boolean canMyceliumSurvive(BlockState blockState, WorldView world, BlockPos blockPos) {
    var upperBlockPos = blockPos.up();
    var upperBlockState = world.getBlockState(upperBlockPos);

    if (Objects.equals(upperBlockState.getFluidState().getLevel(), 8)) {
      return false;
    }

    int lightLevel = ChunkLightProvider.getRealisticOpacity(
      world,
      blockState,
      blockPos,
      upperBlockState,
      upperBlockPos,
      Direction.UP,
      upperBlockState.getOpacity(world, upperBlockPos)
    );

    return lightLevel < world.getMaxLightLevel();
  }

  /** Checks whether a <b>Solid Mycelium Block</b> can survive. */
  @SuppressWarnings("deprecation")
  public static boolean canSolidMyceliumSurvive(BlockState state, WorldView world, BlockPos pos) {
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

  public static boolean canSpread(BlockState blockState, WorldView world, BlockPos blockPos) {
    return canMyceliumSurvive(blockState, world, blockPos) && !world.getFluidState(blockPos.up()).isIn(FluidTags.WATER);
  }
}
