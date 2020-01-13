package org.oilmod.api.blocks.type;

import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.blocks.PistonReaction;
import org.oilmod.api.registry.IKeySettable;

public interface IBlockGeneric extends IKeySettable {
    default PistonReaction getPistonReaction() {
        return PistonReaction.NORMAL;
    }

    BlockType getBlockType();


    default BlockImplementationProvider getImplementationProvider() {
        return BlockImplementationProvider.GENERIC.getValue();
    }

}
