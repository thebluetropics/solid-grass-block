package io.github.thebluetropics.solidgrassblock.block;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

public final class ModBlocks {
  public static final Block SOLID_GRASS_BLOCK = Registry.register(
    BuiltInRegistries.BLOCK,
    ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_grass_block")),
    new SolidGrassBlock(
      Properties.of()
        .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_grass_block")))
        .mapColor(MapColor.GRASS)
        .randomTicks()
        .strength(0.6f)
        .sound(SoundType.GRASS)
    )
  );
	public static final Block SOLID_DIRT_PATH = Registry.register(
		BuiltInRegistries.BLOCK,
		ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_dirt_path")),
		new SolidDirtPathBlock(
			Properties.of()
				.setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_dirt_path")))
				.mapColor(MapColor.DIRT)
				.strength(0.65f)
				.sound(SoundType.GRASS)
				.isViewBlocking(Blocks::always)
				.isSuffocating(Blocks::always)
		)
	);
	public static final Block SOLID_PODZOL = Registry.register(
		BuiltInRegistries.BLOCK,
		ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_podzol")),
		new SolidPodzolBlock(
			Properties.of()
				.setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_podzol")))
				.mapColor(MapColor.PODZOL)
				.strength(0.5f)
				.sound(SoundType.GRAVEL)
		)
	);
	public static final Block SOLID_MYCELIUM = Registry.register(
		BuiltInRegistries.BLOCK,
		ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_mycelium")),
		new SolidMyceliumBlock(
			Properties.of()
				.setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_mycelium")))
				.mapColor(MapColor.COLOR_PURPLE)
				.randomTicks()
				.strength(0.6f)
				.sound(SoundType.GRASS)
		)
	);
	public static final Block SOLID_CRIMSON_NYLIUM = Registry.register(
		BuiltInRegistries.BLOCK,
		ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_crimson_nylium")),
		new SolidNyliumBlock(
			Properties.of()
				.setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_crimson_nylium")))
				.mapColor(MapColor.CRIMSON_NYLIUM)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.requiresCorrectToolForDrops()
				.strength(0.4f)
				.sound(SoundType.NYLIUM)
				.randomTicks()
		)
	);
	public static final Block SOLID_WARPED_NYLIUM = Registry.register(
		BuiltInRegistries.BLOCK,
		ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_warped_nylium")),
		new SolidNyliumBlock(
			Properties.of()
				.setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "solid_warped_nylium")))
				.mapColor(MapColor.WARPED_NYLIUM)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.requiresCorrectToolForDrops()
				.strength(0.4f)
				.sound(SoundType.NYLIUM)
				.randomTicks()
		)
	);

	public static void initialize() {
	}
}
