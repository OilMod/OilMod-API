package de.sirati97.oilmod.api.data;

import de.sirati97.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class FloatData implements IData<Float> {
    private float data;
    private String name;
    private DataParent dataParent;

    public FloatData(String name, DataParent dataParent) {
        this.name = name;
        this.dataParent = dataParent;
        dataParent.registerIData(this);
    }

    public void saveTo(Compound parent, String name) {
        parent.setFloat(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getFloat(name);
    }

    public String getName() {
        return name;
    }

    public Float getData() {
        return data;
    }

    public void setData(Float data) {
        this.data = data;
    }

    public DataParent getParent() {
        return dataParent;
    }
}
