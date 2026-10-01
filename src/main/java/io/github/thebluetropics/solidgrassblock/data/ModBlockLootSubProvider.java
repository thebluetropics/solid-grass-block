package io.github.thebluetropics.solidgrassblock.data;

import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.block.SolidGrassBlock;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import io.github.thebluetropics.solidgrassblock.item.component.ModDataComponents;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Set;

public class ModBlockLootSubProvider extends BlockLootSubProvider {
	public ModBlockLootSubProvider(HolderLookup.Provider lookupProvider) {
		super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries()
			.stream()
			.map(entry -> (Block) entry.value())
			.toList();
	}

	@Override
	protected void generate() {
		this.add(ModBlocks.SOLID_GRASS_BLOCK.get(), LootTable.lootTable().withPool(LootPool.lootPool()
			.setRolls(ConstantValue.exactly(1.0F))
			.setBonusRolls(ConstantValue.exactly(0.0F))
			.add(AlternativesEntry.alternatives(
				LootItem.lootTableItem(ModItems.SOLID_GRASS_BLOCK.get())
					.apply(SetComponentsFunction.setComponent(ModDataComponents.EATEN.get(), true))
					.when(this.hasSilkTouch())
					.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.SOLID_GRASS_BLOCK.get())
						.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SolidGrassBlock.EATEN, true))),
				LootItem.lootTableItem(ModItems.SOLID_GRASS_BLOCK.get())
					.apply(SetComponentsFunction.setComponent(ModDataComponents.EATEN.get(), false))
					.when(this.hasSilkTouch())
					.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.SOLID_GRASS_BLOCK.get())
						.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SolidGrassBlock.EATEN, false))),
				LootItem.lootTableItem(Items.DIRT)
					.when(ExplosionCondition.survivesExplosion())
			))
		));
		this.add(ModBlocks.SOLID_DIRT_PATH.get(), this.createSingleItemTableWithSilkTouch(ModBlocks.SOLID_DIRT_PATH.get(), Items.DIRT));
		this.add(ModBlocks.SOLID_MYCELIUM.get(), this.createSingleItemTableWithSilkTouch(ModBlocks.SOLID_MYCELIUM.get(), Items.DIRT));
		this.add(ModBlocks.SOLID_PODZOL.get(), this.createSingleItemTableWithSilkTouch(ModBlocks.SOLID_PODZOL.get(), Items.DIRT));
		this.add(ModBlocks.SOLID_CRIMSON_NYLIUM.get(), this.createSingleItemTableWithSilkTouch(ModBlocks.SOLID_CRIMSON_NYLIUM.get(), Items.NETHERRACK));
		this.add(ModBlocks.SOLID_WARPED_NYLIUM.get(), this.createSingleItemTableWithSilkTouch(ModBlocks.SOLID_WARPED_NYLIUM.get(), Items.NETHERRACK));
	}
}
