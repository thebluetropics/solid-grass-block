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
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

import java.util.Objects;

public class SolidGrassBlock extends Block implements Fertilizable {
	/** Set to <code>true</code> if a solid grass block is eaten (e.g by sheep) */
	public static final BooleanProperty EATEN = BooleanProperty.of("eaten");

	public SolidGrassBlock(Settings settings) {
		super(settings);

		this.setDefaultState(this.getDefaultState().with(EATEN, false));
	}

	@Override
	protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.randomTick(state, world, pos, random);

		if (world.getLightLevel(pos.up()) > 8) {
			if (state.get(EATEN)) {
				world.setBlockState(pos, state.with(EATEN, false), Block.NOTIFY_LISTENERS);
			}
		}

		var upperBlockPos = pos.up();

		if (!canSolidGrassBlockSurvive(state, world, pos)) {
			world.setBlockState(pos, Blocks.DIRT.getDefaultState(), Block.NOTIFY_ALL);

			return;
		}

		if (world.getLightLevel(upperBlockPos.up()) >= 9) {
			spread(state, world, pos, random);
		}
	}

	public static void spread(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		var spreadState = Blocks.GRASS_BLOCK.getDefaultState();

		for (int i = 0; i < 4; i++) {
			var targetPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);

			if (world.getBlockState(targetPos).isOf(Blocks.DIRT) && canSpread(spreadState, world, targetPos)) {
				world.setBlockState(
					targetPos,
					spreadState.with(SnowyBlock.SNOWY, world.getBlockState(targetPos.up()).isOf(Blocks.SNOW))
				);
			}
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(EATEN);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		return getDefaultState().with(EATEN, context.getStack().get(ModDataComponents.EATEN));
	}

	@Override
	public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
		var stack = ModItems.SOLID_GRASS_BLOCK.getDefaultStack();
		stack.set(ModDataComponents.EATEN, state.get(EATEN));

		return stack;
	}

	@Override
	public FertilizableType getFertilizableType() {
		return Fertilizable.FertilizableType.NEIGHBOR_SPREADER;
	}

	@Override
	public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		((Fertilizable) Blocks.GRASS_BLOCK).grow(world, random, pos, state);
	}

	/** Checks whether a regular grass block can survive */
	public static boolean canGrassBlockSurvive(BlockState blockState, WorldView world, BlockPos blockPos) {
		var upperBlockPos = blockPos.up();
		var upperBlockState = world.getBlockState(upperBlockPos);

		if (upperBlockState.isOf(Blocks.SNOW) && Objects.equals(upperBlockState.get(SnowBlock.LAYERS), 1)) {
			return true;
		}

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

	/** Checks whether a solid grass block can survive */
	public static boolean canSolidGrassBlockSurvive(BlockState state, WorldView world, BlockPos pos) {
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

	/** Checks whether a grass block can spread */
	public static boolean canSpread(BlockState blockState, WorldView world, BlockPos blockPos) {
		return canGrassBlockSurvive(blockState, world, blockPos) && !world.getFluidState(blockPos.up()).isIn(FluidTags.WATER);
	}
}
