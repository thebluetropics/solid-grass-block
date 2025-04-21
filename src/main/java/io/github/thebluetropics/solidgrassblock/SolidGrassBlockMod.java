package io.github.thebluetropics.solidgrassblock;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import io.github.thebluetropics.solidgrassblock.item.group.ModItemGroups;
import net.fabricmc.api.ModInitializer;

public class SolidGrassBlockMod implements ModInitializer {
	public static final String ID = "solid_grass_block";

	@Override
	public void onInitialize() {
		ModBlocks.initialize();
		ModItems.initialize();
		ModItemGroups.initialize();
	}
}
