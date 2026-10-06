package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FungusBlock.class)
public class FungusBlockMixin {
	@Shadow
	private Block requiredBlock;

	// Allows fungus on top of solid nyliums to be grown using bone meal
	@Inject(
		at = @At("HEAD"),
		method = "isValidBonemealTarget",
		cancellable = true
	)
	private void isValidBonemealTarget(LevelReader levelReader, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> info) {
		var lowerState = levelReader.getBlockState(pos.below());

		if (this.requiredBlock == Blocks.CRIMSON_NYLIUM) {
			if (lowerState.is(ModBlocks.SOLID_CRIMSON_NYLIUM)) {
				info.setReturnValue(true);
			}
		}

		if (this.requiredBlock == Blocks.WARPED_NYLIUM) {
			if (lowerState.is(ModBlocks.SOLID_WARPED_NYLIUM)) {
				info.setReturnValue(true);
			}
		}
	}
}
