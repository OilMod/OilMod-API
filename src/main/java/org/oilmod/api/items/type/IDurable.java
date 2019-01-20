package org.oilmod.api.items.type;

import org.bukkit.entity.LivingEntity;
import org.oilmod.api.items.EnchantmentType;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;

public interface IDurable extends IVDAdapter {
    int getMaxDurability();

    /**
     * Called to set the new durability
     * @param stack ItemStack used
     * @param damage damage added to stack after consideration of enchantments and bukkit
     * @param entity Entity using the item
     * @return Return true to destroy the item
     */
    default boolean handleDamage(OilItemStack stack, int damage, LivingEntity entity) {
        return ItemTypeHelper.getInstance().handleDamage(stack, damage, entity);
    }


    default void damageItem(OilItemStack stack, int damage, LivingEntity entity) {
        ItemTypeHelper.getInstance().damageItem(stack, damage, entity);
    }

    default int getVanillaData(OilItemStack stack) {
        int v = ((OilItem)stack.getItem()).getVanillaMaterial(stack).getMaxDurability();
        return stack.getData()*v/getMaxDurability();
    }
}
