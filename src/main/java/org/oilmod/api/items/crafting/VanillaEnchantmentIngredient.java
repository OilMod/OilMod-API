package org.oilmod.api.items.crafting;

import org.oilmod.api.rep.enchant.EnchantmentRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.util.OilUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sirati97 on 02.07.2016 for OilMod-TestPlugin.
 */
/*public class VanillaEnchantmentIngredient extends VanillaMaterialIngredient {
    private final EnchantmentRep enchantment;
    private final int level;

    public VanillaEnchantmentIngredient(EnchantmentRep enchantment, int level) {
        super(Material.ENCHANTED_BOOK);
        this.enchantment = enchantment;
        this.level = level;
    }

    @Override
    public boolean match(ItemStackRep itemStack, DataHolder dataHolder) {
        if (!super.match(itemStack, dataHolder)) {
            return false;
        }
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta)itemStack.getItemMeta();
        return meta.getStoredEnchantLevel(enchantment)==level;
    }

    @Override
    public ItemStackRep getRandomExample(Random rnd, DataHolder dataHolder) {
        ItemStackRep result = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta)result.getItemMeta();
        meta.addStoredEnchant(enchantment, level, true);
        if (rnd.nextInt(4)!=0) {
            List<Enchantment> allowedEnchantments = new ArrayList<>(Enchantment.values().length);
            for (EnchantmentRep enchantmentCurrent:Enchantment.values()) {
                if (!enchantmentCurrent.equals(this.enchantment) && !OilUtil.isSimilarEnchantmentTarget(enchantment.getItemTarget(),enchantmentCurrent.getItemTarget())) {
                    allowedEnchantments.add(enchantmentCurrent);
                }
            }
            if (allowedEnchantments.size()>0) {
                EnchantmentRep enchantment2 = allowedEnchantments.get(rnd.nextInt(allowedEnchantments.size()));
                meta.addStoredEnchant(enchantment2, rnd.nextInt(enchantment2.getMaxLevel()-enchantment2.getStartLevel()+1)+enchantment2.getStartLevel(), false);
            }
        }
        result.setItemMeta(meta);
        return result;
    }
}*/ //TODO readd
