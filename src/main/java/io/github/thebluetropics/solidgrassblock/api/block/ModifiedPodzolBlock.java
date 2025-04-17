package io.github.thebluetropics.solidgrassblock.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

/**
 * Modified implementation of vanilla podzol block.
 */
public class ModifiedPodzolBlock extends Block {
	/**
	 * Set to <code>true</code> when there's a snow block on top of this block.
	 */
	public static final BooleanProperty SNOWY = BooleanProperty.of("snowy");

	public ModifiedPodzolBlock(Settings settings) {
		super(settings);

		// Optionally set default `SNOWY` state.
		if (this.stateManager.getProperties().contains(SNOWY)) {
			this.setDefaultState(this.getDefaultState().with(SNOWY, false));
		}
	}

	public BlockState getPathState() {
		return Blocks.DIRT_PATH.getDefaultState();
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(SNOWY);
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
