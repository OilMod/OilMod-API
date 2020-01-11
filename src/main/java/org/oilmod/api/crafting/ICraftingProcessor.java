package org.oilmod.api.crafting;

import org.oilmod.api.rep.crafting.*;
import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.api.rep.itemstack.ItemStackConsumerRep;
import org.oilmod.api.rep.itemstack.state.Inventory;

public interface ICraftingProcessor {
    IIngredientSupplier getIngredients(IIngredientCategory category);
    InventoryRep getResultInventory(IResultCategory category);
    ICraftingState createCraftingState();
    ICraftingManager getManager();
    RecipeLookupResult updateRecipe(boolean previewOnly);
    void onSlotTake();
    int tryCrafting(int amount, ItemStackConsumerRep consumerRep, boolean simulate);
    boolean isCraftOnTake();
}
