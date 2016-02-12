package de.sirati97.oilmod.api.data;

import de.sirati97.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public abstract class CompoundData<T> implements IData<T>, ICompoundData<T>  {
    private T data;
    private String name;
    private DataParent dataParent;

    public CompoundData(String name, DataParent dataParent) {
        this.name = name;
        this.dataParent = dataParent;
        dataParent.registerIData(this);
    }

    public final void saveTo(Compound parent, String name) {
        save(parent.createCompound(name));
    }

    public final void loadFrom(Compound parent, String name) {
        this.data = load(parent.getCompound(name));
    }

    public String getName() {
        return name;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DataParent getParent() {
        return dataParent;
    }
}
