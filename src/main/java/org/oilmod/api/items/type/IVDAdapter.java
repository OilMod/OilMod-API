package org.oilmod.api.items.type;

import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;

public interface IVDAdapter extends IItemGeneric  {
    int getVanillaData(OilItemStack itemStack);
}
