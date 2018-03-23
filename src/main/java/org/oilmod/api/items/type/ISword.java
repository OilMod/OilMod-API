package org.oilmod.api.items.type;

public interface ISword extends IWeapon, IToolBlockBreaking {
    @Override
    default TBBType<? extends ISword> getTbbType() {return TBBType.SWORD;}
}