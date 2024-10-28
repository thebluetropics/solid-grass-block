package io.github.thebluetropics.solidgrassblock.block;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
  private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SolidGrassBlockMod.ID);

  public static final DeferredBlock<Block> SOLID_GRASS_BLOCK = BLOCKS.registerBlock(
    "solid_grass_block",
    SolidGrassBlock::new,
    BlockBehaviour.Properties.of()
      .mapColor(MapColor.GRASS)
      .randomTicks()
      .strength(0.6f)
      .sound(SoundType.GRASS)
  );

  public static void register(IEventBus modEventBus) {
    BLOCKS.register(modEventBus);
  }
}
