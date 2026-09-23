package io.github.thebluetropics.solidgrassblock.component;

import com.mojang.serialization.Codec;
import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public final class ModDataComponents {
	public static final DataComponentType<Boolean> EATEN = Registry.register(
		BuiltInRegistries.DATA_COMPONENT_TYPE,
		Identifier.fromNamespaceAndPath(SolidGrassBlockMod.MOD_ID, "eaten"),
		DataComponentType.<Boolean>builder()
			.persistent(Codec.BOOL)
			.build()
	);

	public static void initialize() {
	}
}
