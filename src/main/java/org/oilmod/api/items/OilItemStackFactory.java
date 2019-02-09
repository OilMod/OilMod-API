package org.oilmod.api.items;


import org.oilmod.api.rep.itemstack.ItemStackRep;

/**
 * Created by sirati97 on 06.03.2016.
 */
public interface OilItemStackFactory {
    /**
     * @return should return a new ItemStack
     */
    ItemStackRep create();
}
