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
        for (IResultCategory category:lr.recipe.getResultCategories()) {
            List<IResult> resultList = lr.recipe.getResultsCategory(category);
            InventoryRep resultInv = getResultInventory(category);
            resultInv.clear();
            for (IResult result:resultList) {
                ItemStackRep stack =  result.getResult( lr.craftingState, lr.checkState);
                stack = resultInv.store(stack);
                if (!stack.isEmpty()) {
                    throw new IllegalStateException("Result inventory too small!");
                }
            }

        }
    }
}
