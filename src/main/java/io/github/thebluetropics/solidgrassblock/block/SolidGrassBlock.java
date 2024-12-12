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
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

import java.util.Objects;

public class SolidGrassBlock extends Block implements Fertilizable {
  public static final BooleanProperty EATEN = BooleanProperty.of("eaten");

  public SolidGrassBlock(Settings settings) {
    super(settings);

    setDefaultState(getDefaultState().with(EATEN, false));
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(EATEN);
  }

  @Override
  protected void randomTick(BlockState blockState, ServerWorld world, BlockPos blockPos, Random random) {
    var upperBlockPos = blockPos.up();

    if (world.getLightLevel(upperBlockPos.up()) >= 9) {
      if (blockState.get(EATEN)) {
        world.setBlockState(blockPos, blockState.with(EATEN, false), Block.NOTIFY_LISTENERS);
      }

      var spreadBlockState = Blocks.GRASS_BLOCK.getDefaultState();

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

  @Override
  public boolean isFertilizable(WorldView world, BlockPos blockPos, BlockState blockState) {
    return world.getBlockState(blockPos.up()).isAir();
  }

  @Override
  public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
    return true;
  }

  @Override
  public void grow(ServerWorld world, Random random, BlockPos blockPos, BlockState blockState) {
    ((Fertilizable) Blocks.GRASS_BLOCK).grow(world, random, blockPos, blockState);
  }

  /// Force solid grass block to immediately turn into dirt when it is cannot survive
  @Override
  protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    if (!canSolidGrassBlockSurvive(state, world, pos)) {
      return Blocks.DIRT.getDefaultState();
    }

    return state;
  }

  @Override
  public FertilizableType getFertilizableType() {
    return Fertilizable.FertilizableType.NEIGHBOR_SPREADER;
  }

  /** Checks whether a regular <b>Grass Block</b> can survive. */
  public static boolean canGrassBlockSurvive(BlockState blockState, WorldView world, BlockPos blockPos) {
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

  /** Checks whether a <b>Solid Grass Block</b> can survive. */
  @SuppressWarnings("deprecation")
  public static boolean canSolidGrassBlockSurvive(BlockState blockState, WorldView world, BlockPos blockPos) {
    for (Direction direction : Direction.values()) {
      var checkBlockState = world.getBlockState(blockPos.offset(direction));

      if (!checkBlockState.isSolid()) {
        return true;
      }
    }

    return false;
  }

  public static boolean canSpread(BlockState blockState, WorldView world, BlockPos blockPos) {
    return canGrassBlockSurvive(blockState, world, blockPos) && !world.getFluidState(blockPos.up()).isIn(FluidTags.WATER);
  }
}
