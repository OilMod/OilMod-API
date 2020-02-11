package org.oilmod.api.data;

import org.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class DoubleData extends IPrimaryDataBase<Double> {
    private double data;

    public DoubleData(String name, IDataParent dataParent) {
        super(name, dataParent);
    }

    public void saveTo(Compound parent, String name) {
        parent.setDouble(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getDouble(name);
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
        getParent().markUpdated();
    }
}
