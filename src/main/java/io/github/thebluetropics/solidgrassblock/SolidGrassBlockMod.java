package io.github.thebluetropics.solidgrassblock;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.component.ModDataComponents;
import io.github.thebluetropics.solidgrassblock.item.ModCreativeModeTabs;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import net.fabricmc.api.ModInitializer;

public class SolidGrassBlockMod implements ModInitializer {
	public static final String MOD_ID = "solid_grass_block";

	@Override
	public void onInitialize() {
		ModBlocks.initialize();
		ModItems.initialize();
		ModDataComponents.initialize();
		ModCreativeModeTabs.register();
	}
}
