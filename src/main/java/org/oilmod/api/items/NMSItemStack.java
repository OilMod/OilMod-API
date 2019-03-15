package org.oilmod.api.items;

import org.oilmod.api.rep.itemstack.ItemStackRep;

/**
 * Internal
 */
public interface NMSItemStack {
    ItemStackRep asItemStackRep();
    OilItemStack getOilItemStack();
    int getDataNMS();
    void setDataNMS(int data);
    String getRenameNMS();
    void setRenameNMS(String name);

    boolean isRenamedNMS();
}
