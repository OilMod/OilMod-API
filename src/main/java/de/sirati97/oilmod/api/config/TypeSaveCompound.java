package de.sirati97.oilmod.api.config;

/**
 * Created by sirati97 on 10.02.2016.
 */
public interface TypeSaveCompound extends Compound {
    Object nbtClone();
    TypeSaveDataType getType(String key);
}
