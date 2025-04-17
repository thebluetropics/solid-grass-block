package io.github.thebluetropics.solidgrassblock.item.group;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
	public static final ItemGroup SOLID_GRASS_BLOCK = register(
		"solid_grass_block",
		FabricItemGroup.builder()
			.icon(ModItems.SOLID_GRASS_BLOCK::getDefaultStack)
			.displayName(Text.translatable("itemGroup." + SolidGrassBlockMod.ID +".solid_grass_block"))
			.noScrollbar()
			.entries((context, entries) -> {
				entries.add(ModItems.SOLID_GRASS_BLOCK);
				entries.add(ModItems.SOLID_DIRT_PATH);
				entries.add(ModItems.SOLID_PODZOL);
				entries.add(ModItems.SOLID_MYCELIUM);
				entries.add(ModItems.SOLID_CRIMSON_NYLIUM);
				entries.add(ModItems.SOLID_WARPED_NYLIUM);
			})
			.build()
	);

	public static <T extends ItemGroup> T register(String id, T itemGroup) {
		return Registry.register(Registries.ITEM_GROUP, Identifier.of(SolidGrassBlockMod.ID, id), itemGroup);
	}

	public static void initialize() { /* ... */ }
}
