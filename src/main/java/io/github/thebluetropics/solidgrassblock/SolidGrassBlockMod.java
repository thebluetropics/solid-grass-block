package io.github.thebluetropics.solidgrassblock;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.component.ModDataComponents;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import io.github.thebluetropics.solidgrassblock.item.group.ModItemGroups;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolidGrassBlockMod implements ModInitializer {
	public static final String MOD_ID = "solid_grass_block";

	@Override
	public void onInitialize() {
		ModBlocks.initialize();
		ModItems.initialize();
		ModDataComponents.initialize();
		ModItemGroups.register();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
