package org.oilmod.api.crafting.custom;

import org.apache.commons.lang3.NotImplementedException;
import org.oilmod.lib.lgpl21.forge.ForgeShapelessRecipeHelper;
import org.oilmod.api.rep.crafting.*;
import org.oilmod.api.rep.itemstack.ItemStackConsumerRep;
import org.oilmod.api.util.checkstate.ICheckState;
import org.oilmod.api.util.checkstate.StateHolderFactory;
import org.oilmod.api.util.checkstate.immutable.ImmutableState;

public class ShapelessMatcher implements IMatcher {
    private static final StateHolderFactory<ImmutableState<int[]>, ShapelessMatcher> MATCHED_TRANSFORMATION =  (currentBackup, maxBackup, key) -> new ImmutableState<>(int[].class);

    private final int size;
    private final IIngredient[] ingredients;

    public ShapelessMatcher(IIngredient[] ingredients) {
        this.size = ingredients.length;
        this.ingredients = ingredients;
    }

    @Override
    public boolean isShaped() {
        return false;
    }

    @Override
    public boolean check(IIngredientSupplier supplier, ICheckState checkState) {
        if (supplier.getSuppliedAmount() != size)return false;
        checkState.requireMaxBackup(1);



        checkState.backupState();
        int[] mapping = ForgeShapelessRecipeHelper.findMatches(size, (ingreId, itemId) -> {
            //i am just thinking this might not be it, state needs to be reverted when backtracking
            checkState.requireMaxBackup(1);
            checkState.backupState();
            boolean result = ingredients[ingreId].check(supplier.getSuppliedShapeless(itemId), checkState, itemId);
            if (result) {
                checkState.confirmState();
            } else {
                checkState.revertState();
            }
            return result;
        });
        if (mapping == null) {
            checkState.revertState();
            return false;
        }

        checkState.getTag(this, MATCHED_TRANSFORMATION).set(mapping);
        checkState.confirmState();
        return true;
    }

    protected int findMatchingTrans(IIngredientSupplier supplier, ICheckState checkState) {


        return -1;
    }

    @Override
    public boolean checkPartial(IIngredientSupplier supplier, IPartialCheckState checkState) {
        throw new NotImplementedException("todo");
    }

    @Override
    public int getInputWidth() {
        return -1;
    }

    @Override
    public int getInputHeight() {
        return -1;
    }

    @Override
    public int getInputSize() {
        return size;
    }

    @Override
    public int process(IIngredientSupplier supplier, ICheckState checkState, ItemStackConsumerRep stackConsumer, int amount, boolean simulate) {
        int[] mappings = checkState.getTag(this, MATCHED_TRANSFORMATION).get();

        for (int i = 0; i < size; i++) {
            IIngredientAccessor accessor = supplier.getSuppliedShapeless(i);
            int ingre = mappings[i];
            if (!ingredients[ingre].check(accessor, checkState, i)) return 0;
            int newAmount = ingredients[ingre].consume(accessor, i, stackConsumer, amount, supplier.getSupSlotMaxStack(i), checkState, simulate);
            amount = Math.min(amount, newAmount);
        }


        return amount;
    }

}
