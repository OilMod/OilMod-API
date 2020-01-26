package org.oilmod.api.items.type;

import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.rep.block.BlockFaceRep;
import org.oilmod.api.rep.entity.EntityHumanRep;
import org.oilmod.api.rep.world.LocationBlockRep;
import org.oilmod.api.util.InteractionResult;

public interface IShovel extends IToolBlockBreaking {
    @Override
    default TBBType getTbbType() {return TBBType.SHOVEL.getValue();}

    @Override
    default InteractionResult onItemUseOnBlock(OilItemStack stack, EntityHumanRep human, LocationBlockRep pos, boolean offhand, BlockFaceRep facing, float hitX, float hitY, float hitZ) {
        //noinspection unchecked
        return ((TBBType)getTbbType()).onItemUseOnBlock(this, stack, human, pos, offhand, facing, hitX, hitY, hitZ);
    }
}
