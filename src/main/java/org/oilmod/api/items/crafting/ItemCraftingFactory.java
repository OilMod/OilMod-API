package org.oilmod.api.items.crafting;

import org.oilmod.api.items.internal.ItemCraftingFactoryBase;
import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.api.items.OilItemBase;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.items.OilSpecificItemStackFactory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
     *                    <li>Class&lt;? extends OilItemBase&gt;</li>
     *                    <li>OilMod Item</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapedRecipe(int width, int height, OilSpecificItemStackFactory result, int amount, Object... ingredients) {
        return createShapedRecipe(width, height, new SpecificItemstackFactoryOilCraftingResult(result, amount), ingredients);
    }

    /**
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItemBase&gt;</li>
     *                    <li>OilMod Item</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapedRecipe(int width, int height, ItemStack vanillaResult, Object... ingredients) {
        return createShapedRecipe(width, height, new VanillaOilCraftingResult(vanillaResult), ingredients);
    }

    /**
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItemBase&gt;</li>
     *                    <li>OilMod Item</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapedRecipe(int width, int height, OilCraftingResult result, Object... ingredients) {
        return ItemCraftingFactoryBase.getInstance().createShapedRecipe(width, height, result, toOilCraftingIngredients(ingredients));
    }

    /**
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItemBase&gt;</li>
     *                    <li>OilMod Item</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapelessRecipe(OilSpecificItemStackFactory result, int amount, Object... ingredients) {
        return createShapelessRecipe(new SpecificItemstackFactoryOilCraftingResult(result, amount), ingredients);
    }

    /**
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItemBase&gt;</li>
     *                    <li>OilMod Item</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapelessRecipe(ItemStack vanillaResult, Object... ingredients) {
        return createShapelessRecipe(new VanillaOilCraftingResult(vanillaResult), ingredients);
    }

    /**
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItemBase&gt;</li>
     *                    <li>OilMod Item</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapelessRecipe(OilCraftingResult result, Object... ingredients) {
        return ItemCraftingFactoryBase.getInstance().createShapelessRecipe(result, toOilCraftingIngredients(ingredients));
    }

    /**
     * Registers the recipe in the standard crafting table crafting manager
     */
    public static void registerGlobal(OilCraftingRecipe recipe) {
        ItemCraftingFactoryBase.getInstance().registerGlobal(recipe);
    }

    public static OilCraftingIngredient[] toOilCraftingIngredients(Object... ingredients) {
        OilCraftingIngredient[] result = new OilCraftingIngredient[ingredients.length];
        for (int i=0;i<ingredients.length;i++) {
            result[i] = toOilCraftingIngredient(ingredients[i]);
        }
        return result;
    }


    private static final Class<OilItemBase> oilItemBaseClass = OilItemBase.class;
    private static final Class<OilItemStack> oilItemStackClass = OilItemStack.class;
    private static OilCraftingIngredient toOilCraftingIngredient(Object ingredient) {
        if (ingredient == null) {
            return null;
        } else if (ingredient instanceof OilCraftingIngredient) {
            return (OilCraftingIngredient) ingredient;
        } else if (ingredient instanceof Material) {
            return new VanillaMaterialOilCraftingIngredient((Material) ingredient);
        } else if (ingredient instanceof ItemStack && !(ingredient instanceof OilBukkitItemStack)) {
            return new VanillaItemStackOilCraftingIngredient((ItemStack) ingredient);
        } else if (ingredient instanceof OilItemBase) {
            return new ModItemOilCraftingIngredient((OilItemBase) ingredient);
        } else if (ingredient instanceof Class) {
            Class<?> clazz = (Class) ingredient;
            if (oilItemBaseClass.isAssignableFrom(clazz)) {
                //noinspection unchecked
                return new ModItemClassOilCraftingIngredient((Class<? extends OilItemBase>) ingredient);
            } else if (oilItemStackClass.isAssignableFrom(clazz)) {
                //noinspection unchecked,deprecation
                return new ModItemStackClassOilCraftingIngredient((Class<? extends OilItemStack>) ingredient);
            }
        }
        throw new IllegalStateException("Invalid ingredient: unknown type " + ingredient.getClass().getName());
    }

}
