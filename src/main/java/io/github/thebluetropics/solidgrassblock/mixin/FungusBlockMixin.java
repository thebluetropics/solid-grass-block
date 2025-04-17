package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FungusBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FungusBlock.class)
public class FungusBlockMixin {
	@Shadow
	private Block nylium;

	// Allows fungus to be planted on top of solid mycelium.
	@Inject(
		at = @At("HEAD"),
		method = "canPlantOnTop",
		cancellable = true
	)
	private void canPlantOnTop(BlockState floorState, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		if (floorState.isOf(ModBlocks.SOLID_MYCELIUM)) {
			info.setReturnValue(true);
		}
	}

	// Allows fungus on top of solid nyliums to be grown using bone meal.
	@Inject(
		at = @At("HEAD"),
		method = "isFertilizable",
		cancellable = true
	)
	private void isFertilizable(WorldView world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> info) {
		var lowerBlockState = world.getBlockState(pos.down());

		if (this.nylium.equals(Blocks.CRIMSON_NYLIUM)) {
			if (lowerBlockState.isOf(ModBlocks.SOLID_CRIMSON_NYLIUM)) {
				info.setReturnValue(true);
			}
		}

		if (this.nylium.equals(Blocks.WARPED_NYLIUM)) {
			if (lowerBlockState.isOf(ModBlocks.SOLID_WARPED_NYLIUM)) {
				info.setReturnValue(true);
			}
		}
	}
}
