package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.HugeFungusFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HugeFungusFeature.class)
public class HugeFungusFeatureMixin {
  @Redirect(
    method = "place",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z",
      ordinal = 0
    )
  )
  private boolean is(BlockState state, Object object) {
    var block = (Block) object;

    if (block == Blocks.CRIMSON_NYLIUM && state.is(ModBlocks.SOLID_CRIMSON_NYLIUM))
      return true;

    if (block == Blocks.WARPED_NYLIUM && state.is(ModBlocks.SOLID_WARPED_NYLIUM))
      return true;

    return state.is(block);
  }
}
