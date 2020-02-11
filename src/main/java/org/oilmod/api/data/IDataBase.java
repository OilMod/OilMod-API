package org.oilmod.api.data;

/**
 * Created by sirati97 on 12.02.2016.
 */
public abstract class IDataBase<T> implements IData<T> {
    private String name;
    private IDataParent dataParent;

    public IDataBase(String name, IDataParent dataParent) {
        this.name = name;
        this.dataParent = dataParent;
        dataParent.registerIData(this);
    }

    public String getName() {
        return name;
    }


    public IDataParent getParent() {
        return dataParent;
    }

    @Override
    public void onCloned(Object original) {
        this.onCloned((IData<T>)original);
    }
}
