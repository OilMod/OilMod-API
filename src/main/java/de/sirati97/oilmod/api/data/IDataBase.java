package de.sirati97.oilmod.api.data;

/**
 * Created by sirati97 on 12.02.2016.
 */
public abstract class IDataBase<T> implements IData<T> {
    private String name;
    private DataParent dataParent;

    public IDataBase(String name, DataParent dataParent) {
        this.name = name;
        this.dataParent = dataParent;
        dataParent.registerIData(this);
    }

    public String getName() {
        return name;
    }

    public DataParent getParent() {
        return dataParent;
    }

    @Override
    public void onCloned(Object original) {
        this.onCloned((IData<T>)original);
    }
}
