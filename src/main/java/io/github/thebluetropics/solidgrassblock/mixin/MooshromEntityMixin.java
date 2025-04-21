package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MooshroomEntity.class)
public class MooshromEntityMixin {
	// Allows mooshroom to favor solid mycelium for pathfinding
	@Inject(
		at = @At("HEAD"),
		method = "getPathfindingFavor",
		cancellable = true
	)
	private void getPathfindingFavor(BlockPos pos, WorldView world, CallbackInfoReturnable<Float> info) {
		var lowerBlockState = world.getBlockState(pos.down());

		if (lowerBlockState.isOf(ModBlocks.SOLID_MYCELIUM)) {
			info.setReturnValue(10.0f);
		}
	}
}
