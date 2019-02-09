package org.oilmod.api.userinterface;

import org.oilmod.api.rep.entity.EntityHumanRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.userinterface.internal.NMSClickData;
import org.oilmod.api.userinterface.internal.NMSUIElement;

/**
 * Created by sirati97 on 16.06.2016 for OilMod-Api.
 */
public interface IUIElement {
    /**
     *
     * @param index the <b>local</b> index of the element
     * @return ItemStackRep at given slot. Element does not have to behave different with different index, but it is possible
     */
    ItemStackRep getDisplayed(int index);

    /**
     * Sets ItemStackRep at given slot. Element does not have to behave different with different index, but it is possible
     * @param index the <b>local</b> index of the element
     */
    void setDisplayed(int index, ItemStackRep itemStack);

    /**
     * Called when Element got clicked. Element does not have to behave different with different index, but it is possible
     * @param player The player using the ui
     * @param index The <b>local</b> index of the element
     * @param data Only important for NMS Callbacks
     */
    void onClick(EntityHumanRep player, int index, Click click, NMSClickData data);

    /**
     * Sets NMS Version of this Element
     */
    void setNmsWrapper(NMSUIElement nmsWrapper);

    /**
     * @return NMS Version of this Element
     */
    NMSUIElement getNmsWrapper();
}
