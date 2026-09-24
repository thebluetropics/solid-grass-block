package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractHorse.class)
public class AbstractHorseMixin {
  @Shadow
  private int eatingCounter;

  // Allow horses to eat solid grass block
  @Redirect(
    method = "Lnet/minecraft/world/entity/animal/equine/AbstractHorse;aiStep()V",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"
    )
  )
  private boolean is(BlockState state, Object object) {
    return state.is((Block) object) || state.is(ModBlocks.SOLID_GRASS_BLOCK);
  }
}
