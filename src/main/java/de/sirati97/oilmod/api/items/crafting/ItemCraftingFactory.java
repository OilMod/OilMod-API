package de.sirati97.oilmod.api.items.crafting;

import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilSpecificItemstackFactory;
import de.sirati97.oilmod.api.items.internal.ItemCraftingFactoryBase;
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
     *                    <li>Class&lt;? extends OilItemBase&gt;</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapedRecipe(int width, int height, OilSpecificItemstackFactory result, int amount, Object... ingredients) {
        return createShapedRecipe(width, height, new SpecificItemstackFactoryOilCraftingResult(result, amount), ingredients);
    }

    /**
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItemBase&gt;</li></ul>
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
     *                    <li>Class&lt;? extends OilItemBase&gt;</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapedRecipe(int width, int height, OilCraftingResult result, Object... ingredients) {
        return ItemCraftingFactoryBase.getInstance().createShapedRecipe(width, height, result, toOilItemComparators(ingredients));
    }

    /**
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItemBase&gt;</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapelessRecipe(OilSpecificItemstackFactory result, int amount, Object... ingredients) {
        return createShapelessRecipe(new SpecificItemstackFactoryOilCraftingResult(result, amount), ingredients);
    }

    /**
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItemBase&gt;</li></ul>
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
     *                    <li>Class&lt;? extends OilItemBase&gt;</li></ul>
     * @return instance of OilCraftingRecipe
     */
    public static OilCraftingRecipe createShapelessRecipe(OilCraftingResult result, Object... ingredients) {
        return ItemCraftingFactoryBase.getInstance().createShapelessRecipe(result, toOilItemComparators(ingredients));
    }

    /**
     * Registers the recipe in the standard crafting table crafting manager
     */
    public static void registerGlobal(OilCraftingRecipe recipe) {
        ItemCraftingFactoryBase.getInstance().registerGlobal(recipe);
    }

    private static OilCraftingIngredient[] toOilItemComparators(Object[] ingredients) {
        OilCraftingIngredient[] result = new OilCraftingIngredient[ingredients.length];
        for (int i=0;i<ingredients.length;i++) {
            result[i] = toOilItemComparator(ingredients[i]);
        }
        return result;
    }


    private static OilCraftingIngredient toOilItemComparator(Object ingredient) {
        if (ingredient == null) {
            return null;
        } else if (ingredient instanceof OilCraftingIngredient) {
            return (OilCraftingIngredient) ingredient;
        } else if (ingredient instanceof Material) {
            return new VanillaMaterialOilCraftingIngredient((Material) ingredient);
        } else if (ingredient instanceof ItemStack && !(ingredient instanceof OilBukkitItemStack)) {
            return new VanillaItemStackOilCraftingIngredient((ItemStack) ingredient);
        } else if (ingredient instanceof Class) {
            try {
                return new ModItemOilCraftingIngredient((Class<? extends OilItemBase>) ingredient);
            } catch (ClassCastException ex) {}
        }
        throw new IllegalStateException("Invalid ingredient: unknown type " + ingredient.getClass().getName());
    }

}
