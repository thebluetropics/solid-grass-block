package io.github.thebluetropics.solidgrassblock.block;

import io.github.thebluetropics.solidgrassblock.api.block.ModifiedGrassBlock;
import io.github.thebluetropics.solidgrassblock.component.ModDataComponents;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import org.jetbrains.annotations.Nullable;

public class SolidGrassBlock extends ModifiedGrassBlock {
  /**
   * Indicates whether a <b>Solid Grass Block</b> has been eaten by mobs.
   */
  public static final BooleanProperty EATEN = BooleanProperty.of("eaten");

  public SolidGrassBlock(Settings settings) {
    super(settings);

    this.setDefaultState(
      this.getDefaultState()
        .with(EATEN, false)
    );
  }

  @Override
  public BlockState getEatenState() {
    return this.getDefaultState().with(EATEN, true);
  }

  @Override
  public BlockState getPathState() {
    return ModBlocks.SOLID_DIRT_PATH.getDefaultState();
  }

  @Override
  public BlockState getSpreadState() {
    return Blocks.GRASS_BLOCK.getDefaultState();
  }

  @Override
  public boolean canSurvive(BlockState state, WorldView world, BlockPos pos) {
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

  @Override
  protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    super.randomTick(state, world, pos, random);

    if (world.getLightLevel(pos.up()) > 8) {
      if (state.get(EATEN)) {
        world.setBlockState(pos, state.with(EATEN, false), Block.NOTIFY_LISTENERS);
      }
    }
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(EATEN);
  }

  @Override
  protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    return state;
  }

  @Override
  public @Nullable BlockState getPlacementState(ItemPlacementContext context) {
    return getDefaultState().with(EATEN, context.getStack().get(ModDataComponents.EATEN));
  }

  @Override
  public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
    var stack = ModItems.SOLID_GRASS_BLOCK.getDefaultStack();
    stack.set(ModDataComponents.EATEN, state.get(EATEN));

    return stack;
  }
}
