package org.oilmod.api.items;

import java.util.Collections;
import java.util.List;

/**
 * Created by sirati97 on 13.03.2016.
 */
public class ItemDescription {
    private int lastSize = 0;
    private List<String> lines;
    private List<String> linesReadOnly;
    private NMSItemStack itemStack;

    public ItemDescription(List<String> lines, NMSItemStack itemStack) {
        this.lines = lines;
        this.linesReadOnly = Collections.unmodifiableList(lines);
        this.itemStack = itemStack;
        this.lastSize = lines.size();

    }

    /**
     * Initialises this instance of ItemDescription
     */
    public void init() {
        if (!itemStack.hasLore() && lines.size() > 0) {
            /*System.out.println("setted lore first time for " + itemStack.toString() + " " + lines.get(0) + " " + Thread.currentThread().getDisplayName());
            Compound tag = itemStack.getTagCompound();
            System.out.println(tag != null);
            if (tag != null) {
                boolean hasDisplay = tag.containsKey("display");
                System.out.println(hasDisplay);
                if (hasDisplay) {
                    Compound display = tag.getCompound("display");
                    boolean hasLore = tag.containsKey("Lore");
                    System.out.println(hasLore);
                    if (hasLore) {
                        System.out.println(tag.getList("Lore").get(1));
                    }
                }
            }
            if (Thread.currentThread().getDisplayName().equals("Server thread")) {
                printTrace("Wrong item initialisation");
            }*/
            itemStack.updateItemDescription(0, linesReadOnly);
        }
    }

    /**
     *
     * @return site of visible description of the itemstack
     */
    public int getSize() {
        return lastSize;
    }

    /**
     *
     * @return returns size of un-updated description
     */
    public int getVirtualSize() {
        return lines.size();
    }

    /**
     *
     * @return returns a read only version of all the lines
     */
    public List<String> getLines() {
        return linesReadOnly;
    }

    /**
     * Removes the line at the given index
     * @param update should the description of the itemstack be updated immediately? Use carefully to avoid overhead.
     */
    public void removeLine(int index, boolean update) {
        lines.remove(index);
        if (update) {
            forceUpdate();
        }
    }

    /**
     *
     * @return returns the line at the given index
     */
    public String getLine(int index) {
        return lines.get(index);
    }

    /**
     * Sets the line at the index index to line
     * @param update should the description of the itemstack be updated immediately? Use carefully to avoid overhead.
     */
    public void setLine(int index, String line, boolean update) {
        lines.set(index, line);
        if (update) {
            forceUpdate();
        }

    }

    /**
     * Inserts the line at the index index with the text line
     * @param update should the description of the itemstack be updated immediately? Use carefully to avoid overhead.
     */
    public void insertLine(int index, String line, boolean update) {
        lines.add(index, line);
        if (update) {
            forceUpdate();
        }

    }

    /**
     * Appends line at the end of the description
     * @param update should the description of the itemstack be updated immediately? Use carefully to avoid overhead.
     */
    public void appendLine(String line, boolean update) {
        lines.add(line);
        if (update) {
            forceUpdate();
        }
    }

    /**
     *
     */
    public void forceUpdate() {
        itemStack.updateItemDescription(lastSize, linesReadOnly);
        lastSize = lines.size();
    }


}
