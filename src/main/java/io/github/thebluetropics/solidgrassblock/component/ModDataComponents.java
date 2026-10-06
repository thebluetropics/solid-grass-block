package io.github.thebluetropics.solidgrassblock.component;

import com.mojang.serialization.Codec;
import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public final class ModDataComponents {
	public static final DataComponentType<Boolean> EATEN = Registry.register(
		BuiltInRegistries.DATA_COMPONENT_TYPE,
		SolidGrassBlockMod.id("eaten"),
		DataComponentType.<Boolean>builder()
			.persistent(Codec.BOOL)
			.build()
	);

	public static void initialize() {
	}
}
