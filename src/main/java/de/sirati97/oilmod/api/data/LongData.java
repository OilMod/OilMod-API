package de.sirati97.oilmod.api.data;

import de.sirati97.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class LongData implements IData<Long> {
    private long data;
    private String name;
    private DataParent dataParent;

    public LongData(String name, DataParent dataParent) {
        this.name = name;
        this.dataParent = dataParent;
        dataParent.registerIData(this);
    }

    public void saveTo(Compound parent, String name) {
        parent.setLong(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getLong(name);
    }

    public String getName() {
        return name;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public DataParent getParent() {
        return dataParent;
    }
}
