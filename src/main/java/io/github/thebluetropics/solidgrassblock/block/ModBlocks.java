package io.github.thebluetropics.solidgrassblock.block;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SolidGrassBlockMod.MOD_ID);
	public static final DeferredBlock<Block> SOLID_GRASS_BLOCK = BLOCKS.register("solid_grass_block", registryName -> new SolidGrassBlock(
		BlockBehaviour.Properties.of()
			.setId(ResourceKey.create(Registries.BLOCK, registryName))
			.mapColor(MapColor.GRASS)
			.randomTicks()
			.strength(0.6f)
			.sound(SoundType.GRASS)
	));
	public static final DeferredBlock<Block> SOLID_DIRT_PATH = BLOCKS.register("solid_dirt_path", registryName -> new SolidDirtPathBlock(
		BlockBehaviour.Properties.of()
			.setId(ResourceKey.create(Registries.BLOCK, registryName))
			.mapColor(MapColor.DIRT)
			.strength(0.65f)
			.sound(SoundType.GRASS)
			.isViewBlocking((state, level, pos) -> true)
			.isSuffocating((state, level, pos) -> true)
	));
	public static final DeferredBlock<Block> SOLID_PODZOL = BLOCKS.register("solid_podzol", registryName -> new SolidPodzolBlock(
		BlockBehaviour.Properties.of()
			.setId(ResourceKey.create(Registries.BLOCK, registryName))
			.mapColor(MapColor.PODZOL)
			.strength(0.5f)
			.sound(SoundType.GRAVEL)
	));
	public static final DeferredBlock<Block> SOLID_MYCELIUM = BLOCKS.register("solid_mycelium", registryName -> new SolidMyceliumBlock(
		BlockBehaviour.Properties.of()
			.setId(ResourceKey.create(Registries.BLOCK, registryName))
			.mapColor(MapColor.COLOR_PURPLE)
			.randomTicks()
			.strength(0.6f)
			.sound(SoundType.GRASS)
	));
	public static final DeferredBlock<Block> SOLID_CRIMSON_NYLIUM = BLOCKS.register("solid_crimson_nylium", registryName -> new SolidNyliumBlock(
		BlockBehaviour.Properties.of()
			.setId(ResourceKey.create(Registries.BLOCK, registryName))
			.mapColor(MapColor.CRIMSON_NYLIUM)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.strength(0.4f)
			.sound(SoundType.NYLIUM)
			.randomTicks()
	));
	public static final DeferredBlock<Block> SOLID_WARPED_NYLIUM = BLOCKS.register("solid_warped_nylium", registryName -> new SolidNyliumBlock(
		BlockBehaviour.Properties.of()
			.setId(ResourceKey.create(Registries.BLOCK, registryName))
			.mapColor(MapColor.WARPED_NYLIUM)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.strength(0.4f)
			.sound(SoundType.NYLIUM)
			.randomTicks()
	));

	public static void register(IEventBus modEventBus) {
		BLOCKS.register(modEventBus);
	}
}
