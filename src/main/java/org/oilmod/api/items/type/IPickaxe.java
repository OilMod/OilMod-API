package org.oilmod.api.items.type;


public interface IPickaxe extends IToolBlockBreaking {
    @Override
    default TBBType getTbbType() {return TBBType.PICKAXE;}

    int getPickaxeStrength();
}
