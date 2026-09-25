package io.github.thebluetropics.solidgrassblock.block;

import io.github.thebluetropics.solidgrassblock.component.ModDataComponents;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.lighting.LightEngine;
import org.jspecify.annotations.Nullable;

public class SolidGrassBlock extends Block implements BonemealableBlock {
	public static final BooleanProperty EATEN = BooleanProperty.create("eaten");

	public SolidGrassBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.defaultBlockState().setValue(EATEN, false)
		);
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		// Turns into dirt
		if (!canSolidGrassBlockSurvive(state, level, pos)) {
			level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
			return;
		}

		if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
			if (state.getValue(EATEN))
				level.setBlockAndUpdate(pos, state.setValue(EATEN, false)); // Regrow eaten solid grass block

			var spreadBlockState = Blocks.GRASS_BLOCK.defaultBlockState();
			for(int i = 0; i < 4; ++i) {
				var testPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
				if (level.getBlockState(testPos).is(Blocks.DIRT) && canSpread(spreadBlockState, level, testPos)) {
					level.setBlockAndUpdate(
						testPos,
						spreadBlockState.setValue(SnowyBlock.SNOWY, level.getBlockState(testPos.above()).is(BlockTags.SNOW))
					);
				}
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(EATEN);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return level.getBlockState(pos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		((BonemealableBlock) Blocks.GRASS_BLOCK).performBonemeal(level, random, pos, state);
	}

	@Override
	public Type getType() {
		return Type.NEIGHBOR_SPREADER;
	}

	@Override
	protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
		var stack = ModItems.SOLID_GRASS_BLOCK.getDefaultInstance();
		stack.set(ModDataComponents.EATEN, state.getValue(EATEN));
		return stack;
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(EATEN, context.getItemInHand().get(ModDataComponents.EATEN));
	}

	public static boolean canGrassBlockSurvive(final BlockState state, final LevelReader level, final BlockPos pos) {
		var upperState = level.getBlockState(pos.above());

		if (upperState.is(Blocks.SNOW) && upperState.getValue(SnowLayerBlock.LAYERS) == 1) {
			return true;
		}

		if (upperState.getFluidState().isFull()) {
			return false;
		}

		return LightEngine.getLightDampeningInto(state, upperState, Direction.UP, upperState.getLightDampening()) < 15;
	}

	public static boolean canSolidGrassBlockSurvive(final BlockState state, final LevelReader level, final BlockPos pos) {
		for (Direction dir : Direction.values()) {
			if (!level.getBlockState(pos.relative(dir)).isSolid()) {
				return true;
			}
		}

		return false;
	}

	public static boolean canSpread(final BlockState blockState, final LevelReader world, final BlockPos pos) {
		return canGrassBlockSurvive(blockState, world, pos) && !world.getFluidState(pos.above()).is(FluidTags.WATER);
	}
}
