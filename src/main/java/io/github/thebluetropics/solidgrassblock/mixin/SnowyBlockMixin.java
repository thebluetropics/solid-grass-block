package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.SolidGrassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowyBlock.class)
public class SnowyBlockMixin {
  /// Force grass block to immediately turn into dirt when there's a solid block on top of it.
  @Inject(
    at = @At("HEAD"),
    method = "getStateForNeighborUpdate",
    cancellable = true
  )
  private void getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> info) {
    if (direction.equals(Direction.UP) && !neighborState.isIn(BlockTags.SNOW)) {
      if (!SolidGrassBlock.canGrassBlockSurvive(state, world, pos)) {
        info.setReturnValue(Blocks.DIRT.getDefaultState());
      }
    }
  }
}
