package org.oilmod.api.registry;

import org.oilmod.api.util.Factory;
import org.oilmod.api.util.OilKey;

import java.util.function.Consumer;
public class DeferredRegister<Type, TReg extends RegistryBase<? super Type, TReg, ?, ?>> {
    public DeferredRegister(OilKey key, Class<TReg> registry, Factory<Type> factory, Consumer<DeferredObject<? extends Type>> consumer) {
        this.key = key;
        this.registry = registry;
        this.factory = factory;
        this.consumer = consumer;
    }

    public final OilKey key;
    public final Class<TReg> registry;
    public final Factory<Type> factory;
    public final Consumer<DeferredObject<? extends Type>> consumer;


    public void register(TReg registry) {
        DeferredObject<? extends Type> def = registry.register(this.key, factory::create);
        consumer.accept(def);
    }

}
