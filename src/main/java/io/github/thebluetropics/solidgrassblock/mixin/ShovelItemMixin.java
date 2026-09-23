package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.block.SolidDirtPathBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
	@Inject(at = @At("HEAD"), method = "useOn", cancellable = true)
	private void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> info) {
		var level = context.getLevel();
		var pos = context.getClickedPos();
		var state = level.getBlockState(pos);

		// Turn into solid dirt path
		if (state.is(ModBlocks.SOLID_GRASS_BLOCK) || state.is(ModBlocks.SOLID_PODZOL) || state.is(ModBlocks.SOLID_MYCELIUM)) {
			if (level.getBlockState(pos.above()).isAir()) {
				if (!level.isClientSide()) {
					level.setBlock(pos, ModBlocks.SOLID_DIRT_PATH.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
					level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(context.getPlayer(), ModBlocks.SOLID_DIRT_PATH.defaultBlockState()));
					var player = context.getPlayer();
					if (player != null) context.getItemInHand().hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
				}
				info.setReturnValue(InteractionResult.SUCCESS);
			}
		}

		// Turn dirt path into solid dirt path
		if (context.getClickedFace().getAxis().isHorizontal() && state.is(Blocks.DIRT_PATH)) {
			level.setBlock(pos, ModBlocks.SOLID_DIRT_PATH.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(context.getPlayer(), ModBlocks.SOLID_DIRT_PATH.defaultBlockState()));

			var player = context.getPlayer();
			var stack = context.getItemInHand();

			if (player != null) {
				stack.hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
			}

			info.setReturnValue(InteractionResult.SUCCESS);
		}

		// Toggle `full_cube` state for solid dirt path
		if (context.getClickedFace().equals(Direction.UP) && state.is(ModBlocks.SOLID_DIRT_PATH)) {
			var updatedBlockState = state.setValue(SolidDirtPathBlock.FULL_CUBE, !state.getValue(SolidDirtPathBlock.FULL_CUBE));

			level.setBlock(pos, updatedBlockState, Block.UPDATE_ALL_IMMEDIATE);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(context.getPlayer(), updatedBlockState));

			var player = context.getPlayer();
			var stack = context.getItemInHand();

			if (player != null) {
				stack.hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
			}

			info.setReturnValue(InteractionResult.SUCCESS);
		}
	}
}
