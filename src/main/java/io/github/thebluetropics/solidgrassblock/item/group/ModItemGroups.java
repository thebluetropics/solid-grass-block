package io.github.thebluetropics.solidgrassblock.item.group;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class ModItemGroups {
	public static final CreativeModeTab SOLID_GRASS_BLOCK = FabricItemGroup.builder()
		.icon(() -> new ItemStack(ModItems.SOLID_GRASS_BLOCK))
		.title(Component.translatable("itemGroup." + SolidGrassBlockMod.MOD_ID))
		.displayItems((parameters, output) -> {
			output.accept(ModItems.SOLID_GRASS_BLOCK);
			output.accept(ModItems.SOLID_DIRT_PATH);
			output.accept(ModItems.SOLID_PODZOL);
			output.accept(ModItems.SOLID_MYCELIUM);
			output.accept(ModItems.SOLID_CRIMSON_NYLIUM);
			output.accept(ModItems.SOLID_WARPED_NYLIUM);
		})
		.build();

	public static void register() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), SolidGrassBlockMod.id("solid_grass_block")), SOLID_GRASS_BLOCK);
	}
}
