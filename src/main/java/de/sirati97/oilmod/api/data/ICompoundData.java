package de.sirati97.oilmod.api.data;

import de.sirati97.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public interface ICompoundData<T> {
    void save(Compound compound);
    T load(Compound compound);
}
