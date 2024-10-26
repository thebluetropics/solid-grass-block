package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
  @Inject(at = @At("HEAD"), method = "useOnBlock", cancellable = true)
  private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
    World world = context.getWorld();
    BlockPos blockPos = context.getBlockPos();
    BlockState blockState = context.getWorld().getBlockState(blockPos);

    if (!context.getSide().equals(Direction.DOWN) && blockState.isOf(ModBlocks.SOLID_GRASS_BLOCK)) {
      if (world.getBlockState(blockPos.up()).isAir()) {
        if (!world.isClient()) {
          world.setBlockState(blockPos, Blocks.DIRT_PATH.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);
          world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(context.getPlayer(), Blocks.DIRT_PATH.getDefaultState()));
          if (context.getPlayer() != null) {
            context.getStack().damage(1, context.getPlayer(), LivingEntity.getSlotForHand(context.getHand()));
          }
        }

        info.setReturnValue(ActionResult.success(world.isClient));
      }
    }
  }
}

