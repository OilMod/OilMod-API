package de.sirati97.oilmod.api.items.crafting;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class VanillaMaterialOilCraftingComparator extends VanillaOilCraftingComparator {
    private final Material material;

    public VanillaMaterialOilCraftingComparator(Material material) {
        this.material = material;
    }

    @Override
    public boolean match(ItemStack itemStack) {
        return super.match(itemStack) && itemStack.getType().equals(material);
    }
}
