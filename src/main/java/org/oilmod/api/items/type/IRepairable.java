package org.oilmod.api.items.type;

import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.rep.itemstack.ItemStackRep;

public interface IRepairable extends IItemGeneric {
    boolean isRepairable(OilItemStack toRepair, ItemStackRep repair);
}
