package org.oilmod.api.ui;

import org.oilmod.api.inventory.ItemFilter;
import org.oilmod.api.rep.inventory.InventoryRep;

public final class UI {
    private static UIManagerHelper uiManager;

    public static abstract class UIManagerHelper {
        public abstract ISlotState createSlot(IUIInventory uiInventory);
        public abstract ISlotState[] createSlots(IUIInventory uiInventory, int size);
        public abstract IUIInventory createUIInventory(int size, ISlotInteraction interaction, ItemFilter filter, int maxstack);

        public abstract IUIInventory createUIInventory(InventoryRep inventory);
    }

    public static ISlotState createSlot(IUIInventory uiInventory) {
        return uiManager.createSlot(uiInventory);
    }

    public static ISlotState[] createSlots(IUIInventory uiInventory, int size) {
        return uiManager.createSlots(uiInventory, size);
    }

    public static IUIInventory createUIInventory(int size, ISlotInteraction interaction, ItemFilter filter, int maxstack) {
        return uiManager.createUIInventory(size, interaction, filter, maxstack);
    }

    public static IUIInventory createUIInventory(InventoryRep inventory) {
        return uiManager.createUIInventory(inventory);
    }


}
