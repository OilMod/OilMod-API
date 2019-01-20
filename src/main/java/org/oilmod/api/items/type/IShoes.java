package org.oilmod.api.items.type;

import org.oilmod.api.items.EnchantmentType;

public interface IShoes extends IArmor {


    @Override
    int getItemEnchantability();

    @Override
    default EnchantmentType getEnchantmentType() {
        return EnchantmentType.ARMOR_BOOTS;
    }
}
