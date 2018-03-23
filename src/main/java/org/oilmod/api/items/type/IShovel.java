package org.oilmod.api.items.type;

public interface IShovel extends IToolBlockBreaking {
    @Override
    default TBBType<? extends IShovel> getTbbType() {return TBBType.SHOVEL;}
}
