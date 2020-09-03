package org.oilmod.api.test.providers.crafting;

import org.oilmod.api.crafting.CraftingMPI;
import org.oilmod.api.rep.crafting.ICraftingManager;

public class CraftingHelper extends CraftingMPI.Helper<CraftingHelper> {
    @Override
    protected ICraftingManager getWorkbench() {
        return null;
    }
}
