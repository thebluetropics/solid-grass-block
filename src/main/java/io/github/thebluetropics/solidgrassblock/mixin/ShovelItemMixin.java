package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.block.SolidDirtPathBlock;
import io.github.thebluetropics.solidgrassblock.helper.BlockStateHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
  @Inject(at = @At("HEAD"), method = "useOnBlock", cancellable = true)
  private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
    var world = context.getWorld();

    var blockPos = context.getBlockPos();
    var blockState = context.getWorld().getBlockState(blockPos);

    // Turns into Dirt Path
    if (!context.getSide().equals(Direction.DOWN) && BlockStateHelper.isOf(blockState, ModBlocks.SOLID_GRASS_BLOCK, ModBlocks.SOLID_PODZOL, ModBlocks.SOLID_MYCELIUM)) {
      if (world.getBlockState(blockPos.up()).isAir()) {
        if (!world.isClient()) {
          world.setBlockState(
            blockPos,
            Blocks.DIRT_PATH.getDefaultState(),
            Block.NOTIFY_ALL_AND_REDRAW
          );
          world.emitGameEvent(
            GameEvent.BLOCK_CHANGE,
            blockPos,
            GameEvent.Emitter.of(context.getPlayer(), Blocks.DIRT_PATH.getDefaultState())
          );

          var player = context.getPlayer();
          var stack = context.getStack();

          if (player != null) {
            stack.damage(1, player, LivingEntity.getSlotForHand(context.getHand()));
          }
        }

        info.setReturnValue(ActionResult.success(world.isClient));
      }
    }

    // Turns Dirt Path into Solid Dirt Path
    if (!context.getSide().equals(Direction.UP) && blockState.isOf(Blocks.DIRT_PATH)) {
      world.setBlockState(
        blockPos,
        ModBlocks.SOLID_DIRT_PATH.getDefaultState(),
        Block.NOTIFY_ALL_AND_REDRAW
      );
      world.emitGameEvent(
        GameEvent.BLOCK_CHANGE,
        blockPos,
        GameEvent.Emitter.of(context.getPlayer(), ModBlocks.SOLID_DIRT_PATH.getDefaultState())
      );

      var player = context.getPlayer();
      var stack = context.getStack();

      if (player != null) {
        stack.damage(1, player, LivingEntity.getSlotForHand(context.getHand()));
      }

      info.setReturnValue(ActionResult.success(world.isClient));
    }

    // Cycle `full_cube` block state for Solid Dirt Path
    if (context.getSide().equals(Direction.UP) && blockState.isOf(ModBlocks.SOLID_DIRT_PATH)) {
      var newBlockState = blockState.with(SolidDirtPathBlock.FULL_CUBE, !blockState.get(SolidDirtPathBlock.FULL_CUBE));

      world.setBlockState(
        blockPos,
        newBlockState,
        Block.NOTIFY_ALL_AND_REDRAW
      );
      world.emitGameEvent(
        GameEvent.BLOCK_CHANGE,
        blockPos,
        GameEvent.Emitter.of(context.getPlayer(), newBlockState)
      );

      var player = context.getPlayer();
      var stack = context.getStack();

      if (player != null) {
        stack.damage(1, player, LivingEntity.getSlotForHand(context.getHand()));
      }

      info.setReturnValue(ActionResult.success(world.isClient));
    }
  }
}

