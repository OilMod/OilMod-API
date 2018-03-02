package org.oilmod.api.items.crafting;

import org.oilmod.api.items.OilItemBase;

/**
 * Used to return a specific OilItemStack defined by OilItem.getCreativeItems().
 */
public class OilItemOilCraftingResult extends SpecificItemstackFactoryOilCraftingResult {
    public OilItemOilCraftingResult(OilItemBase item, int amount, int cId) {
        super(item.getCreativeItems()[cId], amount);
    }

    public OilItemOilCraftingResult(OilItemBase item, int amount) {
        this(item, amount, 0);
    }
}