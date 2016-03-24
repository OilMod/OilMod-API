package de.sirati97.oilmod.api.items.crafting;

import de.sirati97.oilmod.api.items.OilItemBase;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class OilItemOilCraftingResult extends SpecificItemstackFactoryOilCraftingResult {
    public OilItemOilCraftingResult(OilItemBase item, int amount, int cId) {
        super(item.getCreativeItems()[cId], amount);
    }

    public OilItemOilCraftingResult(OilItemBase item, int amount) {
        this(item, amount, 0);
    }
}
