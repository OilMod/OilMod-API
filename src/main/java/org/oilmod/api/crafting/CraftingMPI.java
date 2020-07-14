package org.oilmod.api.crafting;

import org.apache.commons.lang3.Validate;
import org.oilmod.api.UI.IItemInteractionHandler;
import org.oilmod.api.UI.IItemRef;
import org.oilmod.api.UI.UI;
import org.oilmod.api.UI.slot.ISlotType;
import org.oilmod.api.rep.crafting.ICraftingManager;
import org.oilmod.api.rep.crafting.IResultCategory;
import org.oilmod.api.rep.entity.EntityPlayerRep;
import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.spi.mpi.SingleMPI;
import org.oilmod.spi.provider.ImplementationBase;

public class CraftingMPI extends SingleMPI<CraftingMPI, CraftingMPI.Helper<?>> {
    private static CraftingMPI instance;

    @Override
    protected void onSetProvider(CraftingMPI.Helper provider) {
        instance = this;
    }


    public static ICraftingManager getWorkbench() {
        return instance.getProvider().getWorkbench();
    }

	
    /*public static IItemInteractionHandler getNativeHandler() {
		return instance.getProvider().getNativeHandler();
	}
    public static IItemInteractionHandler getCustomHandler() {
		return instance.getProvider().getCustomHandler();
	}*/

    //Useful regex
    //\((\w+ (\w+)\,)*\));
    //from:   protected abstract (\w+ )((\w+\()(\w+ (\w+\,? ?))*\));
    //to:     public static $1$2 \{\n\t\treturn instance.getProvider().$3$5\);\n\t}

    public abstract static class Helper<Impl extends Helper<Impl>> extends ImplementationBase<CraftingMPI, Helper<?>, Impl> {

        protected abstract ICraftingManager getWorkbench();

        
        
        //protected abstract IItemInteractionHandler getNativeHandler();
        //protected abstract IItemInteractionHandler getCustomHandler();


    }
}
