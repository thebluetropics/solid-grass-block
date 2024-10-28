package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
  @Inject(at = @At("HEAD"), method = "useOn", cancellable = true)
  private void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> info) {
    var level = context.getLevel();

    var blockPos = context.getClickedPos();
    var blockState = level.getBlockState(blockPos);

    if (context.getClickedFace() != Direction.UP && blockState.is(Blocks.GRASS_BLOCK)) {
      level.setBlock(blockPos, ModBlocks.SOLID_GRASS_BLOCK.get().defaultBlockState(), Block.UPDATE_CLIENTS);
      context.getItemInHand().shrink(1);

      info.setReturnValue(InteractionResult.SUCCESS);
    }
  }
}
