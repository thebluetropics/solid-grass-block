package io.github.thebluetropics.solidgrassblock.block;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
  public static final Block SOLID_GRASS_BLOCK = register(
    "solid_grass_block",
    new SolidGrassBlock(
      AbstractBlock.Settings.create()
        .mapColor(MapColor.PALE_GREEN)
        .ticksRandomly()
        .strength(0.6f)
        .sounds(BlockSoundGroup.GRASS)
    )
  );

  public static <T extends Block> T register(String id, T block) {
    return Registry.register(Registries.BLOCK, new Identifier(SolidGrassBlockMod.ID, id), block);
  }

  public static void initialize() { /* ... */ }
}
