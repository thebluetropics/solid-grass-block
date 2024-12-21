package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.tag.ModBlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.gen.feature.Feature.isSoil;

@Mixin(TrunkPlacer.class)
public class TrunkPlacerMixin {
  /// Convert custom grass block and solid mycelium to dirt when a tree is grown on top of them.
  @Inject(
    at = @At("HEAD"),
    method = "canGenerate",
    cancellable = true
  )
  private static void canGenerate(TestableWorld world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
    if (!world.testBlockState(pos, state -> isSoil(state) && !state.isIn(ModBlockTags.GRASS_BLOCK))) {
      info.setReturnValue(false);
    }

    if (!world.testBlockState(pos, state -> isSoil(state) && !state.isOf(ModBlocks.SOLID_MYCELIUM))) {
      info.setReturnValue(false);
    }
  }
}
