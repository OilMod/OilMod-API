package org.oilmod.api.data;

import org.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class ShortData extends IPrimaryDataBase<Short> {
    private short data;

    public ShortData(String name, IDataParent dataParent) {
        super(name, dataParent);
    }

    public void saveTo(Compound parent, String name) {
        parent.setShort(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getShort(name);
    }

    public Short getData() {
        return data;
    }

    public void setData(Short data) {
        this.data = data;
        getParent().markUpdated();
    }
}
