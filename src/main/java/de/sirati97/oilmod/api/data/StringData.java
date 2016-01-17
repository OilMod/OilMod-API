package de.sirati97.oilmod.api.data;

import de.sirati97.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class StringData implements IData<String> {
    private String data;
    private String name;
    private DataParent dataParent;

    public StringData(String name, DataParent dataParent) {
        this.name = name;
        this.dataParent = dataParent;
        dataParent.registerIData(this);
    }

    public void saveTo(Compound parent, String name) {
        parent.setString(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getString(name);
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public DataParent getParent() {
        return dataParent;
    }
}
