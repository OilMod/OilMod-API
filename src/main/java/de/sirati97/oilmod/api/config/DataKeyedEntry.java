package de.sirati97.oilmod.api.config;

/**
 * Created by sirati97 on 25.06.2016 for OilMod-Api.
 */
public class DataKeyedEntry<Type> {
    private final String key;
    private final Type value;
    private final DataType type;

    public DataKeyedEntry(String key, Type value, DataType type) {
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
