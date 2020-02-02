package org.oilmod.api;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.UI.UIRegistry;
import org.oilmod.api.blocks.BlockRegistry;
import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.blocks.type.BlockImplementationProvider;
import org.oilmod.api.inventory.ItemFilterRegistry;
import org.oilmod.api.items.ItemRegistry;
import org.oilmod.api.items.type.ItemImplementationProvider;
import org.oilmod.api.items.type.TBBType;
import org.oilmod.api.registry.RegistryBase;
import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.stateable.complex.ComplexStateTypeRegistry;
import org.oilmod.api.util.OilKey;
import org.oilmod.api.util.OilUtil;
import org.oilmod.spi.dependencies.DependencyPipe;
import org.oilmod.spi.mpi.SingleMPI;
import org.oilmod.spi.provider.ImplementationBase;

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
    private final static Map<Class<? extends RegistryBase<?,?,?,?>>, BiConsumer<OilMod, ? extends RegistryBase<?,?,?,?>>> eventCallers = new Object2ObjectOpenHashMap<>();

    private static <T extends RegistryBase<?, T, ?, ?>> void addEventCaller(Class<T> clazz, BiConsumer<OilMod, T> caller){
        eventCallers.put(clazz, caller);
    }

    static {
        addEventCaller(ItemRegistry.class, OilMod::onRegisterItems);
        addEventCaller(ItemFilterRegistry.class, OilMod::onRegisterItemFilter);
        addEventCaller(ItemImplementationProvider.Registry.class, OilMod::onRegisterImplementationProvider);
        addEventCaller(UIRegistry.class, OilMod::onRegisterUI);
        addEventCaller(BlockRegistry.class, OilMod::onRegisterBlocks);
        addEventCaller(BlockType.Registry.class, OilMod::onRegisterBlockTypes);
        addEventCaller(BlockImplementationProvider.Registry.class, OilMod::onRegisterImplementationProvider);
        addEventCaller(TBBType.Registry.class, OilMod::onRegisterTBBType);
        addEventCaller(ComplexStateTypeRegistry.class, OilMod::onRegisterComplexStateType);
    }


    private String internalName;
    private String displayName;
    private Map<Class<? extends RegistryBase<?,?,?,?>>, RegistryBase<?,?,?,?>> registries = new Object2ObjectOpenHashMap<>();
    private Set<RegistryBase<?,?,?,?>> uninvokedRegistries = new ObjectOpenHashSet<>();
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
        RegistryBase.setRegistries(this, registries);
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


    //todo use an event-bus for this. especially if we have the markup-language modding interface invisible, seamless event registering is useful
    protected void onRegisterItems(ItemRegistry registry){}
    protected void onRegisterItemFilter(ItemFilterRegistry registry){}
    protected void onRegisterUI(UIRegistry registry) {}

    protected void onRegisterBlocks(BlockRegistry registry){}
    protected void onRegisterBlockTypes(BlockType.Registry registry){}
    protected void onRegisterImplementationProvider(ItemImplementationProvider.Registry registry){}
    protected void onRegisterImplementationProvider(BlockImplementationProvider.Registry registry){}
    protected void onRegisterTBBType(TBBType.Registry registry){}
    protected void onRegisterComplexStateType(ComplexStateTypeRegistry registry){}
    //protected void onRegisterCraftingRecipes(){}

    public static Collection<OilMod> getAll() {
        return registeredSetRead;
    }

    public static OilMod getByName(String name) {
        return registeredMap.get(name);
    }

    protected boolean isImplementation() {
        return false;
    }
    protected boolean isGame() {
        return false;
    }


    public static class MPI extends SingleMPI<MPI, ModHelper<?>>{
        @Override
        public boolean hasDefaultProvider() {
            return true;
        }

        @Override
        public ModHelper<?> createDefaultProvider() {
            return new DefaultModHelper();
        }

        private static class DefaultModHelper extends ModHelper<DefaultModHelper>{} //needed for generics resolution
    }

    public static class ModHelper<Impl extends ModHelper<?>> extends ImplementationBase<MPI, ModHelper<?>, Impl> {
        private static ModHelper<?> instance;
        static boolean usingDelegatedCtor = false;
        private static OilMod game;
        private static OilMod implementation;


        public static ModHelper<?> getInstance() {
            return instance;
        }

        @Override
        public void addDependencies(DependencyPipe p) {
            p.add(OilUtil.UtilImpl.class, true, true);
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
                if (mod.isImplementation()) {
                    if (implementation != null)throw new IllegalStateException("Only implementation is allowed to have isImplementation()->true");
                    implementation = mod;
                }
                if (mod.isGame()) {
                    if (game != null)throw new IllegalStateException("Only game is allowed to have isGame()->true");
                    game = mod;
                }

                mod.afterCtor(internalName, displayName, context);
                return mod;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException("Cound not construct mod", e);
            } finally {
                usingDelegatedCtor = false;
            }
        }

        @Override
        public void onReady() {
            instance = this;
        }

        public static ModContext getDefaultContext() {
            return getInstance().createDefaultContext();
        }

        public static  <T extends RegistryBase<?, T, ?, ?>> BiConsumer<OilMod, T> getEventCaller(Class<T> clazz) {
            Validate.notNull(clazz, "received null instead of class");
            //noinspection unchecked
            BiConsumer<OilMod, T> result = (BiConsumer<OilMod, T>) eventCallers.get(clazz);
            Validate.notNull(result, "missing eventcaller for %s", clazz.getName());
            return result;
        }

        protected void register(OilMod mod) {
            Validate.isTrue(!registeredMap.containsKey(mod.getInternalName()), "There is already a mod registered with the name " + mod.getInternalName());
            registeredMap.put(mod.getInternalName(), mod);
        }
        protected void unregister(OilMod mod) {
            registeredMap.remove(mod.getInternalName());
        }

        public static OilMod getGame() {
            return game;
        }

        public static OilMod getImplementation() {
            return implementation;
        }

        protected ItemRegistry createItemRegistry(OilMod mod) {
            return ItemRegistry.Helper.getInstance().create(mod);
        }

        protected ModContext createDefaultContext() {
            return new ModContext() {};
        }

        public static <T extends RegistryBase<?,T,?,?>> T getRegistry(OilMod mod, Class<T> clazz) {
            return cast(mod.registries.get(clazz));
        }

        protected static void initialise(OilMod mod) {
            mod.init();
        }
        protected static <T extends RegistryBase<?, T, ?, ?>> void invokeRegister(OilMod mod, Class<T> clazz) {
            T registry = getRegistry(mod, clazz);
            registry.getRegistryHelper().getEventCaller().accept(mod, registry);
            mod.uninvokedRegistries.remove(registry);
        }


        protected static void invokeMissingRegister(OilMod mod) {
            //noinspection unchecked,RedundantCast,rawtypes
            mod.uninvokedRegistries.forEach(registry -> ((RegistryHelperBase)registry.getRegistryHelper()).getEventCaller().accept(mod, registry));
            mod.uninvokedRegistries.clear();
        }
    }

    public interface ModContext {

    }
}
