package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.block.SolidGrassBlock;
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
	private void useOn(final UseOnContext context, CallbackInfoReturnable<InteractionResult> info) {
		var level = context.getLevel();
		var pos = context.getClickedPos();
		var state = level.getBlockState(pos);

		// Turn dirt block into grass block
		if (context.getClickedFace().equals(Direction.UP) && state.is(Blocks.DIRT)) {
			level.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
			context.getItemInHand().shrink(1);
			info.setReturnValue(InteractionResult.SUCCESS);
		}

		// Turn grass block into solid grass block
		if (context.getClickedFace().getAxis().isHorizontal() && state.is(Blocks.GRASS_BLOCK)) {
			level.setBlockAndUpdate(pos, ModBlocks.SOLID_GRASS_BLOCK.defaultBlockState());
			context.getItemInHand().shrink(1);
			info.setReturnValue(InteractionResult.SUCCESS);
		}

		// Turn podzol into solid podzol
		if (context.getClickedFace().getAxis().isHorizontal() && state.is(Blocks.PODZOL)) {
			level.setBlockAndUpdate(pos, ModBlocks.SOLID_PODZOL.defaultBlockState());
			context.getItemInHand().shrink(1);
			info.setReturnValue(InteractionResult.SUCCESS);
		}

		// Turn mycelium into solid mycelium
		if (context.getClickedFace().getAxis().isHorizontal() && state.is(Blocks.MYCELIUM)) {
			level.setBlockAndUpdate(pos, ModBlocks.SOLID_MYCELIUM.defaultBlockState());
			context.getItemInHand().shrink(1);
			info.setReturnValue(InteractionResult.SUCCESS);
		}

		// Turn crimson nylium into solid crimson nylium
		if (context.getClickedFace().getAxis().isHorizontal() && state.is(Blocks.CRIMSON_NYLIUM)) {
			level.setBlockAndUpdate(pos, ModBlocks.SOLID_CRIMSON_NYLIUM.defaultBlockState());
			context.getItemInHand().shrink(1);
			info.setReturnValue(InteractionResult.SUCCESS);
		}

		// Turn warped nylium into solid crimson warped
		if (context.getClickedFace().getAxis().isHorizontal() && state.is(Blocks.WARPED_NYLIUM)) {
			level.setBlockAndUpdate(pos, ModBlocks.SOLID_WARPED_NYLIUM.defaultBlockState());
			context.getItemInHand().shrink(1);
			info.setReturnValue(InteractionResult.SUCCESS);
		}

		// Regrow eaten solid grass block
		if (context.getClickedFace() == Direction.UP && state.is(ModBlocks.SOLID_GRASS_BLOCK)) {
			if (state.getValue(SolidGrassBlock.EATEN)) {
				level.setBlockAndUpdate(pos, state.setValue(SolidGrassBlock.EATEN, false));
				context.getItemInHand().shrink(1);
				info.setReturnValue(InteractionResult.SUCCESS);
			}

		}
	}
}
