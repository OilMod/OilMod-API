package org.oilmod.api.ui;

import org.bukkit.inventory.Inventory;
import org.oilmod.api.inventory.ItemFilter;

public final class UI {
    private static UIManagerHelper uiManager;

    public static abstract class UIManagerHelper {
        public abstract ISlotState createSlot(IUIInventory uiInventory);
        public abstract ISlotState[] createSlots(IUIInventory uiInventory, int size);
        public abstract IUIInventory createUIInventory(int size, ISlotInteraction interaction, ItemFilter filter, int maxstack);

        public abstract IUIInventory createUIInventory(Inventory inventory);
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

    public static IUIInventory createUIInventory(Inventory inventory) {
        return uiManager.createUIInventory(inventory);
    }


}
