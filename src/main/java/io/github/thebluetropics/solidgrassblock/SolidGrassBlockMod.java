package io.github.thebluetropics.solidgrassblock;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import io.github.thebluetropics.solidgrassblock.item.group.ModItemGroups;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolidGrassBlockMod implements ModInitializer {
  public static final String ID = "solid_grass_block";
  public static final Logger LOGGER = LoggerFactory.getLogger(SolidGrassBlockMod.class);

  @Override
  public void onInitialize() {
    ModBlocks.initialize();
    ModItems.initialize();
    ModItemGroups.initialize();
  }
}
