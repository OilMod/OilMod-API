package org.oilmod.api.items.type;

import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.rep.entity.EntityLivingRep;

public abstract class ItemTypeHelper {

    private static ItemTypeHelper instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

    public static void setInstance(ItemTypeHelper instance) {
        if (ItemTypeHelper.instance == null) {
            synchronized (MUTEX) {
                if (ItemTypeHelper.instance == null) {
                    ItemTypeHelper.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    public static ItemTypeHelper getInstance() {
        return instance;
    }


    public abstract boolean handleDamage(OilItemStack stack, int damage, EntityLivingRep entity);
    public abstract void damageItem(OilItemStack stack, int damage, EntityLivingRep entity);
    public abstract int getItemDamage(OilItemStack stack);



    //
    private static final String UNSUPPORTED = "This ItemTypeHelper does not support this operation";
    public int getMaxStackSize() {return 64;}

}
