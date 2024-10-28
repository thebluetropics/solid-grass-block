package io.github.thebluetropics.solidgrassblock.item;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SolidGrassBlockMod.ID);

  public static final DeferredItem<BlockItem> SOLID_GRASS_BLOCK = ITEMS.register(
    "solid_grass_block",
    key -> new BlockItem(ModBlocks.SOLID_GRASS_BLOCK.get(), new Item.Properties())
  );

  public static void register(IEventBus modEventBus) {
    ITEMS.register(modEventBus);
  }
}
