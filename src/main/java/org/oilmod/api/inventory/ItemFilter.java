package org.oilmod.api.inventory;


import org.oilmod.api.rep.itemstack.ItemStackRep;

/**
 * Created by sirati97 on 13.02.2016.
 */
public interface ItemFilter {
    boolean allowed(ItemStackRep itemStack);
}
