package io.github.thebluetropics.solidgrassblock.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class SolidDirtPathBlock extends Block {
	public static final BooleanProperty FULL_CUBE = BooleanProperty.create("full_cube");
	public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");

	public SolidDirtPathBlock(Properties properties) {
		super(properties);
		registerDefaultState(
			defaultBlockState()
				.setValue(FULL_CUBE, false)
				.setValue(CONNECTED, false)
		);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FULL_CUBE, CONNECTED);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (!state.getValue(FULL_CUBE) && !state.getValue(CONNECTED)) {
			return Block.column(16.0, 0.0, 15.0);
		} else {
			return Shapes.block();
		}
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}

	@Override
	protected boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		var level = context.getLevel();
		var upperPos = context.getClickedPos().above();
		return defaultBlockState()
			.setValue(FULL_CUBE, false)
			.setValue(CONNECTED, level.getBlockState(upperPos).isFaceSturdy(level, upperPos, Direction.DOWN, SupportType.FULL));
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
		return state.setValue(
			CONNECTED,
			level.getBlockState(pos.above()).isFaceSturdy(level, pos.above(), Direction.DOWN, SupportType.FULL)
		);
	}
}
