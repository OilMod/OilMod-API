package org.oilmod.api.items.crafting;

import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.items.OilItemStackFactory;
import org.oilmod.api.rep.item.ItemRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.util.OilKey;

/**
 * Used to create OilMod Crafting Recipes and register them
 */
public final class ItemCraftingFactory {
    private ItemCraftingFactory(){}

    /**
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItem&gt;</li>
     *                    <li>OilMod Item</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapedRecipe(OilKey key, int width, int height, OilItemStackFactory result, int amount, Object... ingredients) {
        return createShapedRecipe(key, width, height, new ItemStackFactoryCraftingResult(result, amount), ingredients);
    }

    /**
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItem&gt;</li>
     *                    <li>OilMod Item</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapedRecipe(OilKey key, int width, int height, ItemStackRep vanillaResult, Object... ingredients) {
        return createShapedRecipe(key, width, height, new VanillaOilCraftingResult(vanillaResult), ingredients);
    }

    /**
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItem&gt;</li>
     *                    <li>OilMod Item</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapedRecipe(OilKey key, int width, int height, OilCraftingResult result, Object... ingredients) {
        return org.oilmod.api.items.internal.ItemCraftingFactory.getInstance().createShapedRecipe(key, width, height, result, toOilCraftingIngredients(ingredients));
    }

    /**
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItem&gt;</li>
     *                    <li>OilMod Item</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapelessRecipe(OilKey key, OilItemStackFactory result, int amount, Object... ingredients) {
        return createShapelessRecipe(key, new ItemStackFactoryCraftingResult(result, amount), ingredients);
    }

    /**
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItem&gt;</li>
     *                    <li>OilMod Item</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapelessRecipe(OilKey key, ItemStackRep vanillaResult, Object... ingredients) {
        return createShapelessRecipe(key, new VanillaOilCraftingResult(vanillaResult), ingredients);
    }

    /**
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItem&gt;</li>
     *                    <li>OilMod Item</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapelessRecipe(OilKey key, OilCraftingResult result, Object... ingredients) {
        return org.oilmod.api.items.internal.ItemCraftingFactory.getInstance().createShapelessRecipe(key, result, toOilCraftingIngredients(ingredients));
    }

    /**
     * Registers the recipe in the standard crafting table crafting manager
     */
    public static void registerGlobal(OilCraftingRecipe recipe) {
        org.oilmod.api.items.internal.ItemCraftingFactory.getInstance().registerGlobal(recipe);
    }

    public static OilCraftingIngredient[] toOilCraftingIngredients(Object... ingredients) {
        OilCraftingIngredient[] result = new OilCraftingIngredient[ingredients.length];
        for (int i=0;i<ingredients.length;i++) {
            result[i] = toOilCraftingIngredient(ingredients[i]);
        }
        return result;
    }


    private static final Class<OilItem> oilItemBaseClass = OilItem.class;
    private static final Class oilItemStackClass = OilItemStack.class;
    private static OilCraftingIngredient toOilCraftingIngredient(Object ingredient) {
        if (ingredient == null) {
            return null;
        } else if (ingredient instanceof OilCraftingIngredient) {
            return (OilCraftingIngredient) ingredient;
        } else if (ingredient instanceof ItemRep) {
            return new VanillaMaterialIngredient((ItemRep) ingredient);
        } else if (ingredient instanceof ItemStackRep && !(ingredient instanceof OilBukkitItemStack)) {
            return new VanillaItemStackIngredient((ItemStackRep) ingredient);
        } else if (ingredient instanceof OilItem) {
            return new OilModItemIngredient((OilItem) ingredient);
        } else if (ingredient instanceof Class) {
            Class<?> clazz = (Class) ingredient;
            if (oilItemBaseClass.isAssignableFrom(clazz)) {
                //noinspection unchecked
                return new OilModItemClassIngredient((Class<? extends OilItem>) ingredient);
            } else if (oilItemStackClass.isAssignableFrom(clazz)) {
                //noinspection unchecked,deprecation
                return new OilModItemStackClassIngredient((Class<? extends OilItemStack>) ingredient);
            }
        }
        throw new IllegalStateException("Invalid ingredient: unknown type " + ingredient.getClass().getName());
    }

}
