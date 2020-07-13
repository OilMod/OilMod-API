package org.oilmod.api.UI;


import org.oilmod.api.UI.slot.ISlotType;
import org.oilmod.api.rep.inventory.InventoryRep;

public class SlotPanel extends UIElementBase implements IItemElement {
    private int indexOffset;
    private int rows;
    private int columns;
    private InventoryRep inventory;
    private ISlotType type;

    public SlotPanel(int posX, int posY, int rows, int columns, int indexOffset, InventoryRep inventory) {
        this(posX, posY, rows, columns, indexOffset, inventory, UIMPI.getNormalSlotType());
    }

    public SlotPanel(int posX, int posY, int rows, int columns, int indexOffset, InventoryRep inventory, ISlotType type) {
        super(posX, posY);
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
        //we do not need to do any slot operations here as they should be done previously through handle.next(...)
        //with that i mean that the only referencing not resolving IItemElement have to call handle.next
        UIMPI.handleNormal(handle, inventory, type);
    }
}
