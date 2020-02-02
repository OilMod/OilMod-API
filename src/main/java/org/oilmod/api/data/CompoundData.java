package org.oilmod.api.data;

import org.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public abstract class CompoundData<T> extends IDataBase<T> implements ICompoundData<T>  {
    private T data;

    public CompoundData(String name, IDataParent dataParent) {
        super(name, dataParent);
    }

    public final void saveTo(Compound parent, String name) {
        Compound compound = parent.createCompound();
        save(compound);
        parent.set(name, compound);
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
