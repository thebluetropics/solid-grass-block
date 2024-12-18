package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.helper.BlockStateHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherrackBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NetherrackBlock.class)
public class NetherrackBlockMixin {
  /// Allows Solid Warped Nylium to spread.
  @Redirect(
    method = "grow(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z",
      ordinal = 0
    )
  )
  private boolean isOfWarpedNylium(BlockState blockState, Block block) {
    return BlockStateHelper.isOf(blockState, Blocks.WARPED_NYLIUM, ModBlocks.SOLID_WARPED_NYLIUM);
  }

  /// Allows Solid Crimson Nylium to spread.
  @Redirect(
    method = "grow(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z",
      ordinal = 1
    )
  )
  private boolean isOfCrimsonNylium(BlockState blockState, Block block) {
    return BlockStateHelper.isOf(blockState, Blocks.CRIMSON_NYLIUM, ModBlocks.SOLID_CRIMSON_NYLIUM);
  }
}
