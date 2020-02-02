package org.oilmod.api.registry;

import org.oilmod.spi.mpi.SingleMPI;

public abstract class SimpleRegistryMPIBase<
        Type,
        TReg extends RegistryBase<Type, TReg, MPI, Provider>,
        MPI extends SimpleRegistryMPIBase<Type, TReg, MPI, Provider>,
        Provider extends RegistryHelperBase<Type, TReg, MPI, Provider, ? extends Provider>>
        extends RegistryMPIBase<Type, TReg, MPI, Provider> {


    @Override
    public boolean hasDefaultProvider() {
        return true;
    }

    @Override
    public abstract Provider createDefaultProvider();
}
