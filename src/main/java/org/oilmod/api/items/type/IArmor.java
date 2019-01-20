package org.oilmod.api.items.type;

import org.oilmod.api.items.EnchantmentType;
import org.oilmod.api.items.OilItemStack;

public interface IArmor extends IUnique {


    @Override
    int getItemEnchantability();

    @Override
    default EnchantmentType getEnchantmentType() {
        return EnchantmentType.ARMOR;
    }

}
