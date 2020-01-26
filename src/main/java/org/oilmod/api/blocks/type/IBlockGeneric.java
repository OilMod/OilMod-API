package org.oilmod.api.blocks.type;

import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.blocks.PistonReaction;
import org.oilmod.api.items.type.TBBType;
import org.oilmod.api.registry.IKeySettable;

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

}
