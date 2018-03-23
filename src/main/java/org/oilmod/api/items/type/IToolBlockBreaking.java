package org.oilmod.api.items.type;

import org.bukkit.block.BlockState;
import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.items.OilItemStack;

@SuppressWarnings("unchecked")
public interface IToolBlockBreaking extends ITool {
    TBBType getTbbType();



    default boolean canDestroySpecialBlock(BlockState blockState, BlockType blockType) {
        return getTbbType().canDestroySpecialBlock(this, blockState, blockType);
    }

    default float getDestroySpeed(OilItemStack itemStack, BlockState blockState, BlockType blockType) {
        return getTbbType().getDestroySpeed(this, itemStack, blockState, blockType);
    }

    float getBaseDestroySpeed(OilItemStack itemStack);
}
