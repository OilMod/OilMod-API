package de.sirati97.oilmod.api.items.crafting;

import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilSpecificItemstackFactory;
import de.sirati97.oilmod.api.items.internal.ItemCraftingFactoryBase;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 24.03.2016.
 */
public final class ItemCraftingFactory {
    private ItemCraftingFactory(){}

    public static OilCraftingRecipe createShapedRecipe(int width, int height, OilSpecificItemstackFactory result, Object... ingredients) {
        return createShapedRecipe(width, height, new OilCraftingResultImpl(result), ingredients);
    }

    public static OilCraftingRecipe createShapedRecipe(int width, int height, OilCraftingResult result, Object... ingredients) {
        return ItemCraftingFactoryBase.getInstance().createShapedRecipe(width, height, result, toOilItemComparators(ingredients));
    }

    public static void registerGlobal(OilCraftingRecipe recipe) {
        ItemCraftingFactoryBase.getInstance().registerGlobal(recipe);
    }

    private static OilCraftingComparator[] toOilItemComparators(Object[] ingredients) {
        OilCraftingComparator[] result = new OilCraftingComparator[ingredients.length];
        for (int i=0;i<ingredients.length;i++) {
            result[i] = toOilItemComparator(ingredients[i]);
        }
        return result;
    }


    private static OilCraftingComparator toOilItemComparator(Object ingredient) {
        if (ingredient == null) {
            return null;
        } else if (ingredient instanceof OilCraftingComparator) {
            return (OilCraftingComparator) ingredient;
        } else if (ingredient instanceof Material) {
            return new VanillaMaterialOilCraftingComparator((Material) ingredient);
        } else if (ingredient instanceof ItemStack && !(ingredient instanceof OilBukkitItemStack)) {
            return new VanillaItemStackOilCraftingComparator((ItemStack) ingredient);
        } else if (ingredient instanceof Class) {
            try {
                return new ModItemOilCraftingComparator((Class<? extends OilItemBase>) ingredient);
            } catch (ClassCastException ex) {}
        }
        throw new IllegalStateException("Invalid ingredient: unknown type " + ingredient.getClass().getName());
    }

}
