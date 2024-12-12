package io.github.thebluetropics.solidgrassblock.tag;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {
  public static final TagKey<Block> GRASS_BLOCK = of("grass_block");

  public static TagKey<Block> of(String id) {
    return TagKey.of(RegistryKeys.BLOCK, Identifier.of(SolidGrassBlockMod.ID, id));
  }

  public static void initialize() { /* ... */ }
}
