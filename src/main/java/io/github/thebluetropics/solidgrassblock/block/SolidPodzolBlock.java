package io.github.thebluetropics.solidgrassblock.block;

import io.github.thebluetropics.solidgrassblock.api.block.ModifiedPodzolBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SolidPodzolBlock extends ModifiedPodzolBlock {
  public SolidPodzolBlock(Settings settings) {
    super(settings);
  }

  @Override
  public BlockState getPathState() {
    return ModBlocks.SOLID_DIRT_PATH.getDefaultState();
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    /* ... */
  }

  @Override
  protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    return state;
  }

  @Nullable
  @Override
  public BlockState getPlacementState(ItemPlacementContext context) {
    return this.getDefaultState();
  }
}
