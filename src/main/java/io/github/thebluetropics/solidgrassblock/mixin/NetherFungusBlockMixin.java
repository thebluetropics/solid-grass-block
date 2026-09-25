package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherFungusBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetherFungusBlock.class)
public class NetherFungusBlockMixin {
  @Inject(
    method = "isValidBonemealTarget",
    at = @At("HEAD"),
    cancellable = true
  )
  private void solid_grass_block$isValidBoneMealTarget(final LevelReader level, final BlockPos pos, final BlockState state, CallbackInfoReturnable<Boolean> info) {
    var lowerState = level.getBlockState(pos.below());

    if (state.is(Blocks.CRIMSON_FUNGUS)) {
      if (lowerState.is(ModBlocks.SOLID_CRIMSON_NYLIUM) && level.isInsideBuildHeight(pos.above())) {
        info.setReturnValue(true);
      }
    }

    if (state.is(Blocks.WARPED_FUNGUS)) {
      if (lowerState.is(ModBlocks.SOLID_WARPED_NYLIUM) && level.isInsideBuildHeight(pos.above())) {
        info.setReturnValue(true);
      }
    }
  }
}
