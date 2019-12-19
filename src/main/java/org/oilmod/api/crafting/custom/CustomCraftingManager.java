package org.oilmod.api.crafting.custom;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.crafting.custom.RecipeSizeKey.Mask;
import org.oilmod.api.rep.crafting.*;
import org.oilmod.api.util.checkstate.CheckState;
import org.oilmod.api.util.checkstate.ICheckState;

import java.util.*;

public class CustomCraftingManager implements ICraftingManager {
    private final List<IIngredientCategory> ingredientCategories;
    private final IIngredientCategory[] ingredientCategoriesArray; //needed for mask creation
    private final List<IResultCategory> resultCategories;
    private final List<IRecipeRep> recipesAll = new ObjectArrayList<>();
    private final Map<RecipeSizeKey, List<IRecipeRep>> recipesPerSize = new Object2ObjectOpenHashMap<>();

    public CustomCraftingManager(IIngredientCategory[] ingredientCategories, IResultCategory[] resultCategories) {
        if (ingredientCategories.length > 62)throw new IllegalArgumentException("Cannot have more than 62 categories");
        this.ingredientCategoriesArray = ingredientCategories;
        this.ingredientCategories = Collections.unmodifiableList(Arrays.asList(ingredientCategories));
        this.resultCategories = Collections.unmodifiableList(Arrays.asList(resultCategories));
    }

    @Override
    public int getIngredientCategoriesCount() {
        return ingredientCategoriesArray.length;
    }

    @Override
    public List<IIngredientCategory> getIngredientCategories() {
        return ingredientCategories;
    }

    @Override
    public List<IResultCategory> getResultCategories() {
        return resultCategories;
    }

    @Override
    public RecipeLookupResult find(ICraftingState state, IRecipeRep... cache) {
        if (state.getSupplierCount() == 0)return null;
        ICheckState checkState = new CheckState();

        IIngredientSupplier[] suppliers = new IIngredientSupplier[ingredientCategoriesArray.length];
        for (int i = 0; i < ingredientCategoriesArray.length; i++) {
            suppliers[i] = state.getIngredients(ingredientCategoriesArray[i]);
        }
        RecipeLookupResult result = checkRecipes(state, checkState, suppliers, Arrays.asList(cache));
        if (result != null) return result;
        Mask identifier = new Mask(suppliers);

        do {
            result = checkRecipes(state, checkState, suppliers, recipesPerSize.get(identifier));
            if (result != null) return result;
        } while (identifier.nextMask());
        return null;
    }

    protected RecipeLookupResult checkRecipes(ICraftingState state, ICheckState checkState, IIngredientSupplier[] suppliers, List<IRecipeRep> recipes) {
        if (recipes != null) {
            recipeLoop: for(IRecipeRep recipe:recipes) {
                for (int i = 0; i < suppliers.length; i++) {
                    IMatcher matcher = recipe.getMatcher(ingredientCategoriesArray[i]); //can make this faster if we use an array instead of a map (requires us to sort the array to align with order here
                    if (!matcher.check(suppliers[i], checkState)) {
                        checkState.reset();
                        continue recipeLoop;
                    }
                }
                return new RecipeLookupResult(recipe, state, checkState);
            }
        }
        return null;
    }

    @Override
    public Collection<IRecipeRep> getAllRecipes() {
        return recipesAll;
    }

    @Override
    public void add(IRecipeRep recipe) {
        Validate.isTrue(ingredientCategories.containsAll(recipe.getIngredientCategories()), "Recipe contains ingredient category not supported by type of crafting");
        Validate.isTrue(resultCategories.containsAll(recipe.getResultCategories()), "Recipe contains ingredient category not supported by type of crafting");
        recipesAll.add(recipe);
        recipesPerSize.computeIfAbsent(new RecipeSizeKey(recipe, ingredientCategoriesArray), recipeSizeKey -> new ObjectArrayList<>()).add(recipe);
    }

    @Override
    public void remove(IRecipeRep recipe) {
        recipesAll.remove(recipe); //expensive
        RecipeSizeKey key = new RecipeSizeKey(recipe, ingredientCategoriesArray);
        List<IRecipeRep> recipes = recipesPerSize.get(key);
        if (recipes != null) {
            recipes.remove(recipe);
            if (recipes.size() == 0) {
                recipesPerSize.remove(key);
            }
        }
    }

    //todo partically matched state
}
