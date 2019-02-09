package org.oilmod.api.items.type;

public interface IShears extends IToolBlockBreaking {
    @Override
    default TBBType getTbbType() {return TBBType.SHEARS;}
}
