package org.oilmod.api.items.type;

public interface IPickaxe extends IToolBlockBreaking {
    @Override
    default TBBType<? extends IPickaxe> getTbbType() {return TBBType.PICKAXE;}

    int getPickaxeStrength();
}
