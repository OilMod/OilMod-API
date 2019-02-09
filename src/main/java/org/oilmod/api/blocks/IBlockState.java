package org.oilmod.api.blocks;


import org.oilmod.api.rep.block.BlockRep;
import org.oilmod.api.rep.world.LocationBlockRep;

public interface IBlockState {
    BlockType getBlockType();
    BlockRep getBlock();
    float getBlockHardness(LocationBlockRep location); //todo this is weird, improve
    Object getNMS();
}
