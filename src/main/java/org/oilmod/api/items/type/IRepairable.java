package org.oilmod.api.items.type;

import org.bukkit.inventory.ItemStack;
import org.oilmod.api.items.OilItemStack;

public interface IRepairable extends IItemGeneric {
    boolean isRepairable(OilItemStack toRepair, ItemStack repair);
}
