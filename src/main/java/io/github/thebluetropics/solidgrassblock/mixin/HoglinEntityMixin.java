package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoglinEntity.class)
public class HoglinEntityMixin {
  @Inject(
    at = @At(
      value = "RETURN",
      ordinal = 1
    ),
    method = "getPathfindingFavor",
    cancellable = true
  )
  private void getPathfindingFavor(BlockPos pos, WorldView world, CallbackInfoReturnable<Float> info) {
    if (world.getBlockState(pos.down()).isOf(ModBlocks.SOLID_CRIMSON_NYLIUM)) {
      info.setReturnValue(10.0f);
    }
  }
}
