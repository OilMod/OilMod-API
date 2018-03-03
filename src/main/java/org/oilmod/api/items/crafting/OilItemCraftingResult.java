package org.oilmod.api.items.crafting;

import org.oilmod.api.items.OilItem;

/**
 * Used to return a specific OilItemStack defined by OilItem.getCreativeItems().
 */
public class OilItemCraftingResult extends ItemStackFactoryCraftingResult {
    public OilItemCraftingResult(OilItem item, int amount, int cId) {
        super(item.getCreativeItems()[cId], amount);
    }

    public OilItemCraftingResult(OilItem item, int amount) {
        this(item, amount, 0);
    }
}
