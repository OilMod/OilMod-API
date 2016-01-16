package de.sirati97.oilmod.api.items.internal;

import de.sirati97.oilmod.api.items.ItemRegister;
import de.sirati97.oilmod.api.items.OilItemBase;

/**
 * Created by sirati97 on 15.01.2016.
 */
public abstract class ItemRegisterHelperBase {
    private static ItemRegisterHelperBase instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";
    private static final ModItemSetterHelper HELPER = new ModItemSetterHelper();

    public static void setInstance(ItemRegisterHelperBase instance) {
        if (ItemRegisterHelperBase.instance == null) {
            synchronized (MUTEX) {
                if (ItemRegisterHelperBase.instance == null) {
                    ItemRegisterHelperBase.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    private static class ModItemSetterHelper extends de.sirati97.oilmod.api.items.ModItemSetterHelper {
        @Override
        public void set(Object nmsItem, OilItemBase apiItem) {
            super.set(nmsItem, apiItem);
        }
    }

    public interface InitRegisterCallback {
        void callback(boolean success, Object nmsObject);
    }

    public static ItemRegisterHelperBase getInstance() {
        return instance;
    }

    protected void setNMSModItem(Object nmsItem, OilItemBase apiItem) {
        HELPER.set(nmsItem, apiItem);
    }

    public abstract void register(ItemRegister register, OilItemBase apiItem);


    public abstract void initRegister(ItemRegister register, InitRegisterCallback callback);

}
