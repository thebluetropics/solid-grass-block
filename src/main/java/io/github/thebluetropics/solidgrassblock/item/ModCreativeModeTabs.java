package io.github.thebluetropics.solidgrassblock.item;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class ModCreativeModeTabs {
	public static final ResourceKey<CreativeModeTab> CUSTOM_CREATIVE_TAB_KEY = ResourceKey.create(
		BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_grass_block")
	);
	public static final CreativeModeTab SOLID_GRASS_BLOCK = FabricCreativeModeTab.builder()
		.icon(() -> new ItemStack(ModItems.SOLID_GRASS_BLOCK))
		.title(Component.translatable("creativeTab." + SolidGrassBlockMod.MOD_ID))
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
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_CREATIVE_TAB_KEY, SOLID_GRASS_BLOCK);
	}
}
