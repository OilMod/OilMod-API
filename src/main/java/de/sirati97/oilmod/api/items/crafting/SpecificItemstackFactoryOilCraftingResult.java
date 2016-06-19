package de.sirati97.oilmod.api.items.crafting;

import de.sirati97.oilmod.api.items.OilSpecificItemstackFactory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class SpecificItemstackFactoryOilCraftingResult implements OilCraftingResult {
    private final OilSpecificItemstackFactory itemstackFactory;
    private final int amount;
    private ItemStack result;

    public SpecificItemstackFactoryOilCraftingResult(OilSpecificItemstackFactory itemstackFactory, int amount) {
        this.itemstackFactory = itemstackFactory;
        this.amount = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStack preCraftResult(ItemStack[] matrix, boolean shaped, int width, int height) {
        return getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void craftResult(ItemStack result, ItemStack[] matrix, boolean shaped, int width, int height) {
        this.result = null;
    }

    public ItemStack getResult() {
        if (result == null) {
            result = itemstackFactory.create();
            result.setAmount(amount);
        }
        return result;
    }
}
