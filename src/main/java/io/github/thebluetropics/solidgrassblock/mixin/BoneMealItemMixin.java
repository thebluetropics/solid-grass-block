package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
  @Inject(at = @At("HEAD"), method = "useOnBlock", cancellable = true)
  private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
    var world = context.getWorld();

    var blockPos = context.getBlockPos();
    var blockState = world.getBlockState(blockPos);

    var stack = context.getStack();

    if (context.getSide() != Direction.UP && blockState.isOf(Blocks.GRASS_BLOCK)) {
      world.setBlockState(blockPos, ModBlocks.SOLID_GRASS_BLOCK.getDefaultState(), Block.NOTIFY_LISTENERS);
      stack.decrement(1);

      info.setReturnValue(ActionResult.SUCCESS);
    }
  }
}
