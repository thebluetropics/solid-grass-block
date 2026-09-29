package io.github.thebluetropics.solidgrassblock.item.component;

import com.mojang.serialization.Codec;
import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModDataComponents {
	public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, SolidGrassBlockMod.MOD_ID);
	public static final Supplier<DataComponentType<Boolean>> EATEN = DATA_COMPONENTS.registerComponentType(
		"eaten",
		builder -> builder
			.persistent(Codec.BOOL)
			.networkSynchronized(ByteBufCodecs.BOOL)
	);

	public static void register(IEventBus modEventBus) {
		DATA_COMPONENTS.register(modEventBus);
	}
}
