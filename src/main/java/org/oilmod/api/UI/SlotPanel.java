package org.oilmod.api.UI;


import org.oilmod.api.UI.slot.ISlotType;
import org.oilmod.api.UI.slot.ISlotTypePreviewable;
import org.oilmod.api.crafting.ICraftingProcessor;
import org.oilmod.api.rep.crafting.IResultCategory;
import org.oilmod.api.rep.inventory.InventoryRep;

public class SlotPanel extends FlexPosElement implements IItemElement {
    private final int indexOffset;
    private final int rows;
    private final int columns;
    private final InventoryRep inventory;
    private final ISlotType type;



    public SlotPanel(int posX, int posY, ICraftingProcessor processor, IResultCategory resultCategory) {
        this(new FixedPos(posX, posY), processor, resultCategory);
    }

    public SlotPanel(IPosition pos, ICraftingProcessor processor, IResultCategory resultCategory) {
        this(pos, processor.getResultInventory(resultCategory), processor, resultCategory);
    }

    private SlotPanel(IPosition pos, InventoryRep inv, ICraftingProcessor processor, IResultCategory resultCategory) {
        this(pos, inv.is2d()?inv.getHeight():1, inv.getWidth(), 0, inv, UIMPI.getProcessingSlotType(processor, resultCategory));
    }

    public SlotPanel(int posX, int posY, int rows, int columns, int indexOffset, InventoryRep inventory) {
        this(new FixedPos(posX, posY), rows, columns, indexOffset, inventory, UIMPI.getNormalSlotType());
    }

    public SlotPanel(int posX, int posY, int rows, int columns, int indexOffset, InventoryRep inventory, ISlotType type) {
        this(new FixedPos(posX, posY), rows, columns, indexOffset, inventory, type);
    }

    public SlotPanel(IPosition pos, int rows, int columns, int indexOffset, InventoryRep inventory) {
        this(pos, rows, columns, indexOffset, inventory, UIMPI.getNormalSlotType());
    }

    public SlotPanel(IPosition pos, int rows, int columns, int indexOffset, InventoryRep inventory, ISlotType type) {
        super(pos);
        this.indexOffset = indexOffset;
        //Validate.isTrue(inventory.getStorageSize() == rows*columns, "inventory.getStorageSize does not match columns*rows");
        this.rows = rows;
        this.columns = columns;
        this.inventory = inventory;
        this.type = type;
    }

    public ISlotType getSlotType() {
        return type;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public int toIndex(int row, int column) {
        return indexOffset + column + row*getColumns();
    }


    @Override
    public void handle(IItemRef handle) {
        //we do not need to do any slot operations here as they should be done previously through handle.deferTo(...)
        //with that i mean that the only referencing not resolving IItemElement have to call handle.deferTo
        if (handle.isRedirectPreview() && getSlotType() instanceof ISlotTypePreviewable) {
            UIMPI.handleNormal(handle, ((ISlotTypePreviewable) getSlotType()).getPreviewInventory(), type);
        } else {
            UIMPI.handleNormal(handle, inventory, type);
        }


    }
}
