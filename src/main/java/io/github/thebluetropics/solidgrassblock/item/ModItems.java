package io.github.thebluetropics.solidgrassblock.item;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
  public static final BlockItem SOLID_GRASS_BLOCK = register("solid_grass_block", new BlockItem(ModBlocks.SOLID_GRASS_BLOCK, new Item.Settings()));

  public static <T extends Item> T register(String id, T item) {
    return Registry.register(Registries.ITEM, new Identifier(SolidGrassBlockMod.ID, id), item);
  }

  public static void initialize() { /* ... */ }
}
