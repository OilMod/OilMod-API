package org.oilmod.api.items.type;

import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.rep.entity.EntityLivingRep;

public interface IDurable extends IVDAdapter {
    int getMaxDurability();

    /**
     * Called to set the new durability
     * @param stack ItemStackRep used
     * @param damage damage added to stack after consideration of enchantments and bukkit
     * @param entity Entity using the item
     * @return Return true to destroy the item
     */
    default boolean handleItemDamage(OilItemStack stack, int damage, EntityLivingRep entity) {
        return ItemTypeHelper.getInstance().handleDamage(stack, damage, entity);
    }


    default void damageItem(OilItemStack stack, int damage, EntityLivingRep entity) {
        ItemTypeHelper.getInstance().damageItem(stack, damage, entity);
    }

    default int getItemDamage(OilItemStack stack) {
        return ItemTypeHelper.getInstance().getItemDamage(stack);
    }

    default boolean usesVanillaDamageHandling() {return true;}

    default double getDurabilityForDisplay(OilItemStack stack) {
        return stack.getData()/(double)getMaxDurability();
    }

    default int getVanillaData(OilItemStack stack) {
        int v = ((OilItem)stack.getItem()).getVanillaIcon(stack).getProvidedItem().getMaxDurability();
        return (int)(v*getDurabilityForDisplay(stack));
    }
}
