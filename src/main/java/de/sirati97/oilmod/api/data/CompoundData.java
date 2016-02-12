package de.sirati97.oilmod.api.data;

import de.sirati97.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public abstract class CompoundData<T> extends IDataBase<T> implements ICompoundData<T>  {
    private T data;

    public CompoundData(String name, DataParent dataParent) {
        super(name, dataParent);
    }

    public final void saveTo(Compound parent, String name) {
        save(parent.createCompound(name));
    }

    public final void loadFrom(Compound parent, String name) {
        this.data = load(parent.getCompound(name));
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}