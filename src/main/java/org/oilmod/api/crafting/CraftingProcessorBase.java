package org.oilmod.api.crafting;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.oilmod.api.rep.crafting.*;
import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.api.rep.inventory.InventoryStoreState;
import org.oilmod.api.rep.itemstack.ItemStackConsumerRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import javax.annotation.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.oilmod.api.util.Util.printTrace;

public abstract class CraftingProcessorBase implements ICraftingProcessor {
    private final Map<IIngredientCategory, InventoryRep> supplierMap;
    @Nullable private final Object2ObjectMap<IIngredientCategory, List<InventoryRep>> reserveMap;
    private final Map<IResultCategory, InventoryRep> resultMap;
    @Nullable private final Map<IResultCategory, InventoryRep> resultPreviewMap;
    @Nullable private final Object2ObjectMap<IResultCategory, List<InventoryRep>> overflowMap;
    private final ICraftingManager manager;
    private RecipeLookupResult last;
    private boolean active;

    public CraftingProcessorBase(@NotNull Map<IIngredientCategory, InventoryRep> supplierMap, @Nullable Object2ObjectMap<IIngredientCategory, List<InventoryRep>> reserveMap, Map<IResultCategory, InventoryRep> resultMap, @Nullable  Object2ObjectMap<IResultCategory, List<InventoryRep>> overflowMap, ICraftingManager manager) {
        this.supplierMap = supplierMap;
        this.reserveMap = reserveMap;
        this.resultMap = resultMap;
        this.overflowMap = overflowMap;
        if (needPreviewShadowInv()) {
            this.resultPreviewMap = new Object2ObjectOpenHashMap<>();
            for (Map.Entry<IResultCategory, InventoryRep> entry:resultMap.entrySet()) {
                resultPreviewMap.put(entry.getKey(), entry.getValue().createSizeMirror());
            }
        } else {
            resultPreviewMap = Collections.emptyMap();
        }
        if (reserveMap != null) {
            reserveMap.replaceAll((cat, list) -> Collections.unmodifiableList(list));
        }
        if (reserveMap != null) {
            reserveMap.replaceAll((cat, list) -> Collections.unmodifiableList(list));
        }

        this.manager = manager;
    }

    protected boolean needPreviewShadowInv() {
        return true;
    }

    @Override
    public InventoryRep getPreviewInventory(IResultCategory category) {
        return resultPreviewMap==null?InventoryRep.EMPTY:resultPreviewMap.get(category);
    }

    @Override
    public List<InventoryRep> getOverflowInventories(IResultCategory category) {
        return overflowMap==null?Collections.emptyList():overflowMap.computeIfAbsent(category, ($) ->  Collections.emptyList());
    }

    @Override
    public List<InventoryRep> getReserveInventories(IIngredientCategory category) {
        return reserveMap==null?Collections.emptyList():reserveMap.computeIfAbsent(category, ($) ->  Collections.emptyList());
    }

    @Override
    public InventoryRep getIngredientInventory(IIngredientCategory category) {
        return supplierMap.getOrDefault(category, InventoryRep.EMPTY);
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
            //todo this should be possible without creating two classes, make createView2d be done by IngredientSupplier, and make the current area mutable. this will also allow implementing getIngredients. furthermore the size can be updated on inv manipulation, no brute force needed
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


    private boolean previewActive = false;
    protected void preview(int amount) {
        if (previewActive)return;
        previewActive = true;
        amount = onProcessCraft(last, (stack, testRun, max) -> true, null, null, amount, false, true, true);
    }

    public void previewRemove() {
        if (!previewActive)return;
        previewActive = false;
        for (IResultCategory category:last.recipe.getResultCategories()) {
            InventoryRep resultInv = getPreviewInventory(category);
            resultInv.clear();
        }
    }

    public int onProcessCraft(RecipeLookupResult lr, ItemStackConsumerRep consumerRep, IResultCategory resultToReplace, InventoryRep invReplaceWith, int max, boolean simulateOutput, boolean simulateInput, boolean preview) {
        if (lr == null || lr.recipe == null) {
            printTrace("Tried crafting without recipe. This indicated a bug");
            return 0;
        }

        int amount = checkOutput(lr, consumerRep, resultToReplace, invReplaceWith, max);
        if (amount == 0)return 0;
        amount = doInput(lr, consumerRep, amount, true);
        if (amount == 0)return 0;

        if (!simulateOutput || preview) {
            addOutput(lr, consumerRep, resultToReplace, invReplaceWith, amount, preview); //false&&preview
        }
        if (!simulateInput) {
            doInput(lr, consumerRep, amount, false);
        }

        return amount;
    }

    @Override
    public int tryCrafting(int amount, ItemStackConsumerRep consumerRep, IResultCategory resultToReplace, InventoryRep invReplaceWith, boolean simulate) {
        if (!active)return 0;
        RecipeLookupResult lr = getLast();
        return onProcessCraft(lr, consumerRep, resultToReplace, invReplaceWith, amount, simulate, simulate, false);
        //this can be improved theoretically as when shift crafting all slots free target slots should be usable as well. currently we will be constrained by the output slots stack limit
    }

    protected int checkOutput(RecipeLookupResult lr, ItemStackConsumerRep consumerRep, IResultCategory resultToReplace, InventoryRep invReplaceWith, int max) {
        int crafted = max;


        for (IResultCategory category:lr.recipe.getResultCategories()) {
            List<IResult> resultList = lr.recipe.getResultsCategory(category);
            InventoryRep mainResultInv = category==resultToReplace?invReplaceWith:getResultInventory(category);
            InventoryStoreState storeState = new InventoryStoreState(mainResultInv);
            ItemStackRep[] resultStacks = new ItemStackRep[resultList.size()];
            for (int i = 0, resultListSize = resultList.size(); i < resultListSize; i++) {
                resultStacks[i] = resultList.get(i).getResult( lr.craftingState, lr.checkState);
            }

            int unstoreable = storeState.store(resultStacks, crafted);
            crafted -= unstoreable; //reduce crafted by amount of failed (This is an up rounding int div)
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

    protected void addOutput(RecipeLookupResult lr, ItemStackConsumerRep consumerRep, IResultCategory resultToReplace, InventoryRep invReplaceWith, int amount, boolean preview) {

        for (IResultCategory category:lr.recipe.getResultCategories()) {
            List<IResult> resultList = lr.recipe.getResultsCategory(category);
            InventoryRep resultInv = category==resultToReplace ?
                    invReplaceWith :
                    preview?getPreviewInventory(category):getResultInventory(category);

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
