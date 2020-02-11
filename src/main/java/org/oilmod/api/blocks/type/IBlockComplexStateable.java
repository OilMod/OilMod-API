package org.oilmod.api.blocks.type;

import org.oilmod.api.stateable.complex.ICStateable;

public interface IBlockComplexStateable<TSelf extends IBlockComplexStateable<TSelf>> extends IBlockGeneric, ICStateable<TSelf> {
}
