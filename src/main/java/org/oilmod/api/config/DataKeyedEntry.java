package org.oilmod.api.config;

import org.apache.commons.lang3.Validate;

/**
 * Created by sirati97 on 25.06.2016 for OilMod-Api.
 */
public class DataKeyedEntry<Type> {
    private final String key;
    private final Type value;
    private final DataType type;

    public DataKeyedEntry(String key, Type value, DataType type) {
        Validate.isInstanceOf(type.getJavaClass(), value);
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public DataType getType() {
        return type;
    }

    public Type getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
