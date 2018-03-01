package org.oilmod.api.items.crafting;

import org.oilmod.api.util.Factory;
import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class DataHolder {
    private TMap<Object, Object> data = new THashMap<>();

    public <T> T get(Object holder, Factory<T> factory) {
        //noinspection unchecked
        T result = (T)data.get(holder);
        if (result == null) {
            result = factory.create();
            data.put(holder, result);
        }
        return result;
    }
}
