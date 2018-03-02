package org.oilmod.api.items.crafting;

import org.oilmod.api.util.OilUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sirati97 on 02.07.2016 for OilMod-TestPlugin.
 */
public class VanillaEnchantmentIngredient extends VanillaMaterialIngredient {
    private final Enchantment enchantment;
    private final int level;

    public VanillaEnchantmentIngredient(Enchantment enchantment, int level) {
        super(Material.ENCHANTED_BOOK);
        this.enchantment = enchantment;
        this.level = level;
    }

    @Override
    public boolean match(ItemStack itemStack, DataHolder dataHolder) {
        if (!super.match(itemStack, dataHolder)) {
            return false;
        }
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta)itemStack.getItemMeta();
        return meta.getStoredEnchantLevel(enchantment)==level;
    }

    @Override
    public ItemStack getRandomExample(Random rnd, DataHolder dataHolder) {
        ItemStack result = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta)result.getItemMeta();
        meta.addStoredEnchant(enchantment, level, true);
        if (rnd.nextInt(4)!=0) {
            List<Enchantment> allowedEnchantments = new ArrayList<>(Enchantment.values().length);
            for (Enchantment enchantmentCurrent:Enchantment.values()) {
                if (!enchantmentCurrent.equals(this.enchantment) && !OilUtil.isSimilarEnchantmentTarget(enchantment.getItemTarget(),enchantmentCurrent.getItemTarget())) {
                    allowedEnchantments.add(enchantmentCurrent);
                }
            }
            if (allowedEnchantments.size()>0) {
                Enchantment enchantment2 = allowedEnchantments.get(rnd.nextInt(allowedEnchantments.size()));
                meta.addStoredEnchant(enchantment2, rnd.nextInt(enchantment2.getMaxLevel()-enchantment2.getStartLevel()+1)+enchantment2.getStartLevel(), false);
            }
        }
        result.setItemMeta(meta);
        return result;
    }
}
