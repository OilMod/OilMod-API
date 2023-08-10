package org.oilmod.api.registry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.OilMod;
import org.oilmod.api.util.OilKey;
import org.oilmod.util.ExceptionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static org.oilmod.api.util.Util.checkName;
import static org.oilmod.util.Strings.simpleName;
import static org.oilmod.util.LamdbaCastUtils.cast;

/**
 * Registry instance exists per mod, the global helper is RegistryHelperBase
 * @param <Type>
 * @param <TReg>
 * @param <MPI>
 * @param <Provider>
 */
public abstract class RegistryBase<Type, TReg extends RegistryBase<Type, TReg, MPI, Provider>, MPI extends RegistryMPIBase<Type, TReg, MPI, Provider>, Provider extends RegistryHelperBase<Type, TReg, MPI, Provider, ? extends Provider>> {
    private final OilMod mod;
    private boolean registered;
    private Object nmsObject;
    private final Provider registryHelper;
    private final String registryContext;


    private final Map<String, Type> mapEntry = new Object2ObjectOpenHashMap<>();
    private final Set<Type> registeredSet = new ObjectOpenHashSet<>();
    private List<DeferredRegistryEntry<Type>> deferredEntries = null;
    private final Set<Type> registeredSetReadOnly = Collections.unmodifiableSet(registeredSet);

    /**
     * Creates new instance of Registry
     * @param mod associated mod with this item registry
     * @param registryHelper provider for this
     * @param registryContext
     */
    protected RegistryBase(OilMod mod, Provider registryHelper, String registryContext) {
        this.mod = mod;
        this.registryHelper = registryHelper;
        this.registryContext = registryContext;
        checkName(registryContext);
        canCtor();
        getRegistryHelper().initRegister(cast(this), (success, nmsObject) -> {
            registered = success;
            RegistryBase.this.nmsObject = nmsObject;
        });
    }

    protected void canCtor(){
        if (getMod().isConstructed()) throw new IllegalStateException(String.format("%s can only be created during construction of mod. This should only be called by the implementation of create(OilMod mod) in %s (currently %s)", simpleName(getClass()), simpleName(registryHelper.getProviderClass()), simpleName(registryHelper.getImplementationClass())));
    }



    public Set<Type> getRegistered() {
        return registeredSetReadOnly;
    }

    public Type get(String key) {
        return mapEntry.get(key);
    }

    /**
     *
     * @return mod associated with this item registry
     */
    public OilMod getMod() {
        return mod;
    }

    /**
     * Registers an $(Type) for your mod
     */
    public <T extends Type> DeferredObject<T> register(OilKey key, Supplier<T> entrySupplier) {
        //Check registry
        if (!registered) {
            throw new IllegalStateException(String.format("%s was not successfully initialized", simpleName(getClass())));
        }
        T entry = entrySupplier.get(); //todo make those lazy and subscribe DeferredObject / see blow

        //Check entry and avoid duplicates
        Validate.isTrue(!registeredSet.contains(entry), "Cannot register object twice");
        Validate.isTrue(!mapEntry.containsKey(key.getKeyString()), "Cannot register same key twice. Key: " + key);
        getRegistryHelper()._preregister(key, cast(this), entry); //this can even be called if we are deferred

        //Apply key
        key = key.makeContextual(getRegistryContext());
        if (entry instanceof IKeySettable) {
            ((IKeySettable) entry).setOilKey(key);
        }

        //register local
        registeredSet.add(entry);
        mapEntry.put(key.getKeyString(), entry);

        //register global  //todo make lazy here / see above
        if (isDeferred()) {
            deferredEntries.add(new DeferredRegistryEntry<>(key, entry));
        } else {
            getRegistryHelper()._register(key, cast(this), entry);
        }

        return new DeferredObject<>(key, this);
    }

    public void registerDeferred() {
        Validate.isTrue(isDeferred(), "Cannot defer already deferred register");
        List<RuntimeException> exceptions = new ObjectArrayList<>();
        for(DeferredRegistryEntry<Type> entry:deferredEntries) {
            try {
                getRegistryHelper()._register(entry.key, cast(this), entry.value);
            } catch (RuntimeException ex) {
                exceptions.add(ex);
            }
        }
        ExceptionUtils.summarizeAndClear(exceptions, "Could not register all deferred entries:", IllegalStateException::new);

        deferredEntries = null; //this register is no longer deferred
    }

    public void defer() {
        Validate.isTrue(!isDeferred(), "Cannot defer already deferred register");
        deferredEntries = new ObjectArrayList<>();
    }

    public boolean isDeferred() {
        return deferredEntries != null;
    }

    /**
     * Registers an $(Type) for your mod
     */
    public <T extends Type>  DeferredObject<T> register(String name, Supplier<T> entrySupplier) {
        return register(getMod().createKey(name), entrySupplier);
    }

    public final Provider getRegistryHelper() {
        return registryHelper;
    }

    /**
     *
     * @return nms version of this object
     */
    public Object getNmsObject() {
        return nmsObject;
    }

    public final String getRegistryContext() {
        return registryContext;
    }


    public static void setRegistries(OilMod mod, Map<Class<? extends RegistryBase<?,?,?,?>>, RegistryBase<?,?,?,?>> map) {
        RegistryHelperBase.registries.forEach((c, rb) -> map.put(c, rb.create(mod)));
    }


}
