package org.oilmod.api.items.type;

import org.oilmod.api.items.EnchantmentType;

public interface ILeggings extends IArmor {

    @Override
    default EnchantmentType getEnchantmentType() {
        return EnchantmentType.ARMOR_LEGGINGS;
    }
}
