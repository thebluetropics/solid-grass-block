package io.github.thebluetropics.solidgrassblock.client;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.component.ModDataComponents;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

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

		ModelPredicateProviderRegistry.register(
			ModItems.SOLID_GRASS_BLOCK,
			Identifier.of(SolidGrassBlockMod.ID, "eaten"),
			(stack, world, entity, seed) -> Objects.equals(stack.get(ModDataComponents.EATEN), true) ? 1.0f : 0.0f
		);
	}
}
