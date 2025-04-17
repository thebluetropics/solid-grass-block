package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.api.block.ModifiedGrassBlock;
import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.tag.ModBlockTags;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalEntity.class)
public class AnimalEntityMixin {
	/// Allow animals to favor solid grass block for pathfinding.
	@Inject(
		at = @At("HEAD"),
		method = "getPathfindingFavor(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/WorldView;)F",
		cancellable = true
	)
	private void isOf(BlockPos pos, WorldView world, CallbackInfoReturnable<Float> info) {
		var lowerPos = pos.down();
		var lowerState = world.getBlockState(lowerPos);

		if (lowerState.isIn(ModBlockTags.GRASS_BLOCK)) {
			if (lowerState.getBlock() instanceof ModifiedGrassBlock block) {
				if (block.canAnimalPathfindingFavor(lowerState, world, lowerPos)) {
					info.setReturnValue(10.0f);
				}
			} else {
				info.setReturnValue(10.0f);
			}
		}

		if (world.getBlockState(pos.down()).isOf(ModBlocks.SOLID_GRASS_BLOCK)) {
			info.setReturnValue(10.0f);
		}
	}
}
