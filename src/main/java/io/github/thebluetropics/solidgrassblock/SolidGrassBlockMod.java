package io.github.thebluetropics.solidgrassblock;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.data.ModBlockLootSubProvider;
import io.github.thebluetropics.solidgrassblock.data.ModBlockTagsProvider;
import io.github.thebluetropics.solidgrassblock.data.ModItemTagsProvider;
import io.github.thebluetropics.solidgrassblock.data.ModModelProvider;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import io.github.thebluetropics.solidgrassblock.item.component.ModDataComponents;
import io.github.thebluetropics.solidgrassblock.item.tab.ModCreativeModeTabs;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.client.event.RegisterBlockStateModels;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.List;
import java.util.Set;

@Mod(SolidGrassBlockMod.MOD_ID)
public class SolidGrassBlockMod {
	public static final String MOD_ID = "solid_grass_block";
	public static final Logger LOGGER = LogUtils.getLogger();

	public SolidGrassBlockMod(IEventBus modEventBus, ModContainer modContainer) {
		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::gatherData);
		ModBlocks.register(modEventBus);
		ModDataComponents.register(modEventBus);
		ModItems.register(modEventBus);
		ModCreativeModeTabs.register(modEventBus);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		LOGGER.info("HELLO FROM COMMON SETUP");
	}

	public void gatherData(GatherDataEvent.Client event) {
		var output = event.getGenerator().getPackOutput();
		event.addProvider(new ModModelProvider(output));
		event.createProvider((out, lookupProvider) -> new LootTableProvider(
			out,
			Set.of(),
			List.of(new LootTableProvider.SubProviderEntry(ModBlockLootSubProvider::new, LootContextParamSets.BLOCK)),
			lookupProvider
		));
		event.createBlockAndItemTags(ModBlockTagsProvider::new, ModItemTagsProvider::new);
	}
}
