package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.block.SolidGrassBlock;
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

    // Turns Dirt into Grass Block
    if (Objects.equals(context.getSide(), Direction.UP) && blockState.isOf(Blocks.DIRT)) {
      world.setBlockState(blockPos, Blocks.GRASS_BLOCK.getDefaultState(), Block.NOTIFY_LISTENERS);
      context.getStack().decrement(1);

      info.setReturnValue(ActionResult.SUCCESS);
    }

    // Turns Grass Block into Solid Grass Block
    if (context.getSide() != Direction.UP && blockState.isOf(Blocks.GRASS_BLOCK)) {
      world.setBlockState(blockPos, ModBlocks.SOLID_GRASS_BLOCK.getDefaultState(), Block.NOTIFY_LISTENERS);
      context.getStack().decrement(1);

      info.setReturnValue(ActionResult.SUCCESS);
    }

    // Turns Podzol into Solid Podzol
    if (context.getSide() != Direction.UP && blockState.isOf(Blocks.PODZOL)) {
      world.setBlockState(blockPos, ModBlocks.SOLID_PODZOL.getDefaultState(), Block.NOTIFY_LISTENERS);
      context.getStack().decrement(1);

      info.setReturnValue(ActionResult.SUCCESS);
    }

    // Turns Mycelium into Solid Mycelium
    if (context.getSide() != Direction.UP && blockState.isOf(Blocks.MYCELIUM)) {
      world.setBlockState(blockPos, ModBlocks.SOLID_MYCELIUM.getDefaultState(), Block.NOTIFY_LISTENERS);
      context.getStack().decrement(1);

      info.setReturnValue(ActionResult.SUCCESS);
    }

    // Turns Crimson Nylium into Solid Crimson Nylium
    if (context.getSide() != Direction.UP && blockState.isOf(Blocks.CRIMSON_NYLIUM)) {
      world.setBlockState(blockPos, ModBlocks.SOLID_CRIMSON_NYLIUM.getDefaultState(), Block.NOTIFY_LISTENERS);
      context.getStack().decrement(1);

      info.setReturnValue(ActionResult.SUCCESS);
    }

    // Turns Warped Nylium into Solid Waroed Nylium
    if (context.getSide() != Direction.UP && blockState.isOf(Blocks.WARPED_NYLIUM)) {
      world.setBlockState(blockPos, ModBlocks.SOLID_WARPED_NYLIUM.getDefaultState(), Block.NOTIFY_LISTENERS);
      context.getStack().decrement(1);

      info.setReturnValue(ActionResult.SUCCESS);
    }

    // Regrow eaten Solid Grass Block
    if (context.getSide().equals(Direction.UP) && blockState.isOf(ModBlocks.SOLID_GRASS_BLOCK)) {
      if (blockState.get(SolidGrassBlock.EATEN)) {
        world.setBlockState(blockPos, ModBlocks.SOLID_GRASS_BLOCK.getDefaultState(), Block.NOTIFY_LISTENERS);
        context.getStack().decrement(1);

        info.setReturnValue(ActionResult.SUCCESS);
      }
    }
  }
}
