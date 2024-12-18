package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OcelotEntity.class)
public class OcelotEntityMixin {
  /// Allows Ocelots to spawn on top of a Solid Grass Block.
  @SuppressWarnings("DataFlowIssue")
  @Inject(
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/world/WorldView;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
      shift = At.Shift.AFTER,
      ordinal = 0
    ),
    method = "canSpawn(Lnet/minecraft/world/WorldView;)Z"
  )
  private void canSpawn(WorldView world, CallbackInfoReturnable<Boolean> info) {
    OcelotEntity entity = (OcelotEntity) (Object) this;

    if (world.getBlockState(entity.getBlockPos().down()).isOf(ModBlocks.SOLID_GRASS_BLOCK)) {
      info.setReturnValue(true);
    }
  }
}
