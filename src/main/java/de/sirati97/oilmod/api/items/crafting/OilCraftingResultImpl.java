package de.sirati97.oilmod.api.items.crafting;

import de.sirati97.oilmod.api.items.OilSpecificItemstackFactory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class OilCraftingResultImpl implements OilCraftingResult {
    private final OilSpecificItemstackFactory itemstackFactory;

    public OilCraftingResultImpl(OilSpecificItemstackFactory itemstackFactory) {
        this.itemstackFactory = itemstackFactory;
    }

    @Override
    public ItemStack getResult(ItemStack[] matrix, boolean shaped, int width, int height) {
        return itemstackFactory.create();
    }
}
