package org.oilmod.api.crafting;

import org.oilmod.api.rep.crafting.*;
import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;

import java.util.List;
import java.util.Map;

public class ResultSlotCraftingProcessor extends CraftingProcessorBase {
    public ResultSlotCraftingProcessor(Map<IIngredientCategory, InventoryRep> supplierMap, Map<IResultCategory, InventoryRep> resultMap, ICraftingManager manager) {
        super(supplierMap, resultMap, manager);
    }

    @Override
    protected void onUpdateRecipe(RecipeLookupResult lr) {
        preview(1);
    }

    @Override
    public void onSlotTake() {
        super.onSlotTake();
        tryCrafting(1, (stack, testRun, max) -> true, false);
    }

    @Override
    public void afterSlotTake() {
        preview(1);
    }

    @Override
    public boolean isCraftOnTake() {
        return true;
    }

    @Override
    protected void onInactivate() {
        previewRemove();
    }

    @Override
    protected void onActivate() {
        preview(1);
    }

}
