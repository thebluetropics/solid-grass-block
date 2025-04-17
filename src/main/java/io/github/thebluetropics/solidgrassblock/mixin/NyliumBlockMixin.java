package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NyliumBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NyliumBlock.class)
public class NyliumBlockMixin {
	@Redirect(
		method = "grow(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z",
			ordinal = 0
		)
	)
	private boolean isOf_0(BlockState state, Block block) {
		if (block.equals(ModBlocks.SOLID_CRIMSON_NYLIUM)) {
			return true;
		}

		return state.isOf(block);
	}

	@Redirect(
		method = "grow(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z",
			ordinal = 1
		)
	)
	private boolean isOf_1(BlockState state, Block block) {
		if (block.equals(ModBlocks.SOLID_WARPED_NYLIUM)) {
			return true;
		}

		return state.isOf(block);
	}
}
