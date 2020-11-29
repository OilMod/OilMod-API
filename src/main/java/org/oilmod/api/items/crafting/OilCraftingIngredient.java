package org.oilmod.api.items.crafting;



import org.oilmod.api.rep.crafting.ICraftingState;
import org.oilmod.api.rep.crafting.IIngredient;
import org.oilmod.api.rep.itemstack.ItemStackConsumerRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.rep.itemstack.state.ItemStackStateRep;
import org.oilmod.api.util.checkstate.ICheckState;

import java.util.List;
import java.util.Random;
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
    default boolean check(ItemStackRep rep, ICheckState checkState) {
        return match(rep, null);
    }

    default int consume(ItemStackRep rep, ItemStackConsumerRep stackConsumer, int multiplier, int maxStack, ICheckState checkState, boolean simulate) {
        ItemStackStateRep state = rep.getItemStackState();
        for (int i = 0; i < multiplier; i++) {//this should be doable with math instead of for loop //actually not for container items
            ItemStackRep newRep =  onCrafted(rep, null);
            if (!newRep.isSimilar(state) || newRep.isEmpty()) {
                newRep.getProvidedItemStackState().applyTo(rep, false, true);
                return ++i;
            }
        }
        return multiplier;//no good way to support multiplier
    }
}
