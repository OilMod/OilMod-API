package org.oilmod.api.util;

import org.apache.commons.lang3.Validate;

public interface DeferredProvider<T> {
    T _provide();
    default T get() {
        return Validate.notNull(_provide(), "Deferred value is not yet available");
    }
    default boolean isAvailable() {
        return _provide() != null;
    }
}
