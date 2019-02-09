package org.oilmod.api.inventory;


import org.oilmod.api.rep.itemstack.ItemStackRep;

/**
 * Created by sirati97 on 13.02.2016.
 */
public class AndAlsoItemFilter implements ItemFilter {
    private final ItemFilter[] filters;

    public AndAlsoItemFilter(ItemFilter... filters) {
        this.filters = filters;
    }

    @Override
    public boolean allowed(ItemStackRep itemStack) {
        for (ItemFilter filter:filters) {
            if (!filter.allowed(itemStack))return false;
        }
        return true;
    }
}
