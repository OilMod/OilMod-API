package de.sirati97.oilmod.api.items.crafting;

import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 24.03.2016.
 */
public abstract class VanillaOilCraftingComparator implements OilCraftingComparator {

    @Override
    public boolean match(ItemStack itemStack2) {
        return !(itemStack2 instanceof OilBukkitItemStack);
    }
}
