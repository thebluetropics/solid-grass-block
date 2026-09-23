package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherrackBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NetherrackBlock.class)
public class NetherrackBlockMixin {
	// Allows Solid Crimson Nylium block to spread
	@Redirect(
		method = "performBonemeal(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z",
			ordinal = 1
		)
	)
	private boolean isCrimsonNylium(BlockState state, Object block) {
		return state.is(Blocks.CRIMSON_NYLIUM) || state.is(ModBlocks.SOLID_CRIMSON_NYLIUM);
	}

	// Allows Solid Warped Nylium block to spread
	@Redirect(
		method = "performBonemeal(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z",
			ordinal = 0
		)
	)
	private boolean isWarpedNylium(BlockState state, Object block) {
		return state.is(Blocks.WARPED_NYLIUM) || state.is(ModBlocks.SOLID_WARPED_NYLIUM);
	}
}
