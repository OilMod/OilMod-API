package org.oilmod.api.userinterface;


import org.oilmod.api.rep.itemstack.ItemStackRep;

/**
 * Created by sirati97 on 26.06.2016 for OilMod-Api.
 */
public interface ItemStackHolder {
    ItemStackRep getItemStack();
    void setItemStack(ItemStackRep itemStack);
}
