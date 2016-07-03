package de.sirati97.oilmod.api.items.crafting;

import de.sirati97.oilmod.api.util.Factory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sirati97 on 04.07.2016 for OilMod-Api.
 */
public class InterchangeableCraftingIngredient implements OilCraftingIngredient {
    private final OilCraftingIngredient[] ingredients;
    private final ICIDataFactory factory;

    /**
     * Use this class if you want to make specific slots of a shaped crafting recipe interchangeable. Create one instance of this and pass it for every interchangeable slot that should behave the same
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of OilCraftingIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItemBase&gt;</li>
     *                    <li>OilMod Item</li></ul>
     */
    public InterchangeableCraftingIngredient(Object... ingredients) {
        this(ItemCraftingFactory.toOilCraftingIngredients(ingredients));
    }
    /**
     * Use this class if you want to make specific slots of a shaped crafting recipe interchangeable. Create one instance of this and pass it for every interchangeable slot that should behave the same
     */
    public InterchangeableCraftingIngredient(OilCraftingIngredient... ingredients) {
        this.ingredients = ingredients;
        this.factory = new ICIDataFactory(ingredients.length);
    }


    @Override
    public boolean match(ItemStack itemStack, DataHolder dataHolder) {
        ICIData data = dataHolder.get(this, factory);
        for (int i = 0; i < ingredients.length; i++) {
            if (!data.getFlag(i)) {
                if (matchChild(i, itemStack, dataHolder)) {
                    data.setFlag(i);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean matchChild(int i, ItemStack itemStack, DataHolder dataHolder) {
        OilCraftingIngredient ingredient = ingredients[i];
        if (ingredient == null) {
            return itemStack == null || itemStack.getType().equals(Material.AIR) || itemStack.getAmount() < 0;
        } else {
            return ingredient.match(itemStack, dataHolder);
        }
    }

    @Override
    public ItemStack getRandomExample(Random rnd, DataHolder dataHolder) {
        ICIData data = dataHolder.get(this, factory);
        List<Integer> stillMissing = new ArrayList<>(data.getUnflaggedCount());
        for (int i = 0; i < ingredients.length; i++) {
            if (!data.getFlag(i)) {
                stillMissing.add(i);
            }
        }
        if (stillMissing.size() > 0) {
            int index = stillMissing.get(rnd.nextInt(stillMissing.size()));
            data.setFlag(index);
            return getRandomExampleChild(index, rnd, dataHolder);
        } else {
            throw createIllegalStateException();
        }
    }

    private ItemStack getRandomExampleChild(int i, Random rnd, DataHolder dataHolder) {
        OilCraftingIngredient ingredient = ingredients[i];
        if (ingredient == null) {
            return null;
        } else {
            return ingredient.getRandomExample(rnd, dataHolder);
        }
    }

    @Override
    public ItemStack onCrafted(ItemStack oldItemStack, DataHolder dataHolder) {
        ICIData data = dataHolder.get(this, factory);
        for (int i = 0; i < ingredients.length; i++) {
            if (!data.getFlag(i)) {
                if (matchChild(i, oldItemStack, dataHolder)) {
                    data.setFlag(i);
                    return onCraftedChild(i, oldItemStack, dataHolder);
                }
            }
        }
        throw createIllegalStateException();
    }

    @Override
    public boolean matchesNull() {
        return true;
    }

    private ItemStack onCraftedChild(int i, ItemStack oldItemStack, DataHolder dataHolder) {
        OilCraftingIngredient ingredient = ingredients[i];
        if (ingredient == null) {
            return oldItemStack;
        } else {
            return ingredient.onCrafted(oldItemStack, dataHolder);
        }
    }

    private IllegalStateException createIllegalStateException() {
        return new IllegalStateException((ingredients.length+1)+" random example was requested, but this InterchangeableCraftingIngredient only contains " + ingredients.length + " ingredients");
    }


    private static class ICIData {
        private final boolean[] flags;
        private int unflagged;

        private ICIData(int size) {
            this.flags = new boolean[size];
            this.unflagged = size;
        }

        public boolean getFlag(int i) {
            return flags[i];
        }

        public void setFlag(int i) {
            if (!getFlag(i)) {
                this.flags[i] = true;
                unflagged--;
            }
        }

        public int getUnflaggedCount() {
            return unflagged;
        }
    }

    private static class ICIDataFactory implements Factory<ICIData> {
        private final int size;

        private ICIDataFactory(int size) {
            this.size = size;
        }

        @Override
        public ICIData create() {
            return new ICIData(size);
        }
    }
}
