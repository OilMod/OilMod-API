package org.oilmod.api.items.crafting;



import org.oilmod.api.rep.itemstack.ItemStackRep;

import java.util.Random;

/**
 * Created by sirati97 on 24.03.2016.
 */
public interface OilCraftingIngredient {
    boolean match(ItemStackRep itemStack, DataHolder dataHolder);
    ItemStackRep getRandomExample(Random rnd, DataHolder dataHolder);
    ItemStackRep onCrafted(ItemStackRep oldItemStack, DataHolder dataHolder);
    boolean matchesNull();
}
