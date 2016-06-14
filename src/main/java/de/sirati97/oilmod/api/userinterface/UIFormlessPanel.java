package de.sirati97.oilmod.api.userinterface;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public abstract class UIFormlessPanel implements UIPanel {
    private final int size;
    public UIFormlessPanel(int size) {
        this.size = size;
    }

    public int size() {
        return size;
    }

    @Override
    public boolean hasForm() {
        return false;
    }
}
