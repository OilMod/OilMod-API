package de.sirati97.oilmod.api.items.crafting;

import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class ModItemOilCraftingComparator implements OilCraftingComparator {
    private Class<? extends OilItemBase> itemClass;

    public ModItemOilCraftingComparator(Class<? extends OilItemBase> itemClass) {
        this.itemClass = itemClass;
    }

    @Override
    public boolean match(ItemStack itemStack) {
        return itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack().getItem().getClass().equals(itemClass);
    }
}
