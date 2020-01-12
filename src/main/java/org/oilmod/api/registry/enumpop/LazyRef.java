package org.oilmod.api.registry.enumpop;

import org.oilmod.api.registry.RegistryMPIBase;

public class LazyRef<
        Type extends IEnumPopType<Type, PopEnum, ?, ?, ?>,
        PopEnum extends Enum<PopEnum> & IEnumPopEnum<Type, PopEnum, ?, ?, ?>> {

    private Type value;
    private PopEnum key;

    public LazyRef(LazyResolver<Type, PopEnum> resolver, PopEnum key) {
        this.key = key;
        resolver.add(this);
    }

    public Type getValue() {
        if (value == null)throw new IllegalStateException("Wrong state, still initializing!");
        return value;
    }

    void setValue(Type value) {
        if (this.value != null)throw new IllegalStateException("Cannot set value twice!");
        this.value = value;
    }

    public PopEnum getKey() {
        return key;
    }
}
