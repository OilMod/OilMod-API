package org.oilmod.api.registry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.OilMod;
import org.oilmod.api.util.OilKey;
import org.oilmod.spi.provider.ImplementationBase;
import org.oilmod.util.Strings;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static org.oilmod.util.ReflectionUtils.resolveGenericSuperClass;

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
        Validate.isTrue(generics[1] == reg[1], "Malformed generics, mpi does not match: %s vs %s", Strings.simpleName(generics[1]), Strings.simpleName(reg[1]));

        registries.put(regClass, this);
    }

    public Class<TReg> getRegistryClass() {
        return regClass;
    }



    @Override
    public void onReady() {
        //we are adding our own event code todo: i am unhappy with how this is solved, when we have an event bus use that one instead!
        BiConsumer<OilMod, TReg> _eventCaller = OilMod.ModHelper.getEventCaller(getMpi().getRegistryClass());
        eventCaller = (mod, reg) -> {
            _eventCaller.accept(mod, reg);
            if (yetToRegister.size() > 0) {
                yetToRegister.remove(mod);
                if (yetToRegister.size() == 0) {
                    afterAllMods();
                }
            }
        };
    }

    private final Map<OilKey, Type> mapEntry = new Object2ObjectOpenHashMap<>();
    private final Map<OilKey, TReg> mapRegistry = new Object2ObjectOpenHashMap<>();
    private final Set<Type> registered = new ObjectOpenHashSet<>();
    private final Set<Type> registeredReadOnly = Collections.unmodifiableSet(registered);
    private final Set<OilMod> yetToRegister = new ObjectOpenHashSet<>();


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


    /**Only for internal use! Call registry.register instead!
     *
     * This should be overridden to detect conflicts early*/
    protected final  <T extends Type> void _preregister(OilKey key, TReg registry, T entry) {
        //todo this is unsafe! make this threading safe!
        Validate.isTrue(!registered.contains(entry), "Cannot register object twice");
        Validate.isTrue(!mapEntry.containsKey(key), "Cannot register same key twice. Key: " + key.toString());
    }
    /**Only for internal use! Call registry.register instead!*/
    protected final  <T extends Type> void _register(OilKey key, TReg registry, T entry) {
        _preregister(key, registry, entry);
        //todo this is unsafe! make this threading safe! probably best solution is to not register globally until all mods did theirs and then combining
        mapEntry.put(key, entry);
        mapRegistry.put(key, registry);
        registered.add(entry);
        onRegister(key, registry, entry);
    }

    protected <T extends Type> void onRegister(OilKey key, TReg registry, T entry) {

    }


    protected void initRegister(TReg register, InitRegisterCallback callback) {
        if (register.getMod().isMod()) { //Minecraft and OilMod are assumed to be ignored when checking if mods are yet to be called for a specific registry
            yetToRegister.add(register.getMod());
        }
        callback.callback(true, null);
    }

    protected abstract TReg create(OilMod mod);

    public BiConsumer<OilMod, TReg> getEventCaller() {
        return eventCaller;
    }

    protected void afterAllMods() {
        System.out.println(String.format("All mods were called for the registry %s. Postprocessing can be done now!\n", Strings.simpleName(regClass)));
    }



    public static void assertAllEventsFired() {
        if (registries.values().stream().anyMatch((h) -> h.yetToRegister.size() > 0))  {
            StringBuilder errorSb = registries.values().stream()
                    .filter((h) -> h.yetToRegister.size() > 0)
                    .collect(StringBuilder::new,
                            (sb, h) ->
                                    sb.append(String.format("Registry %s is yet to be called for mods: %s, but it was asserted that all should have been called!\n", Strings.simpleName(h.regClass), h.yetToRegister.stream()
                                            .map(OilMod::getInternalName)
                                            .collect(Collectors.joining(", ")))),
                            StringBuilder::append);
            throw new IllegalStateException(errorSb.toString());
        }
    }


}
