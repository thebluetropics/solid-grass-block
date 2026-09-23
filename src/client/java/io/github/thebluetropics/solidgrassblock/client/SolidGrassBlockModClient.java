package io.github.thebluetropics.solidgrassblock.client;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.color.block.BlockTintSources;

import java.util.List;

public class SolidGrassBlockModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockColorRegistry.register(List.of(BlockTintSources.grassBlock()), ModBlocks.SOLID_GRASS_BLOCK);
	}
}
