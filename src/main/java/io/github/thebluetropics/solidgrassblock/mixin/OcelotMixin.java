package io.github.thebluetropics.solidgrassblock.mixin;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.world.entity.animal.feline.Ocelot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Ocelot.class)
public class OcelotMixin {
  @Redirect(
    method = "checkSpawnObstruction",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z",
      ordinal = 0
    )
  )
  private boolean is(BlockState state, Object object) {
    return state.is((Block) object) || state.is(ModBlocks.SOLID_GRASS_BLOCK);
  }
}
