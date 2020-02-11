package org.oilmod.api.registry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.OilMod;
import org.oilmod.api.util.OilKey;
import org.oilmod.spi.MPILoader;
import org.oilmod.spi.mpi.IModdingPIService;
import org.oilmod.spi.provider.IMPIImplementationProvider;
import org.oilmod.spi.provider.ImplementationBase;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import static org.oilmod.util.ReflectionUtils.resolveGenericSuperClass;
import static org.oilmod.util.ReflectionUtils.resolveGenericSuperInterface;
import static org.oilmod.util.Strings.simpleName;

/**
 * Internal - should not be called by user code
 */
public abstract class RegistryHelperBase<
        Type,
        TReg extends RegistryBase<Type, TReg, MPI, IB>,
        MPI extends RegistryMPIBase<Type, TReg, MPI, IB>,
        IB extends RegistryHelperBase<Type, TReg, MPI, IB, ? extends IB>,
        Impl extends IB>
        extends ImplementationBase<MPI, IB, Impl> {

    protected static final Map<Class<? extends RegistryBase<?,?,?,?>>, RegistryHelperBase<?,?,?,?,?>> registries = new Object2ObjectOpenHashMap<>();
    private BiConsumer<OilMod, TReg> eventCaller;
    private final Class<TReg> regClass;


    @SuppressWarnings("unchecked")
    public RegistryHelperBase() {
        Class<?>[] generics = resolveGenericSuperClass(getClass(), RegistryHelperBase.class, 0, 3);
        this.regClass = (Class<TReg>) generics[1];

        Class<?>[] reg = resolveGenericSuperClass(regClass, RegistryBase.class, 0, 3);
        Validate.isTrue(generics[1] == reg[1], "Malformed generics, mpi does not match: %s vs %s", generics[1], reg[1]);

        registries.put(regClass, this);
    }

    public Class<TReg> getRegistryClass() {
        return regClass;
    }



    @Override
    public void onReady() {
        eventCaller = OilMod.ModHelper.getEventCaller(getMpi().getRegistryClass());
    }

    private final Map<OilKey, Type> mapEntry = new Object2ObjectOpenHashMap<>();
    private final Map<OilKey, TReg> mapRegistry = new Object2ObjectOpenHashMap<>();
    private final Set<Type> registered = new ObjectOpenHashSet<>();
    private final Set<Type> registeredReadOnly = Collections.unmodifiableSet(registered);


    public Set<Type> getRegistered() {
        return registeredReadOnly;
    }

    public Type get(OilKey key) {
        return mapEntry.get(key);
    }


    public boolean allowUnregistering() {
        return false;
    }

    public final void unregister(OilKey key) {
        if (!allowUnregistering()) throw new IllegalStateException("This registry does not support unregistering!");
        Type entry = mapEntry.remove(key);
        if (entry == null)throw new IllegalStateException("There is no entry with key: " + key.toString());
        TReg registry = mapRegistry.remove(key);
        registered.remove(entry);
        onUnregister(key, registry, entry);
    }

    protected void onUnregister(OilKey key, TReg registry, Type entry) {

    }


    /**Only for internal use! Can registry.register instead!
     *
     * This should be overridden to detect conflicts early*/
    protected final  <T extends Type> void _preregister(OilKey key, TReg registry, T entry) {
        Validate.isTrue(!registered.contains(entry), "Cannot register object twice");
        Validate.isTrue(!mapEntry.containsKey(key), "Cannot register same key twice. Key: " + key.toString());
    }
    /**Only for internal use! Can registry.register instead!*/
    protected final  <T extends Type> void _register(OilKey key, TReg registry, T entry) {
        _preregister(key, registry, entry);
        mapEntry.put(key, entry);
        mapRegistry.put(key, registry);
        registered.add(entry);
        onRegister(key, registry, entry);
    }

    protected <T extends Type> void onRegister(OilKey key, TReg registry, T entry) {

    }


    protected void initRegister(TReg register, InitRegisterCallback callback) {
        callback.callback(true, null);
    }

    protected abstract TReg create(OilMod mod);

    public BiConsumer<OilMod, TReg> getEventCaller() {
        return eventCaller;
    }


}
