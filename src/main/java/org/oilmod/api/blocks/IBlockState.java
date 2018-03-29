package org.oilmod.api.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public interface IBlockState {
    BlockType getBlockType();
    Material getMaterial();
    float getBlockHardness(Location location);
    Object getNMS();
}
