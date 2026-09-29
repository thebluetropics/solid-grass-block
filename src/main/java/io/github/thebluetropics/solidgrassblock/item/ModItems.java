package io.github.thebluetropics.solidgrassblock.item;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.item.component.ModDataComponents;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SolidGrassBlockMod.MOD_ID);
	public static final DeferredItem<BlockItem> SOLID_GRASS_BLOCK = ITEMS.registerSimpleBlockItem(
		"solid_grass_block",
		ModBlocks.SOLID_GRASS_BLOCK,
		properties -> properties
			.component(ModDataComponents.EATEN.get(), false)
	);
	public static final DeferredItem<BlockItem> SOLID_DIRT_PATH = ITEMS.registerSimpleBlockItem(
		"solid_dirt_path",
		ModBlocks.SOLID_DIRT_PATH
	);
	public static final DeferredItem<BlockItem> SOLID_PODZOL = ITEMS.registerSimpleBlockItem(
		"solid_podzol",
		ModBlocks.SOLID_PODZOL
	);
	public static final DeferredItem<BlockItem> SOLID_MYCELIUM = ITEMS.registerSimpleBlockItem(
		"solid_mycelium",
		ModBlocks.SOLID_MYCELIUM
	);
	public static final DeferredItem<BlockItem> SOLID_CRIMSON_NYLIUM = ITEMS.registerSimpleBlockItem(
		"solid_crimson_nylium",
		ModBlocks.SOLID_CRIMSON_NYLIUM
	);
	public static final DeferredItem<BlockItem> SOLID_WARPED_NYLIUM = ITEMS.registerSimpleBlockItem(
		"solid_warped_nylium",
		ModBlocks.SOLID_WARPED_NYLIUM
	);

	public static void register(IEventBus modEventBus) {
		ITEMS.register(modEventBus);
	}
}
