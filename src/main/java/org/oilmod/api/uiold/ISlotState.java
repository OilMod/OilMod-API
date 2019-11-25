package org.oilmod.api.uiold;

import org.oilmod.api.rep.itemstack.ItemStackRep;

@Deprecated
public interface ISlotState {
    void setItemStack(ItemStackRep itemStack);
    ItemStackRep getItemStack();
    Object getNMSSlotState();
    IUIInventory getUIInventory();
}
