package io.github.thebluetropics.solidgrassblock.item;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.component.ModDataComponents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public final class ModItems {
	public static final Item SOLID_GRASS_BLOCK = Registry.register(
		BuiltInRegistries.ITEM,
		ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_grass_block")),
		new BlockItem(
			ModBlocks.SOLID_GRASS_BLOCK,
			new Item.Properties()
				.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_grass_block")))
				.useBlockDescriptionPrefix()
				.component(ModDataComponents.EATEN, false)
		)
	);
	public static final Item SOLID_DIRT_PATH = Registry.register(
		BuiltInRegistries.ITEM,
		ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_dirt_path")),
		new BlockItem(
			ModBlocks.SOLID_DIRT_PATH,
			new Item.Properties()
				.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_dirt_path")))
				.useBlockDescriptionPrefix()
		)
	);
	public static final Item SOLID_PODZOL = Registry.register(
		BuiltInRegistries.ITEM,
		ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_podzol")),
		new BlockItem(
			ModBlocks.SOLID_PODZOL,
			new Item.Properties()
				.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_podzol")))
				.useBlockDescriptionPrefix()
		)
	);
	public static final Item SOLID_MYCELIUM = Registry.register(
		BuiltInRegistries.ITEM,
		ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_mycelium")),
		new BlockItem(
			ModBlocks.SOLID_MYCELIUM,
			new Item.Properties()
				.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_mycelium")))
				.useBlockDescriptionPrefix()
		)
	);
	public static final Item SOLID_CRIMSON_NYLIUM = Registry.register(
		BuiltInRegistries.ITEM,
		ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_crimson_nylium")),
		new BlockItem(
			ModBlocks.SOLID_CRIMSON_NYLIUM,
			new Item.Properties()
				.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_crimson_nylium")))
				.useBlockDescriptionPrefix()
		)
	);
	public static final Item SOLID_WARPED_NYLIUM = Registry.register(
		BuiltInRegistries.ITEM,
		ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_warped_nylium")),
		new BlockItem(
			ModBlocks.SOLID_WARPED_NYLIUM,
			new Item.Properties()
				.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_warped_nylium")))
				.useBlockDescriptionPrefix()
		)
	);

	public static void initialize() {
	}
}
