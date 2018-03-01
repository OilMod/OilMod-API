package org.oilmod.api.config;

/**
 * Created by sirati97 on 25.06.2016 for OilMod-Api.
 */
public class DataIndexedEntry<Type> {
    private final int index;
    private final Type value;
    private final DataType type;

    public DataIndexedEntry(int index, Type value, DataType type) {
        this.index = index;
        this.value = value;
        this.type = type;
    }

    public DataType getType() {
        return type;
    }

    public Type getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }
}
