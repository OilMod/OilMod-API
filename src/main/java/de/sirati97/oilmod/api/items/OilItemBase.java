package de.sirati97.oilmod.api.items;

import de.sirati97.oilmod.api.items.internal.ItemFactoryBase;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by sirati97 on 15.01.2016.
 */
public class OilItemBase<T extends OilItemStack> {
    private Material material;
    private int data;
    private final String itemIdentifier;
    private String name;
    private Object nmsItem;
    private int maxStackSize;
    private OilSpecificItemstackFactory[] creativeItems;
    private ItemStack[] naturalExamples;

    public OilItemBase(Material material, int data, String itemIdentifier, String name) {
        this(material, data, itemIdentifier, 64, name);
    }

    public OilItemBase(Material material, int data, String itemIdentifier, int maxStackSize, String name) {
        this.material = material;
        this.data = data;
        this.itemIdentifier = itemIdentifier;
        this.maxStackSize = maxStackSize;
        this.name = name;
    }

    public int getData() {
        return data;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName(Player player) {
        return name;
    }

    public String getName() {
        return name;
    }

    public final ItemStack createItemStack(Player player, int size) {
        return ItemFactoryBase.getInstance().createStack(this, player, size);
    }

    public final ItemStack createItemStack(int size) {
        return createItemStack(null, size);
    }

    public final OilSpecificItemstackFactory[] getCreativeItems() {
        if (creativeItems == null) {
            creativeItems = createCreativeItems();
        }
        return creativeItems;
    }

    protected OilSpecificItemstackFactory[] createCreativeItems() {
        return new OilSpecificItemstackFactory[] {new OilSpecificItemstackFactory() {
            @Override
            public ItemStack create() {
                return createItemStack(1);
            }
        }};
    }
    public final ItemStack[] getNaturalExamples() {
        if (naturalExamples == null) {
            naturalExamples = createNaturalExamples();
        }
        return naturalExamples;
    }

    protected ItemStack[] createNaturalExamples() {
        return new ItemStack[]{createItemStack(1)};
    }

    void setNmsItem(Object nmsItem) {
        this.nmsItem = nmsItem;
    }

    public Object getNmsItem() {
        return nmsItem;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public String getItemIdentifier() {
        return itemIdentifier;
    }

    protected T createOilItemStackInstance(NMSItemStack nmsItemStack) {
        //noinspection unchecked
        return (T) new OilItemStack(nmsItemStack, this);
    }

    public final T createOilStack(NMSItemStack nmsItemStack) {
        return createOilItemStackInstance(nmsItemStack);
    }

    public boolean onUse(OilItemStack itemStack, Player player, Action action) {
        return false;
    }

    public boolean onUseOnBlock(OilItemStack itemStack, Player player, Action action, Block blockClicked, BlockFace blockFace) {
        return false;
    }

    public boolean onLeftClick(OilItemStack itemStack, Player player, Action action) {
        return false;
    }

    public boolean onLeftClickOnBlock(OilItemStack itemStack, Player player, Action action, Block blockClicked, BlockFace blockFace) {
        return false;
    }

    public boolean isEnchantable() {
        return getEnchantSelectModifier() > 0;
    }

    public int getEnchantSelectModifier() {
        return 0;
    }

    public boolean canEnchant(Enchantment enchantment) {
        return false;
    }

    public List<String> getStandardDescription() {
        return null;
    }

    public boolean canRepairAnvil(T oilstack, ItemStack itemStack, HumanEntity human) {
        return false;
    }

    public int prepareRepairAnvil(T oilstack, ItemStack itemStack, HumanEntity human) {
        return 0;
    }

    public void repairAnvil(T oilstack, ItemStack itemStack, HumanEntity human) {}

    public boolean canCombineAnvil(T oilstack, ItemStack itemStack, HumanEntity human) {
        return false;
    }

    public void prepareCombineAnvil(T oilstack, ItemStack itemStack, HumanEntity human) {}

    public void combineAnvil(T oilstack, ItemStack itemStack, HumanEntity human) {}
}
