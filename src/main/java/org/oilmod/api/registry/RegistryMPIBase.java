package org.oilmod.api.registry;

import org.oilmod.spi.mpi.SingleMPI;

import static org.oilmod.util.ReflectionUtils.resolveGenericSuperClass;

public abstract class RegistryMPIBase<Type, TReg extends RegistryBase<Type, TReg, MPI, Provider>, MPI extends RegistryMPIBase<Type, TReg, MPI, Provider>, Provider extends RegistryHelperBase<Type, TReg, MPI, Provider, ? extends Provider>> extends SingleMPI<MPI, Provider> {
    private final Class<Type> registryTypeClass;
    private final Class<TReg> registryClass;

    @SuppressWarnings("unchecked")
    protected RegistryMPIBase() {
        Class[] classes = resolveGenericSuperClass(getClass(), RegistryMPIBase.class);
        registryTypeClass = classes[0];
        registryClass = classes[1];
    }

    /*@Override
    public boolean hasDefaultProvider() {
        return true;
    }

    @Override
    public abstract Provider createDefaultProvider();*/ //todo consider adding default impl for registries

    public Class<Type> getRegistryTypeClass() {
        return registryTypeClass;
    }

    public Class<TReg> getRegistryClass() {
        return registryClass;
    }
}
