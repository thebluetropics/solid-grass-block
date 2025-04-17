package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.SolidGrassBlock;
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

		// Turns dirt into grass block
		if (Objects.equals(context.getSide(), Direction.UP) && blockState.isOf(Blocks.DIRT)) {
			world.setBlockState(blockPos, Blocks.GRASS_BLOCK.getDefaultState(), Block.NOTIFY_LISTENERS);
			context.getStack().decrement(1);

			info.setReturnValue(ActionResult.SUCCESS);
		}

		// Turns grass block into solid grass block
		if (context.getSide() != Direction.UP && blockState.isOf(Blocks.GRASS_BLOCK)) {
			world.setBlockState(blockPos, ModBlocks.SOLID_GRASS_BLOCK.getDefaultState(), Block.NOTIFY_LISTENERS);
			context.getStack().decrement(1);

			info.setReturnValue(ActionResult.SUCCESS);
		}

		// Turns podzol into solid podzol
		if (context.getSide() != Direction.UP && blockState.isOf(Blocks.PODZOL)) {
			world.setBlockState(blockPos, ModBlocks.SOLID_PODZOL.getDefaultState(), Block.NOTIFY_LISTENERS);
			context.getStack().decrement(1);

			info.setReturnValue(ActionResult.SUCCESS);
		}

		// Turns mycelium into solid mycelium
		if (context.getSide() != Direction.UP && blockState.isOf(Blocks.MYCELIUM)) {
			world.setBlockState(blockPos, ModBlocks.SOLID_MYCELIUM.getDefaultState(), Block.NOTIFY_LISTENERS);
			context.getStack().decrement(1);

			info.setReturnValue(ActionResult.SUCCESS);
		}

		// Turns crimson nylium into solid crimson nylium
		if (context.getSide() != Direction.UP && blockState.isOf(Blocks.CRIMSON_NYLIUM)) {
			world.setBlockState(blockPos, ModBlocks.SOLID_CRIMSON_NYLIUM.getDefaultState(), Block.NOTIFY_LISTENERS);
			context.getStack().decrement(1);

			info.setReturnValue(ActionResult.SUCCESS);
		}

		// Turns warped nylium into solid waroed nylium
		if (context.getSide() != Direction.UP && blockState.isOf(Blocks.WARPED_NYLIUM)) {
			world.setBlockState(blockPos, ModBlocks.SOLID_WARPED_NYLIUM.getDefaultState(), Block.NOTIFY_LISTENERS);
			context.getStack().decrement(1);

			info.setReturnValue(ActionResult.SUCCESS);
		}

		// Regrow eaten solid grass block
		if (context.getSide().equals(Direction.UP) && blockState.isOf(ModBlocks.SOLID_GRASS_BLOCK)) {
			if (blockState.get(SolidGrassBlock.EATEN)) {
				world.setBlockState(blockPos, ModBlocks.SOLID_GRASS_BLOCK.getDefaultState(), Block.NOTIFY_LISTENERS);
				context.getStack().decrement(1);

				info.setReturnValue(ActionResult.SUCCESS);
			}
		}
	}
}
