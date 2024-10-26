package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoeItem.class)
public class HoeItemMixin {
  @Inject(at = @At("HEAD"), method = "useOnBlock", cancellable = true)
  private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
    World world = context.getWorld();
    BlockPos pos = context.getBlockPos();

    if (world.getBlockState(pos).isOf(ModBlocks.SOLID_GRASS_BLOCK)) {
      if (HoeItem.canTillFarmland(context)) {
        PlayerEntity player = context.getPlayer();
        world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);

        if (!world.isClient()) {
          world.setBlockState(pos, Blocks.FARMLAND.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);
          world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, Blocks.FARMLAND.getDefaultState()));

          if (player != null) {
            context.getStack().damage(1, player, LivingEntity.getSlotForHand(context.getHand()));
          }
        }

        info.setReturnValue(ActionResult.success(world.isClient));
      }
    }
  }
}
