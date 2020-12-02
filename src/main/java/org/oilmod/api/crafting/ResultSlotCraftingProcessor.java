package org.oilmod.api.crafting;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import org.jetbrains.annotations.NotNull;
import org.oilmod.api.rep.crafting.*;
import org.oilmod.api.rep.inventory.InventoryRep;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ResultSlotCraftingProcessor extends CraftingProcessorBase {

    public ResultSlotCraftingProcessor(@NotNull Map<IIngredientCategory, InventoryRep> supplierMap, @Nullable Object2ObjectMap<IIngredientCategory, List<InventoryRep>> reserveMap, Map<IResultCategory, InventoryRep> resultMap, @Nullable Object2ObjectMap<IResultCategory, List<InventoryRep>> overflowMap, ICraftingManager manager) {
        super(supplierMap, reserveMap, resultMap, overflowMap, manager);
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
