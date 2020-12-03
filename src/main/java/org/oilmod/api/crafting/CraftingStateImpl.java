package org.oilmod.api.crafting;

import org.oilmod.api.rep.crafting.ICraftingState;
import org.oilmod.api.rep.crafting.IIngredientCategory;
import org.oilmod.api.rep.crafting.IIngredientSupplier;
import org.oilmod.api.rep.crafting.IRecipeRep;

import java.util.Map;

public class CraftingStateImpl implements ICraftingState {
    private final Map<IIngredientCategory, IIngredientSupplier> supplierMap;

    public CraftingStateImpl(Map<IIngredientCategory, IIngredientSupplier> supplierMap) {
        this.supplierMap = supplierMap;
    }

    @Override
    public IIngredientSupplier getIngredients(IIngredientCategory category) {
        return supplierMap.get(category);
    }

    @Override
    public int getSupplierCount() {
        return supplierMap.size();
    }

    @Override
    public IRecipeRep getRecipe() {
        return null; //todo: what
    }
}
