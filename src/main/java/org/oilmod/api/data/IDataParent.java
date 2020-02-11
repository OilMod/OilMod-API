package org.oilmod.api.data;

import org.oilmod.api.util.IUpdatable;

import java.util.Map;

/**
 * Created by sirati97 on 17.01.2016.
 */
public interface IDataParent extends IUpdatable {
    void registerIData(IData<?> iData);
    Map<String, IData<?>> getRegisteredIData();
    IUpdatable getUpdatableParent();
    void setUpdatableParent(IUpdatable updatable);

    @Override
    default void markUpdated() {
        IUpdatable updatable = getUpdatableParent();
        if (updatable!=null)updatable.markUpdated();
    }
}
