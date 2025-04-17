package io.github.thebluetropics.solidgrassblock.api.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
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
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Modified implementation of <code>GrassBlock</code> class. Extend this class instead of <code>GrassBlock</code> to
 * achieve full customization.
 */
public class ModifiedGrassBlock extends Block implements Fertilizable {
	public static final BooleanProperty SNOWY = BooleanProperty.of("snowy");

	public ModifiedGrassBlock(Settings settings) {
		super(settings);

		// Optionally set default `SNOWY` state.
		if (this.stateManager.getProperties().contains(SNOWY)) {
			this.setDefaultState(this.getDefaultState().with(SNOWY, false));
		}
	}

	public BlockState getEatenState() {
		return Blocks.DIRT.getDefaultState();
	}

	public BlockState getTilledState() {
		return Blocks.FARMLAND.getDefaultState();
	}

	public BlockState getPathState() {
		return Blocks.FARMLAND.getDefaultState();
	}

	public BlockState getDieState() {
		return Blocks.FARMLAND.getDefaultState();
	}

	public BlockState getSpreadState() {
		return this.getDefaultState();
	}

	public boolean canFertilizerGrowPlants(BlockState state) {
		return true;
	}

	public boolean canAnimalPathfindingFavor(BlockState state, WorldView world, BlockPos pos) {
		return true;
	}

	public boolean canMobsEat(BlockState state) {
		return true;
	}

	public boolean canOcelotSpawn(BlockState state, WorldView world, BlockPos pos) {
		return true;
	}

	public boolean canSpread(BlockState state, WorldView world, BlockPos pos) {
		return this.canSurvive(state, world, pos) && !world.getFluidState(pos.up()).isIn(FluidTags.WATER);
	}

	public boolean canSurvive(BlockState state, WorldView world, BlockPos pos) {
		var upperPos = pos.up();
		var upperState = world.getBlockState(upperPos);

		if (upperState.isOf(Blocks.SNOW) && Objects.equals(upperState.get(SnowBlock.LAYERS), 1)) {
			return true;
		}

		if (Objects.equals(upperState.getFluidState().getLevel(), 8)) {
			return false;
		}

		int lightLevel = ChunkLightProvider.getRealisticOpacity(
			world,
			state,
			pos,
			upperState,
			upperPos,
			Direction.UP,
			upperState.getOpacity(world, upperPos)
		);

		return lightLevel < world.getMaxLightLevel();
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(SNOWY);
	}

	@Override
	protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		var upperBlockPos = pos.up();

		if (!this.canSurvive(state, world, pos)) {
			world.setBlockState(pos, this.getDieState(), Block.NOTIFY_ALL);

			return;
		}

		if (world.getLightLevel(upperBlockPos.up()) >= 9) {
			this.spread(state, world, pos, random);
		}
	}

	/**
	 * Spreading logic.
	 */
	public void spread(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		var spreadState = this.getSpreadState();

		for (int i = 0; i < 4; i++) {
			var targetPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);

			if (world.getBlockState(targetPos).isOf(Blocks.DIRT) && this.canSpread(spreadState, world, targetPos)) {
				world.setBlockState(
					targetPos,
					spreadState.with(SnowyBlock.SNOWY, world.getBlockState(targetPos.up()).isOf(Blocks.SNOW))
				);
			}
		}
	}

	@Override
	public FertilizableType getFertilizableType() {
		return Fertilizable.FertilizableType.NEIGHBOR_SPREADER;
	}

	@Override
	public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
		return ((Fertilizable) Blocks.GRASS_BLOCK).isFertilizable(world, pos, state);
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	/// Note: this method calls the vanilla implementation and should not be changed unless you know what you are doing.
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		((Fertilizable) Blocks.GRASS_BLOCK).grow(world, random, pos, state);
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (direction.equals(Direction.UP)) {
			return state.with(SNOWY, neighborState.isIn(BlockTags.SNOW));
		}

		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		BlockState upperState = context.getWorld().getBlockState(context.getBlockPos().up());

		return this.getDefaultState().with(SNOWY, upperState.isIn(BlockTags.SNOW));
	}
}
