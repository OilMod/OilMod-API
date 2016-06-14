package de.sirati97.oilmod.api.items.crafting;

import de.sirati97.oilmod.api.util.OilUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class VanillaMaterialOilCraftingIngredient extends VanillaOilCraftingIngredient {
    private final Material material;

    public VanillaMaterialOilCraftingIngredient(Material material) {
        this.material = material;
    }

    @Override
    public boolean match(ItemStack itemStack, ComparatorData comparatorData) {
        return super.match(itemStack, comparatorData) && itemStack.getType().equals(material);
    }

    @Override
    public ItemStack getRandomExample(Random rnd) {
        return OilUtil.getRandomValidVariation(material, rnd);
    }
}
