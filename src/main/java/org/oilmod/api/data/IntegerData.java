package org.oilmod.api.data;

import org.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class IntegerData extends IPrimaryDataBase<Integer> {
    private int data;

    public IntegerData(String name, IDataParent dataParent) {
        super(name, dataParent);
    }

    public void saveTo(Compound parent, String name) {
        parent.setInt(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getInt(name);
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
        getParent().markUpdated();
    }
}
