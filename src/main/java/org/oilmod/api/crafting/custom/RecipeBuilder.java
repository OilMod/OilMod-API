package org.oilmod.api.crafting.custom;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
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
        Validate.isTrue(!matchers.containsKey(category), "there is already a matcher for that category"); //we should fail early!
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


    public SupRecipeShapedPattern shapedPattern(IIngredientCategory category, char empty, Transformation... transformations) {
        Validate.isTrue(!matchers.containsKey(category), "there is already a matcher for that category"); //we should fail early!
        return new SupRecipeShapedPattern(category, empty, transformations);
    }

    public class SupRecipeShapedPattern  {
        private final IIngredientCategory category;
        private int width;
        private final List<Transformation> transformations;
        private final List<String> rows;
        private final Char2ObjectMap<IIngredient> ingredientMap;

        public SupRecipeShapedPattern(IIngredientCategory category, char empty, Transformation... transformations) {
            this.category = category;
            this.transformations = new ObjectArrayList<>(transformations);
            this.rows = new ObjectArrayList<>();
            this.ingredientMap = new Char2ObjectOpenHashMap<>();
            ingre(empty, empty());
        }

        public SupRecipeShapedPattern row(String ingredients) {
            width = Math.max(width, ingredients.length());
            this.rows.add(ingredients);
            return this;
        }

        public SupRecipeShapedPattern ingre(char character, IIngredient ingredient) {
            if (ingredientMap.putIfAbsent(character, ingredient) != ingredientMap.defaultReturnValue()) {
                throw new IllegalArgumentException(String.format("There is already an ingredient associated with the character: %c", character));
            }
            return this;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return rows.size();
        }

        public RecipeBuilder ok() {
            Validate.isTrue(!matchers.containsKey(category), "there is already a matcher for that category");
            IIngredient[][] ingredients = new IIngredient[getHeight()][];
            for (int i = 0; i < ingredients.length; i++) {
                IIngredient[] ingreRow = new IIngredient[getWidth()];
                ingredients[i] = ingreRow;
                String strRow = rows.get(i);
                for (int j = 0; j < ingreRow.length; j++) {
                    if (j < strRow.length()) {
                        ingreRow[j] = ingredientMap.computeIfAbsent(strRow.charAt(j), c -> {throw new IllegalStateException(String.format("Missing ingredient for character: %c", c));});
                    } else {
                        ingreRow[j] = empty();
                    }
                }
            }


            matchers.put(category, new ShapedMatcher(transformations.toArray(new Transformation[0]), ingredients));
            return RecipeBuilder.this;
        }
    }


    public SupRecipeShapeless shapeless(IIngredientCategory category) {
        Validate.isTrue(!matchers.containsKey(category), "there is already a matcher for that category"); //we should fail early!
        return new SupRecipeShapeless(category);
    }

    public class SupRecipeShapeless{
        private final IIngredientCategory category;
        private final List<IIngredient> ingredients;

        public SupRecipeShapeless(IIngredientCategory category) {
            this.category = category;
            this.ingredients = new ObjectArrayList<>();

        }

        public SupRecipeShapeless add(IIngredient... ingredients) {
            this.ingredients.addAll(Arrays.asList(ingredients));
            return this;
        }

        public SupRecipeShapeless add(Object... ingredients) {
            IIngredient[] realIngredients = new IIngredient[ingredients.length];
            for (int i = 0; i < ingredients.length; i++) {
                realIngredients[i] = convert(ingredients[i]);
            }
            return add(realIngredients);
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
        return IIngredient.EMPTY;
    }

    public static IIngredient convert(Object o) {
        throw new NotImplementedException("todo");
    }
}
