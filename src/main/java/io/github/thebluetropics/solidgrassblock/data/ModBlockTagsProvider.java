package io.github.thebluetropics.solidgrassblock.data;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
	public static final TagKey<Block> GRASS_BLOCKS = minecraft("grass_blocks");

	public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, SolidGrassBlockMod.MOD_ID);
	}

	private static TagKey<Block> minecraft(String path) {
		return TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace(path));
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) {
		this.tag(minecraft("animal_spawnable_on")).add(ModBlocks.SOLID_GRASS_BLOCK.get());
		this.tag(minecraft("cannot_replace_below_tree_trunk")).add(ModBlocks.SOLID_PODZOL.get());
		this.tag(minecraft("enderman_holdable")).add(ModBlocks.SOLID_CRIMSON_NYLIUM.get(), ModBlocks.SOLID_WARPED_NYLIUM.get());
		this.tag(minecraft("foxes_spawnable_on")).add(ModBlocks.SOLID_GRASS_BLOCK.get(), ModBlocks.SOLID_PODZOL.get());
		this.tag(minecraft("frogs_spawnable_on")).add(ModBlocks.SOLID_GRASS_BLOCK.get());
		this.tag(GRASS_BLOCKS).add(ModBlocks.SOLID_GRASS_BLOCK.get(), ModBlocks.SOLID_PODZOL.get(), ModBlocks.SOLID_MYCELIUM.get());
		this.tag(minecraft("huge_brown_mushroom_can_place_on")).add(ModBlocks.SOLID_PODZOL.get(), ModBlocks.SOLID_MYCELIUM.get(), ModBlocks.SOLID_CRIMSON_NYLIUM.get(), ModBlocks.SOLID_WARPED_NYLIUM.get());
		this.tag(minecraft("huge_red_mushroom_can_place_on")).add(ModBlocks.SOLID_PODZOL.get(), ModBlocks.SOLID_MYCELIUM.get(), ModBlocks.SOLID_CRIMSON_NYLIUM.get(), ModBlocks.SOLID_WARPED_NYLIUM.get());
		this.tag(minecraft("mineable/pickaxe")).add(ModBlocks.SOLID_CRIMSON_NYLIUM.get(), ModBlocks.SOLID_WARPED_NYLIUM.get());
		this.tag(minecraft("mineable/shovel")).add(ModBlocks.SOLID_GRASS_BLOCK.get(), ModBlocks.SOLID_DIRT_PATH.get(), ModBlocks.SOLID_PODZOL.get(), ModBlocks.SOLID_MYCELIUM.get());
		this.tag(minecraft("mooshrooms_spawnable_on")).add(ModBlocks.SOLID_MYCELIUM.get());
		this.tag(minecraft("nylium")).add(ModBlocks.SOLID_CRIMSON_NYLIUM.get(), ModBlocks.SOLID_WARPED_NYLIUM.get());
		this.tag(minecraft("overrides_mushroom_light_requirement")).add(ModBlocks.SOLID_PODZOL.get(), ModBlocks.SOLID_MYCELIUM.get(), ModBlocks.SOLID_CRIMSON_NYLIUM.get(), ModBlocks.SOLID_WARPED_NYLIUM.get());
		this.tag(minecraft("parrots_spawnable_on")).add(ModBlocks.SOLID_GRASS_BLOCK.get());
		this.tag(minecraft("rabbits_spawnable_on")).add(ModBlocks.SOLID_GRASS_BLOCK.get());
		this.tag(minecraft("sniffer_diggable_block")).add(ModBlocks.SOLID_GRASS_BLOCK.get(), ModBlocks.SOLID_PODZOL.get());
		this.tag(minecraft("supports_big_dripleaf")).add(ModBlocks.SOLID_GRASS_BLOCK.get(), ModBlocks.SOLID_PODZOL.get(), ModBlocks.SOLID_MYCELIUM.get());
		this.tag(minecraft("valid_spawn")).add(ModBlocks.SOLID_GRASS_BLOCK.get(), ModBlocks.SOLID_PODZOL.get());
		this.tag(minecraft("wolves_spawnable_on")).add(ModBlocks.SOLID_GRASS_BLOCK.get(), ModBlocks.SOLID_PODZOL.get());
	}
}
