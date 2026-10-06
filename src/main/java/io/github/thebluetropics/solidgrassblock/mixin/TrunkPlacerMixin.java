package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TrunkPlacer.class)
public class TrunkPlacerMixin {
	// Convert custom grass block and solid mycelium to dirt when a tree is grown on top of them
	@Inject(
		method = "isDirt",
		at = @At("HEAD"),
		cancellable = true
	)
	private static void solid_grass_block$isDirt(LevelSimulatedReader levelSimulatedReader, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		if (levelSimulatedReader.isStateAtPosition(pos, state -> state.is(ModBlocks.SOLID_GRASS_BLOCK))) {
			info.setReturnValue(false);
		}

		if (levelSimulatedReader.isStateAtPosition(pos, state -> state.is(ModBlocks.SOLID_MYCELIUM))) {
			info.setReturnValue(false);
		}
	}
}
