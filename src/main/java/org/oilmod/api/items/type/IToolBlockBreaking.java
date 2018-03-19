package org.oilmod.api.items.type;

import org.bukkit.block.BlockState;
import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.items.OilItemStack;

public interface IToolBlockBreaking extends ITool {
    ItemType<? extends IToolBlockBreaking, ? extends IToolBlockBreaking> getItemType();



    default boolean canDestroySpecialBlock(BlockState blockState, BlockType blockType) {
        return getItemType().canDestroySpecialBlock(this, blockState, blockType);
    }

    default float getDestroySpeed(OilItemStack itemStack, BlockState blockState, BlockType blockType) {
        return getItemType().getDestroySpeed(this, itemStack, blockState, blockType);
    }
}
