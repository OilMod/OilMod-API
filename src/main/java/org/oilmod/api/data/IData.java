package org.oilmod.api.data;

import org.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public interface IData<T> {
    void saveTo(Compound parent, String name);
    void loadFrom(Compound parent, String name);
    String getName();
    T getData();
    void setData(T data);
    IDataParent getParent();
    void onCloned(IData<T> original);
    void onCloned(Object original);
}
