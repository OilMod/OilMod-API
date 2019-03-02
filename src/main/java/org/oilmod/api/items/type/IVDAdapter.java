package org.oilmod.api.items.type;

import org.oilmod.api.items.OilItemStack;

public interface IVDAdapter extends IItemGeneric  {//todo to minecraft specific, use different visualiser approach
    int getVanillaData(OilItemStack itemStack);
}
