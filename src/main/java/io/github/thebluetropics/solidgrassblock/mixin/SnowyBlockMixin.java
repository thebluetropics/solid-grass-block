package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.SolidGrassBlock;
import io.github.thebluetropics.solidgrassblock.block.SolidMyceliumBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowyBlock.class)
public class SnowyBlockMixin {
  /// Force Grass Block to immediately turn into dirt when there's a solid block on top of it.
  @Inject(
    at = @At("HEAD"),
    method = "getStateForNeighborUpdate",
    cancellable = true
  )
  private void getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> info) {
    if (state.isOf(Blocks.GRASS_BLOCK) && direction.equals(Direction.UP) && !neighborState.isIn(BlockTags.SNOW)) {
      if (!SolidGrassBlock.canGrassBlockSurvive(state, world, pos)) {
        info.setReturnValue(Blocks.DIRT.getDefaultState());
      }
    }

    if (state.isOf(Blocks.MYCELIUM) && direction.equals(Direction.UP) && !neighborState.isIn(BlockTags.SNOW)) {
      if (!SolidMyceliumBlock.canMyceliumSurvive(state, world, pos)) {
        info.setReturnValue(Blocks.DIRT.getDefaultState());
      }
    }
  }

  /// Force grass block to immediately turn into dirt when there's a solid block on top of it upon placing.
  @Inject(
    at = @At("HEAD"),
    method = "getPlacementState",
    cancellable = true
  )
  private void getPlacementState(ItemPlacementContext context, CallbackInfoReturnable<BlockState> info) {
    var world = context.getWorld();

    var stack = context.getStack();

    var pos = context.getBlockPos();
    var state = world.getBlockState(pos);

    if (stack.isOf(Items.GRASS_BLOCK)) {
      if (!SolidGrassBlock.canGrassBlockSurvive(state, world, pos)) {
        info.setReturnValue(Blocks.DIRT.getDefaultState());
      }
    }

    if (stack.isOf(Items.MYCELIUM)) {
      if (!SolidMyceliumBlock.canMyceliumSurvive(state, world, pos)) {
        info.setReturnValue(Blocks.DIRT.getDefaultState());
      }
    }
  }
}
