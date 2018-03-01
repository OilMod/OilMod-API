package org.oilmod.api.data;

/**
 * Created by sirati97 on 12.02.2016.
 */
public abstract class IClonableDataBase<T extends Cloneable> extends IDataBase<T>{
    public IClonableDataBase(String name, DataParent dataParent) {
        super(name, dataParent);
    }
}
