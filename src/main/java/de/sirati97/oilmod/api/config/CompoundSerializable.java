package de.sirati97.oilmod.api.config;

/**
 * Created by sirati97 on 17.01.2016.
 */
public interface CompoundSerializable {
    void load(Compound compound);
    void save(Compound compound);
    CompoundSerializable cloneIfClonable();
}
