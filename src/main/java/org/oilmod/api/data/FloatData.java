package org.oilmod.api.data;

import org.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class FloatData extends IPrimaryDataBase<Float> {
    private float data;

    public FloatData(String name, DataParent dataParent) {
        super(name, dataParent);
    }

    public void saveTo(Compound parent, String name) {
        parent.setFloat(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getFloat(name);
    }

    public Float getData() {
        return data;
    }

    public void setData(Float data) {
        this.data = data;
    }
}
