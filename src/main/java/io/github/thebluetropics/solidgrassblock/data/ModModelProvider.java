package io.github.thebluetropics.solidgrassblock.data;

import com.mojang.math.Quadrant;
import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.block.SolidDirtPathBlock;
import io.github.thebluetropics.solidgrassblock.block.SolidGrassBlock;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import io.github.thebluetropics.solidgrassblock.item.component.ModDataComponents;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.ComponentContents;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;

import java.util.List;
import java.util.Optional;

public class ModModelProvider extends ModelProvider {
	public ModModelProvider(PackOutput output) {
		super(output, SolidGrassBlockMod.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		// Block state definition for solid grass block
		blockModels.blockStateOutput.accept(
			MultiPartGenerator.multiPart(ModBlocks.SOLID_GRASS_BLOCK.get())
				.with(
					BlockModelGenerators.condition().term(SolidGrassBlock.EATEN, false),
					new MultiVariant(
						WeightedList.of(
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_grass_block")), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_grass_block")).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_grass_block")).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_grass_block")).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)), 1)
						)
					)
				)
				.with(
					BlockModelGenerators.condition().term(SolidGrassBlock.EATEN, true),
					BlockModelGenerators.plainVariant(Identifier.parse("solid_grass_block:block/solid_grass_block_eaten"))
				)
		);

		// Block state definition for solid dirt path
		blockModels.blockStateOutput.accept(
			MultiPartGenerator.multiPart(ModBlocks.SOLID_DIRT_PATH.get())
				.with(
					BlockModelGenerators.condition().term(SolidDirtPathBlock.FULL_CUBE, false).term(SolidDirtPathBlock.CONNECTED, false),
					new MultiVariant(
						WeightedList.of(
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path")), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path")).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path")).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path")).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)), 1)
						)
					)
				)
				.with(
					BlockModelGenerators.condition().term(SolidDirtPathBlock.FULL_CUBE, true).term(SolidDirtPathBlock.CONNECTED, false),
					new MultiVariant(
						WeightedList.of(
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path_full_cube")), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path_full_cube")).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path_full_cube")).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path_full_cube")).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)), 1)
						)
					)
				)
				.with(
					BlockModelGenerators.condition().term(SolidDirtPathBlock.FULL_CUBE, false).term(SolidDirtPathBlock.CONNECTED, true),
					new MultiVariant(
						WeightedList.of(
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path_full_cube")), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path_full_cube")).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path_full_cube")).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path_full_cube")).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)), 1)
						)
					)
				)
				.with(
					BlockModelGenerators.condition().term(SolidDirtPathBlock.FULL_CUBE, true).term(SolidDirtPathBlock.CONNECTED, true),
					new MultiVariant(
						WeightedList.of(
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path_full_cube")), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path_full_cube")).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path_full_cube")).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_dirt_path_full_cube")).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)), 1)
						)
					)
				)
		);

		// Block state definition for solid podzol
		blockModels.blockStateOutput.accept(
			MultiPartGenerator.multiPart(ModBlocks.SOLID_PODZOL.get())
				.with(
					new MultiVariant(
						WeightedList.of(
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_podzol")), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_podzol")).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_podzol")).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_podzol")).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)), 1)
						)
					)
				)
		);

		// Block state definition for solid mycelium
		blockModels.blockStateOutput.accept(
			MultiPartGenerator.multiPart(ModBlocks.SOLID_MYCELIUM.get())
				.with(
					new MultiVariant(
						WeightedList.of(
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_mycelium")), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_mycelium")).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_mycelium")).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)), 1),
							new Weighted<>(new Variant(Identifier.parse("solid_grass_block:block/solid_mycelium")).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)), 1)
						)
					)
				)
		);

		// Block state definition for solid crimson Nylium
		blockModels.blockStateOutput.accept(
			MultiPartGenerator.multiPart(ModBlocks.SOLID_CRIMSON_NYLIUM.get())
				.with(BlockModelGenerators.plainVariant(Identifier.parse("solid_grass_block:block/solid_crimson_nylium")))
		);

		// Block state definition for solid warped nylium
		blockModels.blockStateOutput.accept(
			MultiPartGenerator.multiPart(ModBlocks.SOLID_WARPED_NYLIUM.get())
				.with(BlockModelGenerators.plainVariant(Identifier.parse("solid_grass_block:block/solid_warped_nylium")))
		);

		// Block model for solid grass block
		ExtendedModelTemplateBuilder.builder()
			.parent(Identifier.parse("minecraft:block/cube_all"))
			.requiredTextureSlot(TextureSlot.PARTICLE)
			.requiredTextureSlot(TextureSlot.ALL)
			.element(elementBuilder -> elementBuilder.from(0, 0, 0).to(16, 16, 16)
				.face(Direction.UP, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.UP).tintindex(0))
				.face(Direction.DOWN, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.DOWN).tintindex(0))
				.face(Direction.NORTH, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.NORTH).tintindex(0))
				.face(Direction.SOUTH, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.SOUTH).tintindex(0))
				.face(Direction.EAST, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.EAST).tintindex(0))
				.face(Direction.WEST, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.WEST).tintindex(0))
			)
			.build()
			.create(
				ModBlocks.SOLID_GRASS_BLOCK.get(),
				new TextureMapping()
					.put(TextureSlot.ALL, new Material(Identifier.parse("minecraft:block/grass_block_top")))
					.put(TextureSlot.PARTICLE, new Material(Identifier.parse("minecraft:block/dirt"))),
				blockModels.modelOutput
			);

		// Block model for eaten solid grass block
		ExtendedModelTemplateBuilder.builder()
			.parent(Identifier.parse("minecraft:block/block"))
			.requiredTextureSlot(TextureSlot.PARTICLE)
			.requiredTextureSlot(TextureSlot.TOP)
			.requiredTextureSlot(TextureSlot.ALL)
			.element(elementBuilder -> elementBuilder.from(0, 0, 0).to(16, 16, 16)
				.face(Direction.DOWN, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.DOWN).tintindex(0))
				.face(Direction.UP, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.TOP).cullface(Direction.UP))
				.face(Direction.NORTH, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.NORTH).tintindex(0))
				.face(Direction.SOUTH, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.SOUTH).tintindex(0))
				.face(Direction.WEST, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.WEST).tintindex(0))
				.face(Direction.EAST, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.EAST).tintindex(0))
			)
			.build()
			.create(
				Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "block/solid_grass_block_eaten"),
				new TextureMapping()
					.put(TextureSlot.ALL, new Material(Identifier.parse("minecraft:block/grass_block_top")))
					.put(TextureSlot.TOP, new Material(Identifier.parse("minecraft:block/dirt")))
					.put(TextureSlot.PARTICLE, new Material(Identifier.parse("minecraft:block/dirt"))),
				blockModels.modelOutput
			);

		// Block model for solid dirt path
		ExtendedModelTemplateBuilder.builder()
			.parent(Identifier.parse("minecraft:block/block"))
			.requiredTextureSlot(TextureSlot.PARTICLE)
			.requiredTextureSlot(TextureSlot.ALL)
			.element(elementBuilder -> elementBuilder.from(0, 0, 0).to(16, 15, 16)
				.face(Direction.DOWN, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.DOWN))
				.face(Direction.UP, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.UP))
				.face(Direction.NORTH, faceBuilder -> faceBuilder.uvs(0, 1, 16, 16).texture(TextureSlot.ALL).cullface(Direction.NORTH))
				.face(Direction.SOUTH, faceBuilder -> faceBuilder.uvs(0, 1, 16, 16).texture(TextureSlot.ALL).cullface(Direction.SOUTH))
				.face(Direction.WEST, faceBuilder -> faceBuilder.uvs(0, 1, 16, 16).texture(TextureSlot.ALL).cullface(Direction.WEST))
				.face(Direction.EAST, faceBuilder -> faceBuilder.uvs(0, 1, 16, 16).texture(TextureSlot.ALL).cullface(Direction.EAST))
			)
			.build()
			.create(
				ModBlocks.SOLID_DIRT_PATH.get(),
				new TextureMapping()
					.put(TextureSlot.ALL, new Material(Identifier.parse("minecraft:block/dirt_path_top")))
					.put(TextureSlot.PARTICLE, new Material(Identifier.parse("minecraft:block/dirt"))),
				blockModels.modelOutput
			);

		// Block model for full-block solid dirt path
		ExtendedModelTemplateBuilder.builder()
			.parent(Identifier.parse("minecraft:block/block"))
			.requiredTextureSlot(TextureSlot.PARTICLE)
			.requiredTextureSlot(TextureSlot.ALL)
			.element(elementBuilder -> elementBuilder.from(0, 0, 0).to(16, 16, 16)
				.face(Direction.DOWN, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.DOWN))
				.face(Direction.UP, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.UP))
				.face(Direction.NORTH, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.NORTH))
				.face(Direction.SOUTH, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.SOUTH))
				.face(Direction.WEST, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.WEST))
				.face(Direction.EAST, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.ALL).cullface(Direction.EAST))
			)
			.build()
			.create(
				Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "block/solid_dirt_path_full_cube"),
				new TextureMapping()
					.put(TextureSlot.ALL, new Material(Identifier.parse("minecraft:block/dirt_path_top")))
					.put(TextureSlot.PARTICLE, new Material(Identifier.parse("minecraft:block/dirt"))),
				blockModels.modelOutput
			);

		// Block model for solid podzol
		ModelTemplates.CUBE_ALL.create(
			Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "block/solid_podzol"),
			new TextureMapping()
				.put(TextureSlot.ALL, new Material(Identifier.parse("minecraft:block/podzol_top")))
				.put(TextureSlot.PARTICLE, new Material(Identifier.parse("minecraft:block/podzol_top"))),
			blockModels.modelOutput
		);

		// Solid Mycelium
		ModelTemplates.CUBE_ALL.create(
			Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "block/solid_mycelium"),
			new TextureMapping()
				.put(TextureSlot.ALL, new Material(Identifier.parse("minecraft:block/mycelium_top")))
				.put(TextureSlot.PARTICLE, new Material(Identifier.parse("minecraft:block/mycelium_top"))),
			blockModels.modelOutput
		);

		// Block model for solid crimson nylium
		ModelTemplates.CUBE_ALL.create(
			Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "block/solid_crimson_nylium"),
			new TextureMapping()
				.put(TextureSlot.ALL, new Material(Identifier.parse("minecraft:block/crimson_nylium")))
				.put(TextureSlot.PARTICLE, new Material(Identifier.parse("minecraft:block/crimson_nylium"))),
			blockModels.modelOutput
		);

		// Block model for solid warped nylium
		ModelTemplates.CUBE_ALL.create(
			Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "block/solid_warped_nylium"),
			new TextureMapping()
				.put(TextureSlot.ALL, new Material(Identifier.parse("minecraft:block/warped_nylium")))
				.put(TextureSlot.PARTICLE, new Material(Identifier.parse("minecraft:block/warped_nylium"))),
			blockModels.modelOutput
		);

		// Client item for solid grass block
		itemModels.itemModelOutput.accept(
			ModItems.SOLID_GRASS_BLOCK.get(),
			new SelectItemModel.Unbaked(
				Optional.empty(),
				new SelectItemModel.UnbakedSwitch<>(
					new ComponentContents<>(ModDataComponents.EATEN.get()),
					List.of(
						new SelectItemModel.SwitchCase<>(
							List.of(false),
							new CuboidItemModelWrapper.Unbaked(
								Identifier.parse("solid_grass_block:item/solid_grass_block"),
								Optional.empty(),
								List.of(new GrassColorSource(0.5F, 1.0F))
							)
						),
						new SelectItemModel.SwitchCase<>(
							List.of(true),
							new CuboidItemModelWrapper.Unbaked(
								Identifier.parse("solid_grass_block:item/solid_grass_block_eaten"),
								Optional.empty(),
								List.of(new GrassColorSource(0.5F, 1.0F))
							)
						)
					)
				),
				Optional.empty()
			)
		);

		// Item model for solid grass block
		ExtendedModelTemplateBuilder.builder()
			.parent(Identifier.parse("solid_grass_block:block/solid_grass_block"))
			.build()
			.create(Identifier.parse("solid_grass_block:item/solid_grass_block"), new TextureMapping(), blockModels.modelOutput);

		// Item model for eaten solid grass block
		ExtendedModelTemplateBuilder.builder()
			.parent(Identifier.parse("solid_grass_block:block/solid_grass_block_eaten"))
			.build()
			.create(Identifier.parse("solid_grass_block:item/solid_grass_block_eaten"), new TextureMapping(), blockModels.modelOutput);
	}
}
