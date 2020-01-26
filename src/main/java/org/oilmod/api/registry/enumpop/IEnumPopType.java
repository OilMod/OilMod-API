package org.oilmod.api.registry.enumpop;

import org.oilmod.api.registry.RegistryMPIBase;

public interface IEnumPopType<
        Type extends IEnumPopType<Type, PopEnum, TReg, MPI, Provider>,
        PopEnum extends Enum<PopEnum> & IEnumPopEnum<Type, PopEnum, TReg, MPI, Provider>,
        TReg extends EnumPopRegistry<Type, PopEnum, TReg, MPI, Provider>,
        MPI extends RegistryMPIBase<Type, TReg, MPI, Provider>,
        Provider extends EnumPopRegistryHelperBase<Type, PopEnum, TReg, MPI, Provider, ? extends Provider>> {

    PopEnum getTypeEnum();
    Object getNMS();
    void setNMS(Object nms);

}
