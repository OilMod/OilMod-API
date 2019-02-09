package org.oilmod.api.items.crafting;

import org.oilmod.api.items.OilItemStackFactory;
import org.oilmod.api.rep.itemstack.ItemStackRep;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class ItemStackFactoryCraftingResult implements OilCraftingResult {
    private final OilItemStackFactory itemstackFactory;
    private final int amount;
    private ItemStackRep result;

    public ItemStackFactoryCraftingResult(OilItemStackFactory itemstackFactory, int amount) {
        this.itemstackFactory = itemstackFactory;
        this.amount = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStackRep preCraftResult(ItemStackRep[] matrix, boolean shaped, int width, int height) {
        return getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void craftResult(ItemStackRep result, ItemStackRep[] matrix, boolean shaped, int width, int height) {
        this.result = null;
    }

    public ItemStackRep getResult() {
        if (result == null) {
            result = itemstackFactory.create();
            result.setAmount(amount);
        }
        return result;
    }
}
