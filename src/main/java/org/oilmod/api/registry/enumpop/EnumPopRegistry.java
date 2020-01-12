package org.oilmod.api.registry.enumpop;

import org.oilmod.api.OilMod;
import org.oilmod.api.registry.RegistryBase;
import org.oilmod.api.registry.RegistryMPIBase;

public abstract class EnumPopRegistry<
        Type extends IEnumPopType<Type, PopEnum, TReg, MPI, Provider>,
        PopEnum extends Enum<PopEnum> & IEnumPopEnum<Type, PopEnum, TReg, MPI, Provider>,
        TReg extends EnumPopRegistry<Type, PopEnum, TReg, MPI, Provider>,
        MPI extends RegistryMPIBase<Type, TReg, MPI, Provider>,
        Provider extends EnumPopRegistryHelperBase<Type, PopEnum, TReg, MPI, Provider, ? extends Provider>>
        extends RegistryBase<Type, TReg, MPI, Provider> {

    /**
     * Creates new instance of Registry
     *
     * @param mod            associated mod with this item registry
     * @param registryHelper provider for this
     */
    protected EnumPopRegistry(OilMod mod, Provider registryHelper, String registryContext) {
        super(mod, registryHelper, registryContext);
    }
}
