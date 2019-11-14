package org.oilmod.api.items.nms;

import org.oilmod.api.items.NMSItem;
import org.oilmod.api.rep.enchant.EnchantmentRep;
import org.oilmod.api.rep.item.ItemStateRep;

public interface NMSEnchantmentType {
    boolean canEnchantNMS(ItemStateRep item);
    boolean containsEnchantmentNMS(EnchantmentRep ench);
}
