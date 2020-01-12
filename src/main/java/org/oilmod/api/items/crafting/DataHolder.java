package org.oilmod.api.items.crafting;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.oilmod.api.util.Factory;

import javax.swing.plaf.ListUI;
import java.util.Map;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class DataHolder {
    private Map<Object, Object> data = new Object2ObjectOpenHashMap<>();
    private boolean inUse = false;

    public <T> T get(Object holder, Factory<T> factory) {
        if (!inUse) throw new IllegalStateException("Cannot use out of use DataHolder");
        //noinspection unchecked
        T result = (T)data.get(holder);
        if (result == null) {
            result = factory.create();
            data.put(holder, result);
        }
        return result;
    }

    public void use() {
        if (inUse) throw new IllegalStateException("Cannot use DataHolder twice");
        inUse = true;
    }

    public void dispose() {
        if (!inUse) throw new IllegalStateException("Cannot dispose DataHolder twice");
        data.clear();
        inUse = false;
    }
}
