package org.oilmod.api.util;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import org.apache.commons.lang.Validate;
import org.oilmod.api.items.EnchantmentType;
import org.oilmod.api.util.OilKey;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class OilRegistry<T extends IKeyed> {
    private final TMap<OilKey, T> map = new THashMap<>();
    private final Set<T> registered = new THashSet<>();
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
