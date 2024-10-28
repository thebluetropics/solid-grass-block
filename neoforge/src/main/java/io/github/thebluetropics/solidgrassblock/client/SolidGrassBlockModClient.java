package io.github.thebluetropics.solidgrassblock.client;

import io.github.thebluetropics.solidgrassblock.SolidGrassBlockMod;
import io.github.thebluetropics.solidgrassblock.block.ModBlocks;
import io.github.thebluetropics.solidgrassblock.item.ModItems;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = SolidGrassBlockMod.ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SolidGrassBlockModClient {
  @SubscribeEvent
  public static void registerColorHandlersForBlock(RegisterColorHandlersEvent.Block event) {
    event.register(
      (blockState, level, blockPos, tintIndex) -> level != null && blockPos != null ? (
        BiomeColors.getAverageGrassColor(level, blockPos)
      ) : GrassColor.getDefaultColor(),
      ModBlocks.SOLID_GRASS_BLOCK.get()
    );
  }

  @SubscribeEvent
  public static void registerColorHandlersForItem(RegisterColorHandlersEvent.Item event) {
    event.register(
      (itemStack, tintIndex) -> GrassColor.getDefaultColor(),
      ModItems.SOLID_GRASS_BLOCK.get()
    );
  }
}
