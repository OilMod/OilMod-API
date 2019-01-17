package org.oilmod.api.ui;

import org.bukkit.inventory.ItemStack;
import org.oilmod.api.inventory.ItemFilter;

public interface ISlotState {
    void setItemStack(ItemStack itemStack);
    ItemStack getItemStack();
    Object getNMSSlotState();
    IUIInventory getUIInventory();
}
