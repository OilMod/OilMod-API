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
        onProcessCraft(lr, (stack, testRun, max) -> true, 1);
    }

    @Override
    protected void onInactivate() {
        for (IResultCategory category:this.getManager().getResultCategories()) {
            InventoryRep resultInv = getResultInventory(category);
            resultInv.clear();
        }
    }

    @Override
    protected void onActivate() {

    }
}
