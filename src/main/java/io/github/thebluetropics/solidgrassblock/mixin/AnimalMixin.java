package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Animal.class)
public class AnimalMixin {
  @Inject(
    method = "getWalkTargetValue",
    at = @At("HEAD"),
    cancellable = true
  )
  private void getWalkTargetValue(final BlockPos pos, final LevelReader level, CallbackInfoReturnable<Float> info) {
    if (level.getBlockState(pos.below()).is(ModBlocks.SOLID_GRASS_BLOCK)) {
      info.setReturnValue(10.0f);
    }
  }
}
