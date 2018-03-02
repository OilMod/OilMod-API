package org.oilmod.api.items.crafting;

import org.oilmod.api.util.OilUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class VanillaMaterialIngredient extends VanillaIngredientBase {
    private final Material material;

    public VanillaMaterialIngredient(Material material) {
        this.material = material;
    }

    @Override
    public boolean match(ItemStack itemStack, DataHolder dataHolder) {
        return super.match(itemStack, dataHolder) && itemStack.getType().equals(material);
    }

    @Override
    public ItemStack getRandomExample(Random rnd, DataHolder dataHolder) {
        return OilUtil.getRandomValidVariation(material, rnd);
    }
}
