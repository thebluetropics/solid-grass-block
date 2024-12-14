package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.FungusBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FungusBlock.class)
public class FungusBlockMixin {
  /// Allows fungus to be planted on top of solid mycelium.
  @Inject(
    at = @At("HEAD"),
    method = "canPlantOnTop",
    cancellable = true
  )
  private void canPlantOnTop(BlockState floorState, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
    if (floorState.isOf(ModBlocks.SOLID_MYCELIUM)) {
      info.setReturnValue(true);
    }
  }
}
