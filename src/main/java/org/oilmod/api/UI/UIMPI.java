package org.oilmod.api.UI;

import org.oilmod.api.UI.slot.ISlotType;
import org.oilmod.api.crafting.ICraftingProcessor;
import org.oilmod.api.rep.entity.EntityPlayerRep;
import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.spi.mpi.SingleMPI;
import org.oilmod.spi.provider.ImplementationBase;

public class UIMPI extends SingleMPI<UIMPI, UIMPI.Helper<?>> {
    private static UIMPI instance;

    @Override
    protected void onSetProvider(UIMPI.Helper provider) {
        instance = this;
    }


    /**
     * Automatically calls either Native or Custom depending on presented {@link InventoryRep}
     */
    public static void handleNormal(IItemRef handle, InventoryRep inv, ISlotType type) {
        if (inv.isNative()) {
            instance.getProvider().handleNative(handle, inv, type);
        } else {
            instance.getProvider().handleCustom(handle, inv, type);
        }
    }

    public static void handleNative(IItemRef handle, InventoryRep inv, ISlotType type) {
        instance.getProvider().handleNative(handle, inv, type);
    }

    public static void handleCustom(IItemRef handle, InventoryRep inv, ISlotType type) {
        instance.getProvider().handleCustom(handle, inv, type);
    }

    public static void handleCustom(IItemRef handle, InventoryRep inv, IItemInteractionHandler handler) {
        instance.getProvider().handleCustom(handle, inv, handler);
    }

    public static int getSizeSlots() {
        return instance.getProvider().getSizeSlots();
    }
    public static int getSizeBorder() {
        return instance.getProvider().getSizeBorder();
    }
    public static int getSizeText() {
        return instance.getProvider().getSizeText();
    }
    public static int getSizeItemRender() {
        return instance.getProvider().getSizeItemRender();
    }


    public static void openUI(EntityPlayerRep player, UI ui) {
		instance.getProvider().openUI(player, ui);
	}


    public static ISlotType getNormalSlotType() {
		return instance.getProvider().getNormalSlotType();
	}
    public static ISlotType getTakeOnlySlotType() {
		return instance.getProvider().getTakeOnlySlotType();
	}
    public static ISlotType getProcessingSlotType(ICraftingProcessor processor) {
		return instance.getProvider().getProcessingSlotType(processor);
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

    public abstract static class Helper<Impl extends Helper<Impl>> extends ImplementationBase<UIMPI, Helper<?>, Impl> {

        protected abstract void openUI(EntityPlayerRep player, UI ui);
        protected abstract void handleNative(IItemRef handle, InventoryRep inv, ISlotType type);
        protected abstract void handleCustom(IItemRef handle, InventoryRep inv, ISlotType type);
        protected abstract void handleCustom(IItemRef handle, InventoryRep inv, IItemInteractionHandler handler);
        protected abstract int getSizeSlots();
        protected abstract int getSizeBorder();
        protected abstract int getSizeText();
        protected abstract int getSizeItemRender();
        protected abstract ISlotType getNormalSlotType();
        protected abstract ISlotType getTakeOnlySlotType();
        protected abstract ISlotType getProcessingSlotType(ICraftingProcessor processor);
        
        
        //protected abstract IItemInteractionHandler getNativeHandler();
        //protected abstract IItemInteractionHandler getCustomHandler();


    }
}
