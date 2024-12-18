package io.github.thebluetropics.solidgrassblock.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SideShapeType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SolidDirtPathBlock extends Block {
  /**
   * Force solid dirt path to be a full cube when set to <code>true</code>.
   */
  public static final BooleanProperty FULL_CUBE = BooleanProperty.of("full_cube");

  /**
   * Set to <code>true</code> when there's a solid block on top of solid dirt path.
   */
  public static final BooleanProperty CONNECTED = BooleanProperty.of("connected");

  public SolidDirtPathBlock(Settings settings) {
    super(settings);

    setDefaultState(
      getDefaultState()
        .with(FULL_CUBE, false)
        .with(CONNECTED, false)
    );
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FULL_CUBE, CONNECTED);
  }

  @Override
  protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return !state.get(FULL_CUBE) && !state.get(CONNECTED) ? Block.createCuboidShape(
      0.0, 0.0, 0.0, 16.0, 15.0, 16.0
    ) : VoxelShapes.fullCube();
  }

  @Override
  protected boolean canPathfindThrough(BlockState state, NavigationType type) {
    return false;
  }

  @Nullable
  @Override
  public BlockState getPlacementState(ItemPlacementContext context) {
    var world = context.getWorld();
    var upperBlockPos = context.getBlockPos().up();

    return getDefaultState()
      .with(FULL_CUBE, false)
      .with(CONNECTED, world.getBlockState(upperBlockPos).isSideSolid(world, upperBlockPos, Direction.DOWN, SideShapeType.FULL));
  }

  @Override
  protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    return state
      .with(
        CONNECTED,
        Objects.equals(direction, Direction.UP) && neighborState.isSideSolid(world, neighborPos, Direction.DOWN, SideShapeType.FULL)
      );
  }

  @Override
  protected boolean hasSidedTransparency(BlockState state) {
    return true;
  }
}
