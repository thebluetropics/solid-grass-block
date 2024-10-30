package io.github.thebluetropics.solidgrassblock;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class SolidGrassBlockMod implements ModInitializer {
  public static final String ID = "solidgrassblock";
  public static final Logger LOGGER = LoggerFactory.getLogger(ID);

  @Override
  public void onInitialize() {
    ModBlocks.initialize();
    ModItems.initialize();

    ItemGroupEvents.MODIFY_ENTRIES_ALL.register((itemGroup, fabricItemGroupEntries) -> {
      if (Objects.equals(itemGroup, ItemGroups.getDefaultTab())) {
        fabricItemGroupEntries.add(ModItems.SOLID_GRASS_BLOCK.getDefaultStack());
      }
    });
  }
}
