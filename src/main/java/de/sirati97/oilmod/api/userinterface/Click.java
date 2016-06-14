package de.sirati97.oilmod.api.userinterface;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public class Click {
    private final ClickType type;
    private final int subType;

    public Click(ClickType type, int subType) {
        this.type = type;
        this.subType = subType;
    }

    public ClickType getType() {
        return type;
    }

    public int getSubType() {
        return subType;
    }
}
