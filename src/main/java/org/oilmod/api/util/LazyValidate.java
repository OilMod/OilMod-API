package org.oilmod.api.util;

import java.util.Arrays;
import java.util.function.Supplier;

public class LazyValidate {
    public static void isTrue(final boolean expression, final String message, final Supplier<?>... values) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, Arrays.stream(values).map(Supplier::get).toArray()));
        }
    }
}
