package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.entity.passive.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorseEntity.class)
public class AbstractHorseEntityMixin {
	@Shadow
	private int eatingGrassTicks;

	// Allow horses to eat custom grass blocks
	@Inject(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/passive/AnimalEntity;tickMovement()V",
			ordinal = 0,
			shift = At.Shift.AFTER
		),
		method = "tickMovement()V"
	)
	private void tickMovement(CallbackInfo info) {
		var entity = (AbstractHorseEntity) (Object) this;
		var world = entity.getWorld();

		if (!world.isClient) {
			if (entity.isAlive() && entity.eatsGrass() && !entity.isEatingGrass()) {
				if (world.getBlockState(entity.getBlockPos().down()).isOf(ModBlocks.SOLID_GRASS_BLOCK) && !entity.hasPassengers()) {
					if (!(entity.getRandom().nextInt(300) != 0)) {
						entity.setEatingGrass(true);
					}
				}
			}
		}
	}
}
