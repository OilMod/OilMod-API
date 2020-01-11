package org.oilmod.api;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.UI.UIRegistry;
import org.oilmod.api.inventory.ItemFilterRegistry;
import org.oilmod.api.items.ItemRegistry;
import org.oilmod.api.registry.Registry;
import org.oilmod.api.unification.material.IUniMaterial;
import org.oilmod.api.unification.material.MaterialHelper;
import org.oilmod.api.unification.material.UniMaterial;
import org.oilmod.api.util.OilKey;
import org.oilmod.api.util.Util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import static org.oilmod.api.util.Util.checkName;
import static org.oilmod.util.LamdbaCastUtils.cast;

public class OilMod {
    private final static Map<String, OilMod> registeredMap = new Object2ObjectOpenHashMap<>();
    private final static Collection<OilMod> registeredSetRead = Collections.unmodifiableCollection(registeredMap.values());
    private final static Map<Class<? extends Registry>, BiConsumer<OilMod, ? extends Registry>> eventCallers = new Object2ObjectOpenHashMap<>();

    private static <T extends Registry<?, T, ?, ?>> void addEventCaller(Class<T> clazz, BiConsumer<OilMod, T> caller){
        eventCallers.put(clazz, caller);
    }

    static {
        addEventCaller(ItemRegistry.class, OilMod::onRegisterItems);
        addEventCaller(ItemFilterRegistry.class, OilMod::onRegisterItemFilter);
        addEventCaller(UIRegistry.class, OilMod::onRegisterUI);
    }


    private String internalName;
    private String displayName;
    private Map<Class<? extends Registry>, Registry> registries = new Object2ObjectOpenHashMap<>();
    private Set<Registry> uninvokedRegistries = new ObjectOpenHashSet<>();
    private boolean initialised = false;
    private boolean initialising = false;
    private boolean constructed = false;
    private ModContext context;


    protected OilMod() {
        Validate.isTrue(ModHelper.usingDelegatedCtor, "Cannot directly construct OilMod use OilMod.ModHelper.createInstance instead");
    }

    void afterCtor(String internalName, String displayName, ModContext context) {
        if (constructed) throw new IllegalStateException("Cannot call afterCtor twice");
        checkName(internalName);
        this.internalName = internalName;
        this.displayName = displayName;
        this.context = context;
        ModHelper.getInstance().register(this);
        Registry.setRegistries(this, registries);
        uninvokedRegistries.addAll(registries.values());
        constructed = true;
        onConstructed();
    }

    protected void onConstructed() {}

    public boolean isConstructed() {
        return constructed;
    }

    private void init() {
        if (initialised) throw new IllegalStateException("Cannot initalise mod twice");
        initialising = true;

        //now register dependencies

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

    public ModContext getContext() {
        return context;
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


    //todo use an event-bus for this. especially if we have the markup-language modding interface invisible, seamless even registering is useful
    protected void onRegisterItems(ItemRegistry registry){}
    protected void onRegisterItemFilter(ItemFilterRegistry registry){}
    protected void onRegisterUI(UIRegistry registry) {}

    protected void onRegisterBlocks(){}
    protected void onRegisterCraftingRecipes(){}

    public static Collection<OilMod> getAll() {
        return registeredSetRead;
    }

    public static OilMod getByName(String name) {
        return registeredMap.get(name);
    }




    public static class ModHelper {
        private static ModHelper instance;
        static boolean usingDelegatedCtor = false;
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

        /**
         * Creates new instance of OilMod
         * @param clazz Class of Mod
         * @param internalName Short unique name. Restricted to lowercase alphanumerical characters and underscores. Do not change it later, otherwise your mod becomes incompatible with older versions of you mod. This will be used to identify content of this mod and will be visible to admins and players with cheat perms
         * @param displayName This is used rarely and only where things are presented pretty.
         */
        public static <T extends OilMod> T createInstance(Class<T> clazz, ModContext context, String internalName, String displayName) {
            try {
                usingDelegatedCtor = true;
                T mod = clazz.newInstance();
                mod.afterCtor(internalName, displayName, context);
                return mod;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException("Cound not construct mod", e);
            } finally {
                usingDelegatedCtor = false;
            }
        }

        public static ModContext getDefaultContext() {
            return getInstance().createDefaultContext();
        }

        public static  <T extends Registry<?, T, ?, ?>>  BiConsumer<OilMod, T> getEventCaller(Class<T> clazz) {
            Validate.notNull(clazz, "received null instead of class");
            //noinspection unchecked
            BiConsumer<OilMod, T> result = (BiConsumer<OilMod, T>) eventCallers.get(clazz);
            Validate.notNull(result, "missing eventcaller for %s", clazz.getSimpleName());
            return result;
        }

        protected void register(OilMod mod) {
            Validate.isTrue(!registeredMap.containsKey(mod.getInternalName()), "There is already a mod registered with the name " + mod.getInternalName());
            registeredMap.put(mod.getInternalName(), mod);
        }
        protected void unregister(OilMod mod) {
            registeredMap.remove(mod.getInternalName());
        }



        protected ItemRegistry createItemRegistry(OilMod mod) {
            return ItemRegistry.RegistryHelper.getInstance().create(mod);
        }

        protected ModContext createDefaultContext() {
            return new ModContext() {};
        }

        protected static <T extends Registry> T getRegistry(OilMod mod, Class<T> clazz) {
            return cast(mod.registries.get(clazz));
        }

        protected static void initialise(OilMod mod) {
            mod.init();
        }
        protected static <T extends Registry<?, T, ?, ?>> void invokeRegister(OilMod mod, Class<T> clazz) {
            T registry = getRegistry(mod, clazz);
            registry.getRegistryHelper().getEventCaller().accept(mod, registry);
            mod.uninvokedRegistries.remove(registry);
        }


        protected static void invokeMissingRegister(OilMod mod) {
            //noinspection unchecked
            mod.uninvokedRegistries.forEach(registry -> registry.getRegistryHelper().getEventCaller().accept(mod, registry));
            mod.uninvokedRegistries.clear();
        }
    }

    public interface ModContext {

    }
}
