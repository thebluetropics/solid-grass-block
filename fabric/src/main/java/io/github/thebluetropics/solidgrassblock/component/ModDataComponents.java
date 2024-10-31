package io.github.thebluetropics.solidgrassblock.component;

import com.mojang.serialization.Codec;
import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import net.minecraft.component.DataComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModDataComponents {
  public static final DataComponentType<Boolean> EATEN = register(
    "eaten",
    DataComponentType.<Boolean>builder()
      .codec(Codec.BOOL)
      .packetCodec(PacketCodecs.BOOL)
      .build()
  );

  public static <T> DataComponentType<T> register(String id, DataComponentType<T> dataComponentType) {
    return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(SolidGrassBlockMod.ID, id), dataComponentType);
  }

  public static void initialize() { /* ... */ }
}
