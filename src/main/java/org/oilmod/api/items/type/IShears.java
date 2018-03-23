package org.oilmod.api.items.type;

public interface IShears extends IToolBlockBreaking {
    @Override
    default TBBType<? extends IShears> getTbbType() {return TBBType.SHEARS;}
}
