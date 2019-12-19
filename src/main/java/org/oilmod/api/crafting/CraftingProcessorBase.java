package org.oilmod.api.crafting;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.apache.commons.lang3.NotImplementedException;
import org.oilmod.api.rep.crafting.*;
import org.oilmod.api.rep.inventory.InventoryRep;

import java.util.Map;

public abstract class CraftingProcessorBase implements ICraftingProcessor {
    private final Map<IIngredientCategory, InventoryRep> supplierMap;
    private final Map<IResultCategory, InventoryRep> resultMap;
    private final ICraftingManager manager;
    private IRecipeRep last;

    public CraftingProcessorBase(Map<IIngredientCategory, InventoryRep> supplierMap, Map<IResultCategory, InventoryRep> resultMap, ICraftingManager manager) {
        this.supplierMap = supplierMap;
        this.resultMap = resultMap;
        this.manager = manager;
    }

    @Override
    public IIngredientSupplier getIngredients(IIngredientCategory category) {
        throw new NotImplementedException("todo");
        //return supplierMap.get(category);
    }

    @Override
    public InventoryRep getResultInventory(IResultCategory category) {
        return resultMap.get(category);
    }

    @Override
    public ICraftingState createCraftingState() {
        Map<IIngredientCategory, IIngredientSupplier> result = new Object2ObjectOpenHashMap<>(supplierMap.size());
        for (Map.Entry<IIngredientCategory, InventoryRep> entry:supplierMap.entrySet()) {
            InventoryRep inv = entry.getValue();
            int top = 0;
            int left = 0;
            int width = 0;
            int height = 0;
            //todo shapeless
            boolean flagTop = true;
            outer: for (int cTop = 0; cTop < inv.getHeight(); cTop++) {
                for (int cLeft = 0; cLeft < inv.getWidth(); cLeft++) {
                    if (!inv.getStored(cLeft, cTop).isEmpty()) {
                        if (flagTop) {
                            top = cTop;
                            height = 1;
                            flagTop =false;
                        } else {
                            height = cTop - top + 1;
                        }
                        continue outer;
                    }
                }
            }

            boolean flagLeft = true;
            outer: for (int cLeft = 0; cLeft < inv.getWidth(); cLeft++) {
                for (int cTop = top; cTop < top+height; cTop++) {
                    if (!inv.getStored(cLeft, cTop).isEmpty()) {
                        if (flagLeft) {
                            left = cLeft;
                            width = 1;
                            flagLeft =false;
                        } else {
                            width = cLeft - left + 1;
                        }
                        continue outer;
                    }
                }
            }
            System.out.printf("found top %d, height %d, left %d, width %d\n", top, height, left, width);
            if (width == 0 || height == 0)continue; //its empty
            result.put(entry.getKey(), new IngredientSupplierImpl(inv.createView2d(left, width, top, height), true));
        }


        return new CraftingStateImpl(result);
    }

    @Override
    public ICraftingManager getManager() {
        return manager;
    }

    @Override
    public RecipeLookupResult updateRecipe(boolean previewOnly) {
        ICraftingState craftingState = createCraftingState();

        RecipeLookupResult result =  last==null?manager.find(craftingState):manager.find(craftingState, last);
        if (result == null)return null;

        last = result.recipe;
        onUpdateRecipe(result);
        return result;
    }

    protected abstract void onUpdateRecipe(RecipeLookupResult lr);
}
