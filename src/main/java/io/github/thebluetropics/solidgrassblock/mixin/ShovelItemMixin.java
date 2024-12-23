package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.api.block.ModifiedGrassBlock;
import io.github.thebluetropics.solidgrassblock.api.block.ModifiedPodzolBlock;
import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.block.SolidDirtPathBlock;
import io.github.thebluetropics.solidgrassblock.helper.BlockStateHelper;
import io.github.thebluetropics.solidgrassblock.tag.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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

    // Turn into solid dirt path
    if (!context.getSide().equals(Direction.DOWN) && BlockStateHelper.isOf(blockState, ModBlocks.SOLID_GRASS_BLOCK, ModBlocks.SOLID_MYCELIUM)) {
      if (world.getBlockState(blockPos.up()).isAir()) {
        if (!world.isClient()) {
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
        }

        info.setReturnValue(ActionResult.success(world.isClient));
      }
    }

    // Turn custom podzol block into dirt path.
    if (blockState.getBlock() instanceof ModifiedPodzolBlock block) {
      if (world.getBlockState(blockPos.up()).isAir()) {
        if (!world.isClient) {
          var pathState = block.getPathState();

          world.setBlockState(blockPos, ModBlocks.SOLID_DIRT_PATH.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);
          world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(context.getPlayer(), ModBlocks.SOLID_DIRT_PATH.getDefaultState()));

          var player = context.getPlayer();

          if (player != null) {
            context.getStack().damage(1, player, LivingEntity.getSlotForHand(context.getHand()));
          }
        }

        info.setReturnValue(ActionResult.success(world.isClient));
      }
    }

    if (blockState.isIn(ModBlockTags.GRASS_BLOCK)) {
      if (world.getBlockState(blockPos.up()).isAir()) {
        if (!world.isClient()) {
          BlockState pathState = Blocks.DIRT_PATH.getDefaultState();

          if (blockState.getBlock() instanceof ModifiedGrassBlock block) {
            pathState = block.getPathState();
          }

          world.setBlockState(blockPos, pathState, Block.NOTIFY_ALL_AND_REDRAW);
          world.emitGameEvent(
            GameEvent.BLOCK_CHANGE,
            blockPos,
            GameEvent.Emitter.of(context.getPlayer(), pathState)
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

    // Turn dirt path into solid dirt path
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

    // Cycle `full_cube` block state for solid dirt path
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

