package de.sirati97.oilmod.api.data;

import de.sirati97.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class ShortData implements IData<Short> {
    private short data;
    private String name;
    private DataParent dataParent;

    public ShortData(String name, DataParent dataParent) {
        this.name = name;
        this.dataParent = dataParent;
        dataParent.registerIData(this);
    }

    public void saveTo(Compound parent, String name) {
        parent.setShort(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getShort(name);
    }

    public String getName() {
        return name;
    }

    public Short getData() {
        return data;
    }

    public void setData(Short data) {
        this.data = data;
    }

    public DataParent getParent() {
        return dataParent;
    }
}
