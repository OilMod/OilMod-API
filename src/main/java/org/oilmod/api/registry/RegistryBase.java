package org.oilmod.api.registry;

import org.oilmod.api.OilMod;
import org.oilmod.api.util.OilKey;

import java.util.Map;

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
        canCtor();
        getRegistryHelper().initRegister(cast(this), (success, nmsObject) -> {
            registered = success;
            RegistryBase.this.nmsObject = nmsObject;
        });
    }

    protected void canCtor(){
        if (getMod().isConstructed()) throw new IllegalStateException(String.format("%s can only be created during construction of mod. This should only be called by the implementation of create(OilMod mod) in %s (currently %s)", getClass().getSimpleName(), registryHelper.getProviderClass().getSimpleName(), registryHelper.getImplementationClass().getSimpleName()));
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
    public <T extends Type> void register(OilKey key, T entry) {
        if (!registered) {
            throw new IllegalStateException(String.format("%s was not successfully initialized", getClass().getSimpleName()));
        }
        key = key.makeContextual(getRegistryContext());
        if (entry instanceof IKeySettable) {
            ((IKeySettable) entry).setOilKey(key);
        }
        getRegistryHelper().register(key, cast(this), entry);
    }
    /**
     * Registers an $(Type) for your mod
     */
    public <T extends Type> void register(String name, T entry) {
        register(getMod().createKey(name), entry);
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
