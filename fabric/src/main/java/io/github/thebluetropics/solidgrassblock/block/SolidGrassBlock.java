package io.github.thebluetropics.solidgrassblock.block;

import io.github.thebluetropics.solidgrassblock.component.ModDataComponents;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

import java.util.Objects;

public class SolidGrassBlock extends GrassBlock {
  public static final BooleanProperty EATEN = BooleanProperty.of("eaten");

  public SolidGrassBlock(Settings settings) {
    super(settings);

    setDefaultState(getDefaultState().with(EATEN, false));
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder);

    builder.add(EATEN);
  }

  @Override
  protected void randomTick(BlockState blockState, ServerWorld world, BlockPos blockPos, Random random) {
    var upperBlockPos = blockPos.up();
    var upperBlockState = world.getBlockState(upperBlockPos);

    if (world.getFluidState(upperBlockPos).isIn(FluidTags.WATER)) {
      return;
    }

    if (Objects.equals(upperBlockState.getFluidState().getLevel(), 8)) {
      return;
    }

    int realisticOpacity = ChunkLightProvider.getRealisticOpacity(
      world,
      blockState,
      blockPos,
      upperBlockState,
      upperBlockPos,
      Direction.UP,
      upperBlockState.getOpacity(world, upperBlockPos)
    );

    if (realisticOpacity < world.getMaxLightLevel()) {
      if (blockState.get(EATEN)) {
        world.setBlockState(blockPos, blockState.with(EATEN, false), Block.NOTIFY_LISTENERS);
      }
    }
  }

  @Override
  public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
    var stack = ModItems.SOLID_GRASS_BLOCK.getDefaultStack();
    stack.set(ModDataComponents.EATEN, state.get(EATEN));

    return stack;
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext context) {
    return getDefaultState().with(EATEN, context.getStack().get(ModDataComponents.EATEN));
  }
}
