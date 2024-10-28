package io.github.thebluetropics.solidgrassblock;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SolidGrassBlockMod.ID)
public class SolidGrassBlockMod {
  public static final String ID = "solidgrassblock";
  public static final Logger LOGGER = LoggerFactory.getLogger(ID);

  public SolidGrassBlockMod(IEventBus modEventBus, ModContainer modContainer) {
    modEventBus.addListener(this::onCommonSetup);
    ModBlocks.register(modEventBus);
    ModItems.register(modEventBus);
  }

  public void onCommonSetup(final FMLCommonSetupEvent event) {
    SolidGrassBlockMod.LOGGER.info("Hello, World! (Common)");
  }
}
