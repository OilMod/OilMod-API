package de.sirati97.oilmod.api.data;

import de.sirati97.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class BooleanData implements IData<Boolean> {
    private boolean data;
    private String name;
    private DataParent dataParent;

    public BooleanData(String name, DataParent dataParent) {
        this.name = name;
        this.dataParent = dataParent;
        dataParent.registerIData(this);
    }

    public void saveTo(Compound parent, String name) {
        parent.setBoolean(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getBoolean(name);
    }

    public String getName() {
        return name;
    }

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }

    public DataParent getParent() {
        return dataParent;
    }
}
