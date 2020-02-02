package org.oilmod.api.util;

/**
 * Created by sirati97 on 03.07.2016 for OilMod-Api.
 */
@FunctionalInterface
public interface Factory<T> {
    T create();
}
