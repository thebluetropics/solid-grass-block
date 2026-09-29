package io.github.thebluetropics.solidgrassblock.data;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;

import java.util.concurrent.CompletableFuture;

public final class ModItemTagsProvider extends BlockTagCopyingItemTagProvider {
	public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
		super(output, lookupProvider, blockTags, SolidGrassBlockMod.MOD_ID);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) {
		this.copy(ModBlockTagsProvider.GRASS_BLOCKS, TagKey.create(Registries.ITEM, Identifier.withDefaultNamespace("grass_blocks")));
	}
}
