package io.github.thebluetropics.solidgrassblock.item.group;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

public class ModItemGroups {
  public static final ItemGroup SOLID_GRASS_BLOCK = register(
    "solid_grass_block",
    FabricItemGroup.builder()
      .icon(ModItems.SOLID_GRASS_BLOCK::getDefaultStack)
      .displayName(Text.translatable("itemGroup." + SolidGrassBlockMod.ID +".solid_grass_block"))
      .noScrollbar()
      .entries((context, entries) -> {
        for (Item item : ModItems.toArray()) {
          entries.add(item.getDefaultStack());
        }
      })
      .build()
  );

  @ApiStatus.Internal
  public static <T extends ItemGroup> T register(String id, T itemGroup) {
    return Registry.register(Registries.ITEM_GROUP, Identifier.of(SolidGrassBlockMod.ID, id), itemGroup);
  }

  @ApiStatus.Internal
  public static void initialize() { /* ... */ }
}
