package org.oilmod.api.util;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Deprecated
public class OilRegistry<T extends IKeyed> {
    private final Map<OilKey, T> map = new Object2ObjectOpenHashMap<>();
    private final Set<T> registered = new ObjectOpenHashSet<>();
    private final Set<T> registeredReadOnly = Collections.unmodifiableSet(registered);

    public void register(T obj) {
        Validate.isTrue(!registered.contains(obj), "Cannot register object twice");
        Validate.isTrue(!map.containsKey(obj.getOilKey()), "Cannot register same key twice. Key: " + obj.getOilKey().toString());
        map.put(obj.getOilKey(), obj);
        registered.add(obj);
    }

    public Set<T> getRegistered() {
        return registeredReadOnly;
    }

    public T get(OilKey key) {
        return map.get(key);
    }

    public void unregister(OilKey key) {
        registered.remove(map.remove(key));
    }
}
