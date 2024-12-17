package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.HugeFungusFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HugeFungusFeature.class)
public class HugeFungusFeatureMixin {
  /// Allows huge fungus feature to be placed on top of solid nyliums.
  @Redirect(
    method = "generate(Lnet/minecraft/world/gen/feature/util/FeatureContext;)Z",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z",
      ordinal = 0
    )
  )
  private boolean isOf(BlockState instance, Block block) {
    System.out.println(instance.getBlock() + ", " + block);

    if (block.equals(Blocks.CRIMSON_NYLIUM)) {
      if (instance.isOf(ModBlocks.SOLID_CRIMSON_NYLIUM)) {
        return true;
      }
    }

    if (block.equals(Blocks.WARPED_NYLIUM)) {
      if (instance.isOf(ModBlocks.SOLID_WARPED_NYLIUM)) {
        return true;
      }
    }

    return instance.isOf(block);
  }
}
