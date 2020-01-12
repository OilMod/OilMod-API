package org.oilmod.api.registry.enumpop;

import java.util.EnumMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class LazyResolver<
        Type extends IEnumPopType<Type, PopEnum, ?, ?, ?>,
        PopEnum extends Enum<PopEnum> & IEnumPopEnum<Type, PopEnum, ?, ?, ?>> {
    private final EnumMap<PopEnum, LazyRef<Type, PopEnum>> map;
    private final Supplier<Function<PopEnum, Type>> ctorSupplier;

    public LazyResolver(Class<PopEnum> enumClass, Supplier<Function<PopEnum, Type>> ctorSupplier) {
        this.map = new EnumMap<>(enumClass);
        this.ctorSupplier = ctorSupplier;
    }

    public void resolveAll(PopEnum[] enumMembers) {
        Function<PopEnum, Type> ctor = ctorSupplier.get();
        for (PopEnum member:enumMembers) {
            if (member.isSpecial())continue;
            LazyRef<Type, PopEnum> ref = map.get(member);
            if (ref == null) throw new IllegalStateException("missing lazy reference for " + member);
            ref.setValue(ctor.apply(member));
        }
    }

    public void add(LazyRef<Type, PopEnum> ref) {
        map.put(ref.getKey(), ref);
    }


}
