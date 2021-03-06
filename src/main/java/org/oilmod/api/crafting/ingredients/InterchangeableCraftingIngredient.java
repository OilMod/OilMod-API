package org.oilmod.api.crafting.ingredients;

import it.unimi.dsi.fastutil.ints.IntSortedSet;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.rep.crafting.IIngredient;
import org.oilmod.api.rep.crafting.IIngredientAccessor;
import org.oilmod.api.rep.crafting.IIngredientSupplier;
import org.oilmod.api.rep.itemstack.ItemStackConsumerRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.util.checkstate.ArrayState;
import org.oilmod.api.util.checkstate.ICheckState;
import org.oilmod.api.util.checkstate.immutable.ImmutableIntState;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

/**
 * Created by sirati97 on 04.07.2016 for OilMod-Api.
 */
public class InterchangeableCraftingIngredient implements IIngredient {
    private final IIngredient[] ingredients;



    @Override
    public Supplier<ItemStackRep> getExampleSupplier() {


        return null;
    }

    @Override
    public List<ItemStackRep> getExamples() {
        return null;
    }

    @Override
    public boolean check(IIngredientAccessor accessor, ICheckState checkState, int slotId, int slotCount, IntPredicate disclaimer, IntPredicate reclaimer) {
        checkState.requireMaxBackup(1);
        checkState.backupState();
        ArrayState<ImmutableIntState> flags = checkState.getTag(this, ImmutableIntState.FACTORY_ARRAY);
        flags.assureCreated(ingredients.length);
        //currently this algorithm is greedy and will fail, consider using the shapeless matching algorithm for this (needs to be adjusted as this here is happening in steps

        //in case this is a rematch, we need to make sure, we find that one
        for (int i = 0; i < ingredients.length; i++) {

            //this condition is in case we are asked the verify a previously already matched slot again
            //as we saved the slotid for the ingredient that matched with it, we will find it again
            if (flags.hasState(i) && flags.getOrCreateState(i).get() == slotId) {
                //we found a rematch, make sure its still valid
                if (ingredients[i].check(accessor, checkState, slotId, slotCount, disclaimer, reclaimer)) {
                    checkState.confirmState();
                    return true;
                } else {
                    checkState.revertState();
                    return false;
                }
            }
        }

        //now we can be sure that this is not a rematch we can check so far not used ingredients
        for (int i = 0; i < ingredients.length; i++) {
            if (!flags.hasState(i)) {
                //first time
                if (ingredients[i].check(accessor, checkState, slotId, slotCount, disclaimer, reclaimer)) {
                    flags.getOrCreateState(i).set(slotId);

                    checkState.confirmState();
                    return true;
                }
            }
        }
        checkState.revertState();
        return false;
    }

    @Override
    public boolean confirmState(IIngredientSupplier supplier, IntSortedSet slots, int current, int needed, ICheckState checkState, IntPredicate disclaimer) {
        return current == needed;
    }

    @Override
    public int consume(IIngredientAccessor accessor, int slotId, ItemStackConsumerRep stackConsumer, int multiplier, ICheckState checkState, boolean simulate) {
        ArrayState<ImmutableIntState> flags = checkState.getTag(this, ImmutableIntState.FACTORY_ARRAY);
        //here we are just using a previously written checkstate, it should already be valid!
        // this is a naive approach to verify it. if checkstate is partially created this will not catch it
        Validate.isTrue(flags.isInUse(), "Consume can only be called with a valid checkstate generated by consume");

        for (int i = 0; i < ingredients.length; i++) {

            if (flags.hasState(i) && flags.getOrCreateState(i).get() == slotId) {
                return ingredients[i].consume(accessor, slotId, stackConsumer, multiplier, checkState, simulate);
            }
        }

        throw new IllegalStateException("Either some of the ingredients were never matched, or most likely consume was called without a valid checkstate. make sure that ALL input categories are checked while given the same checkstate as this method to ensure proper initialisation of said");
    }

    @Override
    public boolean prepareRematch(ICheckState checkState) {
        return false;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public boolean isSingular() {
        return false;
    }

    @Override
    public boolean equals(IIngredient that) {
        return equals((Object)that);
    }

    @Override
    public int createHashCode() {
        return hashCode();
    }


    /**
     * Use this class if you want to make specific slots of a shaped crafting recipe interchangeable. Create one instance of this and pass it for every interchangeable slot that should behave the same
     * @param ingredients Ingredients can be the following:<ul>
     *                    <li>Instances of IIngredient</li>
     *                    <li>Bukkit Material</li>
     *                    <li>Bukkit ItemStack</li>
     *                    <li>Class&lt;? extends OilItem&gt;</li>
     *                    <li>OilMod Item</li></ul>
     */
    /*public InterchangeableCraftingIngredient(Object... ingredients) {
        this(ItemCraftingFactory.toIIngredients(ingredients));
    }*/
    /**
     * Use this class if you want to make specific slots of a shaped crafting recipe interchangeable. Create one instance of this and pass it for every interchangeable slot that should behave the same
     */
    public InterchangeableCraftingIngredient(IIngredient... ingredients) {
        this.ingredients = ingredients;
    }


    /*public ItemStackRep getRandomExample(Random rnd, DataHolder dataHolder) {
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

    private ItemStackRep getRandomExampleChild(int i, Random rnd, DataHolder dataHolder) {
        IIngredient ingredient = ingredients[i];
        if (ingredient == null) {
            return null;
        } else {
            return ingredient.getRandomExample(rnd, dataHolder);
        }
    }

    @Override
    public ItemStackRep onCrafted(ItemStackRep oldItemStack, DataHolder dataHolder) {
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

    private ItemStackRep onCraftedChild(int i, ItemStackRep oldItemStack, DataHolder dataHolder) {
        IIngredient ingredient = ingredients[i];
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
    }*/
}
