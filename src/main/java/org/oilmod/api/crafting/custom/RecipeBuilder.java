package org.oilmod.api.crafting.custom;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.rep.crafting.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RecipeBuilder {
    private final Map<IIngredientCategory, IMatcher> matchers = new Object2ObjectOpenHashMap<>();
    private final Map<IResultCategory, List<IResult>> results = new Object2ObjectOpenHashMap<>();



    public SupRecipeShaped shaped(IIngredientCategory category, Transformation... transformations) {
        return new SupRecipeShaped(category, transformations);
    }

    public class SupRecipeShaped{
        private final IIngredientCategory category;
        private int width;
        private final List<Transformation> transformations;
        private final List<List<IIngredient>> ingredients;

        public SupRecipeShaped(IIngredientCategory category, Transformation... transformations) {
            this.category = category;
            this.transformations = new ObjectArrayList<>(transformations);
            this.ingredients = new ObjectArrayList<>();

        }

        public SupRecipeShaped row(IIngredient... ingredients) {
            if (ingredients.length > width) {
                int diff = ingredients.length - width;
                for (List<IIngredient> column:this.ingredients) {
                    for (int i = 0; i < diff; i++) {
                        column.add(empty());
                    }
                }
                width = ingredients.length;
            }
            this.ingredients.add(new ObjectArrayList<>(ingredients));
            return this;
        }


        public SupRecipeShaped row(Object... ingredients) {
            IIngredient[] realIngredients = new IIngredient[ingredients.length];
            for (int i = 0; i < ingredients.length; i++) {
                realIngredients[i] = convert(ingredients[i]);
            }
            return row(realIngredients);
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return ingredients.size();
        }

        public RecipeBuilder ok() {
            Validate.isTrue(!matchers.containsKey(category), "there is already a matcher for that category");
            matchers.put(category, new ShapedMatcher(transformations.toArray(new Transformation[0]), ingredients.stream().map(u -> u.toArray(new IIngredient[0])).toArray(IIngredient[][]::new)));
            return RecipeBuilder.this;
        }
    }


    public SupRecipeShapeless shapeless(IIngredientCategory category) {
        return new SupRecipeShapeless(category);
    }

    public class SupRecipeShapeless{
        private final IIngredientCategory category;
        private final List<IIngredient> ingredients;

        public SupRecipeShapeless(IIngredientCategory category) {
            this.category = category;
            this.ingredients = new ObjectArrayList<>();

        }

        public SupRecipeShapeless row(IIngredient... ingredients) {
            this.ingredients.addAll(Arrays.asList(ingredients));
            return this;
        }

        public SupRecipeShapeless row(Object... ingredients) {
            IIngredient[] realIngredients = new IIngredient[ingredients.length];
            for (int i = 0; i < ingredients.length; i++) {
                realIngredients[i] = convert(ingredients[i]);
            }
            return row(realIngredients);
        }

        public int getAmount() {
            return ingredients.size();
        }

        public RecipeBuilder ok() {
            //shapelesses.add(this);
            throw new NotImplementedException("todo");
            //return RecipeBuilder.this;
        }
    }

    public RecipeBuilder results(IResultCategory category, IResult... results) {
        List<IResult> resultList = this.results.computeIfAbsent(category, c -> new ObjectArrayList<>());
        resultList.addAll(Arrays.asList(results));
        return this;
    }

    public CustomRecipe build() {
        return new CustomRecipe(matchers, results);
    }

    public static IIngredient empty() {
            throw new NotImplementedException("todo");
    }

    public static IIngredient convert(Object o) {
        throw new NotImplementedException("todo");
    }
}
