package org.oilmod.api;

import gnu.trove.map.hash.THashMap;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.items.ItemRegistry;
import org.oilmod.api.unification.material.IUniMaterial;
import org.oilmod.api.unification.material.MaterialHelper;
import org.oilmod.api.unification.material.UniMaterial;
import org.oilmod.api.util.OilKey;
import org.oilmod.api.util.Util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.oilmod.api.util.Util.checkName;

public class OilMod {
    private final static Map<String, OilMod> registeredMap = new THashMap<>();
    private final static Collection<OilMod> registeredSetRead = Collections.unmodifiableCollection(registeredMap.values());

    private final String internalName;
    private final String displayName;
    private ItemRegistry itemRegistry;
    private boolean initialised = false;
    private boolean initialising = false;


    /**
     * Creates new instance of OilMod
     * @param internalName Short unique name. Restricted to lowercase alphanumerical characters and underscores. Do not change it later, otherwise your mod becomes incompatible with older versions of you mod. This will be used to identify content of this mod and will be visible to admins and players with cheat perms
     */
    public OilMod(String internalName) {
        this(internalName, internalName);
    }

    /**
     * Creates new instance of OilMod
     * @param internalName Short unique name. Restricted to lowercase alphanumerical characters and underscores. Do not change it later, otherwise your mod becomes incompatible with older versions of you mod. This will be used to identify content of this mod and will be visible to admins and players with cheat perms
     * @param displayName This is used rarely and only where things are presented pretty.
     */
    public OilMod(String internalName, String displayName) {
        checkName(internalName);
        this.internalName = internalName;
        this.displayName = displayName;
        ModHelper.getInstance().register(this);

    }

    private void init() {
        if (initialised) throw new IllegalStateException("Cannot initalise mod twice");
        initialising = true;
        //now register dependencies
        itemRegistry= ModHelper.getInstance().createItemRegistry(this);


        //###
        initialising = false;
        initialised = true;
    }

    public boolean isInitialised() {
        return initialised;
    }

    public boolean isInitialising() {
        return initialising;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getInternalName() {
        return internalName;
    }

    @Override
    public String toString() {
        return internalName;
    }

    /**
     *
     * @param keyString Short unique string. Restricted to lowercase alphanumerical characters and underscores. Do not change it later, otherwise your mod becomes incompatible with older versions of you mod. This will be used to identify content of this mod and will be visible to admins and players with cheat perms
     * @return Returns an unique key.
     */
    public OilKey createKey(String keyString) {
        return OilKey.create(this, keyString);
    }

    public void onRegisterItems(ItemRegistry itemRegistry){}
    public void onRegisterBlocks(){}
    public void onRegisterCraftingRecipes(){}

    public static Collection<OilMod> getAll() {
        return registeredSetRead;
    }

    public static OilMod getByName(String name) {
        return registeredMap.get(name);
    }

    public static class ModHelper {
        private static ModHelper instance;
        private static final Object MUTEX = new Object();
        private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

        public static void setInstance(ModHelper instance) {
            if (ModHelper.instance == null) {
                synchronized (MUTEX) {
                    if (ModHelper.instance == null) {
                        ModHelper.instance = instance;
                    } else {
                        throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                    }
                }
            } else {
                throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
            }
        }

        public static ModHelper getInstance() {
            return instance;
        }

        protected void register(OilMod mod) {
            Validate.isTrue(!registeredMap.containsKey(mod.getInternalName()), "There is already a mod registered with the name " + mod.getInternalName());
            registeredMap.put(mod.getInternalName(), mod);
        }
        protected void unregister(OilMod mod) {
            registeredMap.remove(mod.getInternalName());
        }



        protected ItemRegistry createItemRegistry(OilMod mod) {
            return new ItemRegistry(mod);
        }


        protected static ItemRegistry getItemRegistry(OilMod mod) {
            return mod.itemRegistry;
        }
        protected static void initialise(OilMod mod) {
            mod.init();
        }
        protected static void invokeRegisterItems(OilMod mod) {
            mod.onRegisterItems(getItemRegistry(mod));
        }
    }
}
