package org.oilmod.api.items.crafting;



import org.oilmod.api.rep.crafting.IIngredient;
import org.oilmod.api.rep.crafting.IIngredientAccessor;
import org.oilmod.api.rep.itemstack.ItemStackConsumerRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.rep.itemstack.state.ItemStackStateRep;
import org.oilmod.api.util.checkstate.ICheckState;

import java.util.List;
import java.util.Random;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

/**
 * Created by sirati97 on 24.03.2016.
 */
public interface OilCraftingIngredient extends IIngredient {
    boolean match(ItemStackRep itemStack, DataHolder dataHolder);
    ItemStackRep getRandomExample(Random rnd, DataHolder dataHolder);
    ItemStackRep onCrafted(ItemStackRep oldItemStack, DataHolder dataHolder);
    boolean matchesNull();

    default Supplier<ItemStackRep> getExampleSupplier() {
        return null;
    }

    default List<ItemStackRep> getExamples() {
        return null;
    }

    //todo remove this mess below again, just for testing without having to implement ingredients yet
    @Override
    default boolean check(IIngredientAccessor accessor, ICheckState checkState, int slotId, int slotCount, IntPredicate disclaimer, IntPredicate reclaimer) {
        //yes this is horrible, thats why it needs to be replaced anyway
        return match(accessor.getItemState().createStack(accessor.getTotalMatched(1)), null);
    }

    @Override
    default int consume(IIngredientAccessor accessor, int slotId, ItemStackConsumerRep stackConsumer, int multiplier, ICheckState checkState, boolean simulate) {
        ItemStackStateRep state = accessor.getItemState().getProvidedItemStackState();
        ItemStackRep rep = state.createStack(accessor.getTotalMatched(multiplier)); //yes this is horrible, thats why it needs to be replaced anyway
        ItemStackRep newRep =  onCrafted(rep, null);
        if (newRep.isSimilar(state) || newRep.isEmpty()) {
            int sizeDiff = accessor.getTotalMatched(multiplier) - newRep.getAmount();
            multiplier = Math.min(multiplier, accessor.getTotalMatched(multiplier)/sizeDiff);
            multiplier = Math.min(multiplier, accessor.use(sizeDiff * multiplier, simulate));
        } else {
            multiplier = Math.min(multiplier, accessor.getTotalMatched(multiplier));
            multiplier = Math.min(multiplier, accessor.use(multiplier, newRep, stackConsumer, simulate)); //todo this kinda ignored stacksize of replaced item
        }
        return multiplier;
    }
    /**
     * @return true if matcher is only able to match 1! item/item variant
     */
    default boolean isStatic() {
        return true;
    }

    /**
     * @return true if matcher does not have state. that means only ever considers the current input
     */
    default boolean isSingular() {
        return true;
    }

    /**
     * Please also write equals and getHashCode, this allows for some shortcuts in shapeless matching
     */
    default boolean equals(IIngredient that) {
        return equals((Object)that);
    }

    /**
     * Please also write equals and getHashCode, this allows for some shortcuts in shapeless matching
     */
    default int createHashCode() {
        return hashCode();
    }
}
