package org.oilmod.api.items.type;


import org.oilmod.api.items.OilItemStack;

public interface IPickaxe extends IToolBlockBreaking {
    @Override
    default TBBType getTbbType() {return TBBType.PICKAXE.getValue();}

    @Override
    int getToolStrength(OilItemStack stack, TBBType tooltype);
}
