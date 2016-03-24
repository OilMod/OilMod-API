package de.sirati97.oilmod.api.items.crafting;

import de.sirati97.oilmod.api.items.OilSpecificItemstackFactory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class SpecificItemstackFactoryOilCraftingResult implements OilCraftingResult {
    private final OilSpecificItemstackFactory itemstackFactory;
    private final int amount;

    public SpecificItemstackFactoryOilCraftingResult(OilSpecificItemstackFactory itemstackFactory, int amount) {
        this.itemstackFactory = itemstackFactory;
        this.amount = amount;
    }

    @Override
    public ItemStack getResult(ItemStack[] matrix, boolean shaped, int width, int height) {
        ItemStack result = itemstackFactory.create();
        result.setAmount(amount);
        return result;
    }
}
