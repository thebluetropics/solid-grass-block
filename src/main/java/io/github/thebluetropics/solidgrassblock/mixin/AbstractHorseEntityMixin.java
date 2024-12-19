package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.block.SolidGrassBlock;
import io.github.thebluetropics.solidgrassblock.tag.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.passive.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractHorseEntity.class)
public class AbstractHorseEntityMixin {
  /// Allows horses to eat solid grass blocks and custom grass blocks.
  @Redirect(
    method = "tickMovement()V",
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

    if (state.isOf(ModBlocks.SOLID_GRASS_BLOCK) && !state.get(SolidGrassBlock.EATEN)) {
      return true;
    }

    return state.isIn(ModBlockTags.GRASS_BLOCK);
  }
}
