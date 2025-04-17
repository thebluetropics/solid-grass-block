package io.github.thebluetropics.solidgrassblock.helper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.jetbrains.annotations.ApiStatus;

public class BlockStateHelper {
	public static boolean isOf(BlockState blockState, Block... blocks) {
		for (Block block : blocks) {
			if (blockState.isOf(block)) {
				return true;
			}
		}

		return false;
	}
}
