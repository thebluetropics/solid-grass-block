package io.github.thebluetropics.solidgrassblock.item.tab;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModCreativeModeTabs {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SolidGrassBlockMod.MOD_ID);
	public static final Supplier<CreativeModeTab> SOLID_GRASS_BLOCK = CREATIVE_MODE_TABS.register("solid_grass_block", () -> CreativeModeTab.builder()
		.title(Component.translatable("itemGroup." + SolidGrassBlockMod.MOD_ID))
		.icon(() -> new ItemStack(ModItems.SOLID_GRASS_BLOCK.get()))
		.displayItems((params, output) -> {
			output.accept(ModItems.SOLID_GRASS_BLOCK);
			output.accept(ModItems.SOLID_DIRT_PATH);
			output.accept(ModItems.SOLID_PODZOL);
			output.accept(ModItems.SOLID_MYCELIUM);
			output.accept(ModItems.SOLID_CRIMSON_NYLIUM);
			output.accept(ModItems.SOLID_WARPED_NYLIUM);
		})
		.build()
	);

	public static void register(IEventBus modEventBus) {
		CREATIVE_MODE_TABS.register(modEventBus);
	}
}
