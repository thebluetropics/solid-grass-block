package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalEntity.class)
public class AnimalEntityMixin {
  @Inject(
    at = @At("HEAD"),
    method = "getPathfindingFavor(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/WorldView;)F",
    cancellable = true
  )
  private void isOf(BlockPos pos, WorldView world, CallbackInfoReturnable<Float> info) {
    if (world.getBlockState(pos.down()).isOf(ModBlocks.SOLID_GRASS_BLOCK)) {
      info.setReturnValue(10.0f);
    }
  }
}
