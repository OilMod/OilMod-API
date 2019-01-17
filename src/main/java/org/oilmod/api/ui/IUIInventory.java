package org.oilmod.api.ui;

import org.oilmod.api.inventory.ItemFilter;

import java.util.List;

public interface IUIInventory {
    List<ISlotState> getInteractableSlots();
    ItemFilter getItemFilter();
    ISlotInteraction getInteraction();
    int getMaxStackSize();

}
