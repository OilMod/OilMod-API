package org.oilmod.api.blocks.type;

import org.apache.commons.lang3.NotImplementedException;
import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.blocks.PistonReaction;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.type.TBBType;
import org.oilmod.api.registry.IKeySettable;
import org.oilmod.api.rep.item.ItemRep;

public interface IBlockGeneric extends IKeySettable {
    default PistonReaction getPistonReaction() {
        return PistonReaction.NORMAL;
    }

    BlockType getBlockType();


    default BlockImplementationProvider getImplementationProvider() {
        return BlockImplementationProvider.GENERIC.getValue();
    }

    default boolean isBlocksMovement() {return true;}
    //todo sound
    default int getLightSourceValue() {return 0;}

    default float getResistance() {return 0;}
    default float getHardness() {return 0;}

    default int getHarvestLevel() {return -1;}
    default TBBType getHarvestTool() {return TBBType.NONE.getValue();}

    default float getSlipperiness() {return 0.6f;}

    default boolean hasAssociatedItem() {return true;}

    default ItemRep getAssociatedItem() {
        throw new NotImplementedException("todo"); //todo
    }

    default boolean hasCustomAssociatedItem() {return false;}
    default OilItem getCustomAssociatedItem() {
        throw new NotImplementedException("if hasCustomAssociatedItem returns true, getCustomAssociatedItem needs to be overridden");
    }



}
