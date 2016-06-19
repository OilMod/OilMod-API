package de.sirati97.oilmod.api.userinterface;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public abstract class UIFormlessPanel implements UIPanel {
    private final int size;
    public UIFormlessPanel(int size) {
        this.size = size;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasForm() {
        return false;
    }
}
