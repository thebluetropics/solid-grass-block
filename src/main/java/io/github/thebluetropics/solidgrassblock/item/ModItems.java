package io.github.thebluetropics.solidgrassblock.item;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.component.ModDataComponents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ModItems {
  private static final ArrayList<Item> items = new ArrayList<>();

  public static final BlockItem SOLID_GRASS_BLOCK = register(
    "solid_grass_block",
    new BlockItem(
      ModBlocks.SOLID_GRASS_BLOCK,
      new Item.Settings()
        .component(ModDataComponents.EATEN, false)
    )
  );
  public static final BlockItem SOLID_DIRT_PATH = register(
    "solid_dirt_path",
    new BlockItem(
      ModBlocks.SOLID_DIRT_PATH,
      new Item.Settings()
    )
  );
  public static final BlockItem SOLID_PODZOL = register(
    "solid_podzol",
    new BlockItem(
      ModBlocks.SOLID_PODZOL,
      new Item.Settings()
    )
  );
  public static final BlockItem SOLID_MYCELIUM = register(
    "solid_mycelium",
    new BlockItem(
      ModBlocks.SOLID_MYCELIUM,
      new Item.Settings()
    )
  );
  public static final BlockItem SOLID_CRIMSON_NYLIUM = register(
    "solid_crimson_nylium",
    new BlockItem(
      ModBlocks.SOLID_CRIMSON_NYLIUM,
      new Item.Settings()
    )
  );
  public static final BlockItem SOLID_WARPED_NYLIUM = register(
    "solid_warped_nylium",
    new BlockItem(
      ModBlocks.SOLID_WARPED_NYLIUM,
      new Item.Settings()
    )
  );

  @ApiStatus.Internal
  public static <T extends Item> T register(String id, T item) {
    Registry.register(Registries.ITEM, new Identifier(SolidGrassBlockMod.ID, id), item);

    items.add(item);

    return item;
  }

  public static Item[] toArray() {
    return items.toArray(new Item[] {});
  }

  public static Set<Item> toSet() {
    return new HashSet<>(items);
  }

  @ApiStatus.Internal
  public static void initialize() { /* ... */ }
}
