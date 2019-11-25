package org.oilmod.api.uiold;

import org.oilmod.api.inventory.ItemFilter;

import java.util.List;

@Deprecated
public interface IUIInventory {
    List<ISlotState> getInteractableSlots();
    ItemFilter getItemFilter();
    ISlotInteraction getInteraction();
    int getMaxStackSize();

}
