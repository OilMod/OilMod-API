package de.sirati97.oilmod.api.items.crafting;

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
public class VanillaEnchantedBookIngredient extends VanillaMaterialOilCraftingIngredient {
    private final Enchantment enchantment;
    private final int level;

    public VanillaEnchantedBookIngredient(Enchantment enchantment, int level) {
        super(Material.ENCHANTED_BOOK);
        this.enchantment = enchantment;
        this.level = level;
    }

    @Override
    public boolean match(ItemStack itemStack, ComparatorData comparatorData) {
        if (!super.match(itemStack, comparatorData)) {
            return false;
        }
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta)itemStack.getItemMeta();
        return meta.getStoredEnchantLevel(enchantment)==level;
    }

    @Override
    public ItemStack getRandomExample(Random rnd) {
        ItemStack result = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta)result.getItemMeta();
        meta.addStoredEnchant(enchantment, level, true);
        if (rnd.nextInt(4)!=0) {
            List<Enchantment> allowedEnchantments = new ArrayList<>(Enchantment.values().length);
            for (Enchantment enchantmentCurrent:Enchantment.values()) {
                if (enchantmentCurrent != this.enchantment && !meta.hasConflictingStoredEnchant(enchantmentCurrent)) {
                    allowedEnchantments.add(enchantmentCurrent);
                }
            }
            if (allowedEnchantments.size()>0) {
                Enchantment enchantment2 = allowedEnchantments.get(rnd.nextInt(allowedEnchantments.size()));
                meta.addStoredEnchant(enchantment, rnd.nextInt(enchantment2.getMaxLevel()-enchantment2.getStartLevel()+1)+enchantment2.getStartLevel(), false);
            }
        }
        result.setItemMeta(meta);
        return result;
    }
}
