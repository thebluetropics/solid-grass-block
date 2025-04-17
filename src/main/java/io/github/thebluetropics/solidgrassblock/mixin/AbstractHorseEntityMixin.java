package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.api.block.ModifiedGrassBlock;
import io.github.thebluetropics.solidgrassblock.internal.MixinCompatibility;
import io.github.thebluetropics.solidgrassblock.tag.ModBlockTags;
import net.minecraft.entity.passive.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(AbstractHorseEntity.class)
public class AbstractHorseEntityMixin {
	@Shadow
	private int eatingGrassTicks;

	/// Allow horses to eat custom grass blocks.
	@SuppressWarnings("DataFlowIssue")
	@MixinCompatibility.High
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

		if (!entity.getWorld().isClient && entity.isAlive()) {
			if (entity.eatsGrass()) {
				var lowerBlockState = entity.getWorld().getBlockState(entity.getBlockPos().down());

				if (!entity.isEatingGrass()) {
					if (lowerBlockState.isIn(ModBlockTags.GRASS_BLOCK)) {
						if (lowerBlockState.getBlock() instanceof ModifiedGrassBlock block) {
							if (block.canMobsEat(lowerBlockState) && !entity.hasPassengers() && Objects.equals(entity.getRandom().nextInt(300), 0)) {
								entity.setEatingGrass(true);
							}
						} else {
							if (!entity.hasPassengers() && Objects.equals(entity.getRandom().nextInt(300), 0)) {
								entity.setEatingGrass(true);
							}
						}
					}
				}
			}
		}
	}
}
