package de.sirati97.oilmod.api.data;

import de.sirati97.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class DoubleData implements IData<Double> {
    private double data;
    private String name;
    private DataParent dataParent;

    public DoubleData(String name, DataParent dataParent) {
        this.name = name;
        this.dataParent = dataParent;
        dataParent.registerIData(this);
    }

    public void saveTo(Compound parent, String name) {
        parent.setDouble(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getDouble(name);
    }

    public String getName() {
        return name;
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }

    public DataParent getParent() {
        return dataParent;
    }
}
