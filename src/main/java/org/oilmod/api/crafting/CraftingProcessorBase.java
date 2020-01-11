package org.oilmod.api.crafting;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.apache.commons.lang3.NotImplementedException;
import org.oilmod.api.rep.crafting.*;
import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.api.rep.inventory.InventoryStoreState;
import org.oilmod.api.rep.itemstack.ItemStackConsumerRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.rep.world.LocationRep;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class CraftingProcessorBase implements ICraftingProcessor {
    private final Map<IIngredientCategory, InventoryRep> supplierMap;
    private final Map<IResultCategory, InventoryRep> resultMap;
    private final ICraftingManager manager;
    private RecipeLookupResult last;
    private boolean active;

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
            //System.out.printf("found top %d, height %d, left %d, width %d\n", top, height, left, width);
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

        RecipeLookupResult result =  last==null?manager.find(craftingState):manager.find(craftingState, last.recipe);
        if (result == null) {
            if (active) {
                active = false;
                onInactivate();
            }
            return null;
        }

        if (active && Objects.equals(last.recipe, result.recipe))return result;
        last = result;
        if (!active) {
            active = true;
            onActivate();
        }
        onUpdateRecipe(result);
        return result;
    }

    protected abstract void onUpdateRecipe(RecipeLookupResult lr);
    protected abstract void onInactivate();
    protected abstract void onActivate();



    public int onProcessCraft(RecipeLookupResult lr, ItemStackConsumerRep consumerRep, int max, boolean simulateOutput, boolean simulateInput) {
        int amount = checkOutput(lr, consumerRep, max);
        if (amount == 0)return 0;
        amount = doInput(lr, consumerRep, amount, true);
        if (amount == 0)return 0;

        if (!simulateOutput) {
            addOutput(lr, consumerRep, amount);
        }
        if (!simulateInput) {
            doInput(lr, consumerRep, amount, false);
        }

        return amount;
    }

    @Override
    public int tryCrafting(int amount, ItemStackConsumerRep consumerRep, boolean simulate) {
        if (!active)return 0;
        RecipeLookupResult lr = getLast();
        return onProcessCraft(lr, consumerRep, amount, simulate, simulate);
        //this can be improved theoretically as when shift crafting all slots free target slots should be usable as well. currently we will be constrained by the output slots stack limit
    }

    protected int checkOutput(RecipeLookupResult lr, ItemStackConsumerRep consumerRep, int max) {
        int crafted = max;


        for (IResultCategory category:lr.recipe.getResultCategories()) {
            List<IResult> resultList = lr.recipe.getResultsCategory(category);
            InventoryStoreState storeState = new InventoryStoreState(getResultInventory(category));
            for (IResult result:resultList) {
                ItemStackRep stack =  result.getResult( lr.craftingState, lr.checkState);
                int resultAmount = stack.getAmount();

                int unstoreable = storeState.store(stack, crafted);
                crafted -= (unstoreable + resultAmount - 1) / resultAmount; //reduce crafted by amount of failed (This is an up rounding int div)
            }
        }
        //this will fail if two categories share the same inventory space
        return  crafted;
    }


    protected int doInput(RecipeLookupResult lr, ItemStackConsumerRep consumerRep, int max, boolean simulate) {
        int crafted = max;




        lr.checkState.backupState(); //we do not want to keep any of the changes done in here, so save this one

        for (IIngredientCategory category:lr.recipe.getIngredientCategories()) {
            IMatcher matcher = lr.recipe.getMatcher(category);
            IIngredientSupplier supplier = lr.craftingState.getIngredients(category);

            crafted = matcher.process(supplier, lr.checkState, consumerRep, crafted, simulate);

        }
        lr.checkState.revertState(); //and go back to it

        return  crafted;
    }

    @Override
    public void onSlotTake() {

    }

    protected void addOutput(RecipeLookupResult lr, ItemStackConsumerRep consumerRep, int amount) {

        for (IResultCategory category:lr.recipe.getResultCategories()) {
            List<IResult> resultList = lr.recipe.getResultsCategory(category);
            InventoryRep resultInv = getResultInventory(category);

            for (IResult result:resultList) {
                ItemStackRep stack =  result.getResult( lr.craftingState, lr.checkState);
                stack.setAmount(stack.getAmount()* amount); //simulated in checkOutput
                stack = resultInv.store(stack);
                if (!stack.isEmpty()) {
                    consumerRep.consume(stack, 1, false);
                    //throw new IllegalStateException("Result inventory too small even though previous test indicated that stack would fit!");
                }
            }
        }
        //System.out.printf("onProcessCraft was called with max=%d resulting in %d crafts\n", max, crafted);
    }

    public RecipeLookupResult getLast() {
        return last;
    }
}
