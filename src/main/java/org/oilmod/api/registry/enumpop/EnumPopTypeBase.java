package org.oilmod.api.registry.enumpop;

import org.apache.commons.lang3.Validate;
import org.oilmod.api.registry.KeySettableBase;
import org.oilmod.api.registry.RegistryMPIBase;

public abstract class EnumPopTypeBase<
        Type extends EnumPopTypeBase<Type, PopEnum, TReg, MPI, Provider>,
        PopEnum extends Enum<PopEnum> & IEnumPopEnum<Type, PopEnum, TReg, MPI, Provider>,
        TReg extends EnumPopRegistry<Type, PopEnum, TReg, MPI, Provider>,
        MPI extends RegistryMPIBase<Type, TReg, MPI, Provider>,
        Provider extends EnumPopRegistryHelperBase<Type, PopEnum, TReg, MPI, Provider, ? extends Provider>> extends KeySettableBase
        implements IEnumPopType<Type, PopEnum, TReg, MPI, Provider> {

    private PopEnum typeEnum;
    private Object nms;

    private Type asCasted() {
        //noinspection unchecked
        return (Type) this;
    }

    protected EnumPopTypeBase(PopEnum typeEnum) {
        this.typeEnum = typeEnum;
        TReg autoReg = getAutoRegistry(typeEnum);
        if (autoReg != null) {
            typeEnum.getProvider().autoRegister(autoReg, asCasted());
        }
    }

    public PopEnum getTypeEnum() {
        return typeEnum;
    }

    protected TReg getAutoRegistry(PopEnum typeEnum) {
        if (typeEnum.isSpecial())return null;
        return typeEnum.getProvider().getGameRegistry();
    }

    @Override
    public Object getNMS() {
        return nms;
    }

    @Override
    public void setNMS(Object nms) {
        Validate.isTrue(this.nms == null, "Cannot manually set nms! This is done by the registry helper!");
        this.nms = nms;
    }
}
