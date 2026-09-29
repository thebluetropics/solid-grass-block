package io.github.thebluetropics.solidgrassblock.client;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.client.color.block.BlockTintSources;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.List;

@Mod(value = SolidGrassBlockMod.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SolidGrassBlockMod.MOD_ID, value = Dist.CLIENT)
public class SolidGrassBlockModClient {
	@SubscribeEvent
	public static void registerBlockColorHandlers(RegisterColorHandlersEvent.BlockTintSources event) {
		event.register(List.of(BlockTintSources.grassBlock()), ModBlocks.SOLID_GRASS_BLOCK.get());
	}
}
