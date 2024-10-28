package io.github.thebluetropics.solidgrassblock.client;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;

public class SolidGrassBlockModClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ColorProviderRegistry.BLOCK.register(
      (state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getDefaultColor(),
      ModBlocks.SOLID_GRASS_BLOCK
    );

    ColorProviderRegistry.ITEM.register(
      (stack, tintIndex) -> GrassColors.getDefaultColor(),
      ModItems.SOLID_GRASS_BLOCK
    );
  }
}
