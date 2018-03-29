package org.oilmod.api.items.type;

import org.oilmod.api.items.OilItemStack;

public interface IAxe extends IToolBlockBreaking {
    @Override
    default TBBType getTbbType() {return TBBType.AXE;}
}
