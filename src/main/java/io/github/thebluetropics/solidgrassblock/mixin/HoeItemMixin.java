package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoeItem.class)
public class HoeItemMixin {
	// Allows Solid Grass Block and Solid Dirt Path to be tilled into a Farmland Block
	@Inject(at = @At("HEAD"), method = "useOn", cancellable = true)
	private void useOnBlock(UseOnContext context, CallbackInfoReturnable<InteractionResult> info) {
		var level = context.getLevel();
		var player = context.getPlayer();
		var pos = context.getClickedPos();
		var state = level.getBlockState(pos);

		if (state.is(ModBlocks.SOLID_GRASS_BLOCK) || state.is(ModBlocks.SOLID_DIRT_PATH)) {
			if (HoeItem.onlyIfAirAbove(context)) {
				level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);

				if (!level.isClientSide()) {
					level.setBlock(pos, Blocks.FARMLAND.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
					level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));

					var stack = context.getItemInHand();
					if (player != null) {
						stack.hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
					}
				}

				info.setReturnValue(InteractionResult.SUCCESS);
			}
		}
	}
}
