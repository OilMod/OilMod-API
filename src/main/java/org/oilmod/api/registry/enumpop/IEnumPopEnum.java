package org.oilmod.api.registry.enumpop;

import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.registry.RegistryMPIBase;

public interface IEnumPopEnum<
        Type extends IEnumPopType<Type, PopEnum, TReg, MPI, Provider>,
        PopEnum extends Enum<PopEnum> & IEnumPopEnum<Type, PopEnum, TReg, MPI, Provider>,
        TReg extends EnumPopRegistry<Type, PopEnum, TReg, MPI, Provider>,
        MPI extends RegistryMPIBase<Type, TReg, MPI, Provider>,
        Provider extends EnumPopRegistryHelperBase<Type, PopEnum, TReg, MPI, Provider, ? extends Provider>> {

    default PopEnum asPopEnum() {
        //noinspection unchecked
        return (PopEnum) this;
    }

    PopEnum missing();
    PopEnum custom();
    Provider getProvider();

    default boolean isMissing() {
        return this.equals(missing());
    }

    default boolean isCustom() {
        return this.equals(custom());
    }

    default boolean isSpecial() {
        return isCustom() || isMissing();
    }

    default Type getAssociated() {
        if (isSpecial())return null;
        return getProvider().get(asPopEnum());
    }
}
