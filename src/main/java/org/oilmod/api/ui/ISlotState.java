package org.oilmod.api.ui;

import org.oilmod.api.rep.itemstack.ItemStackRep;

public interface ISlotState {
    void setItemStack(ItemStackRep itemStack);
    ItemStackRep getItemStack();
    Object getNMSSlotState();
    IUIInventory getUIInventory();
}
