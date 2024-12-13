package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.block.SolidGrassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GrassBlock.class)
public class GrassBlockMixin {
  /// Allows plants to be grown on top of solid grass blocks when using bone meal
  @Redirect(
    method = "grow(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z",
      ordinal = 0
    )
  )
  private boolean isOf(BlockState state, Block block) {
    if (state.isOf(Blocks.GRASS_BLOCK)) {
      return true;
    }

    return state.isOf(ModBlocks.SOLID_GRASS_BLOCK) && !state.get(SolidGrassBlock.EATEN);
  }
}
