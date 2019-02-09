package org.oilmod.api.items.type;

import org.oilmod.api.items.EnchantmentType;

public interface IChestplate extends IArmor {

    @Override
    int getItemEnchantability();

    @Override
    default EnchantmentType getEnchantmentType() {
        return EnchantmentType.ARMOR_CHEST;
    }
}
