package org.oilmod.api.registry;

import org.oilmod.api.util.OilKey;

public class DeferredRegistryEntry<Type> {
    public final OilKey key;
    public final Type value;

    public DeferredRegistryEntry(OilKey key, Type value) {
        this.key = key;
        this.value = value;
    }
}
