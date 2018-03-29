package org.oilmod.api.items.type;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.util.InteractionResult;

public interface IShovel extends IToolBlockBreaking {
    @Override
    default TBBType getTbbType() {return TBBType.SHOVEL;}

    @Override
    default InteractionResult onItemUseOnBlock(OilItemStack stack, HumanEntity human, Location pos, boolean offhand, BlockFace facing, float hitX, float hitY, float hitZ) {
        //noinspection unchecked
        return ((TBBType)getTbbType()).onItemUseOnBlock(this, stack, human, pos, offhand, facing, hitX, hitY, hitZ);
    }
}
