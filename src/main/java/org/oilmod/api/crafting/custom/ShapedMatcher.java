package org.oilmod.api.crafting.custom;

import org.apache.commons.lang3.NotImplementedException;
import org.oilmod.api.rep.crafting.*;
import org.oilmod.api.rep.itemstack.ItemStackConsumerRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.util.checkstate.ICheckState;
import org.oilmod.api.util.checkstate.StateHolderFactory;
import org.oilmod.api.util.checkstate.immutable.ImmutableIntState;

public class ShapedMatcher implements IMatcher {
    private static final StateHolderFactory<ImmutableIntState, ShapedMatcher> MATCHED_TRANSFORMATION =  (currentBackup, maxBackup, key) -> new ImmutableIntState();

    private final int width;
    private final int height;
    private final IIngredient[][][] ingredientsSet;

    public ShapedMatcher(Transformation[] transformations, IIngredient[][] ingredientsSet) {
        this.ingredientsSet = new IIngredient[transformations.length+1][][]; //+1 as we always assume identity matrix
        this.width = ingredientsSet[0].length;
        this.height = ingredientsSet.length;
        this.ingredientsSet[0] = ingredientsSet;
        for (int i = 0; i < transformations.length; i++) {
            this.ingredientsSet[i+1] = transformations[i].transform2dArray(ingredientsSet);
        }
    }

    @Override
    public boolean isShaped() {
        return true;
    }

    @Override
    public boolean check(IIngredientSupplier supplier, ICheckState checkState) {
        int match = findMatchingTrans(supplier, checkState);
        return match >= 0;
    }

    protected int findMatchingTrans(IIngredientSupplier supplier, ICheckState checkState) {
        checkState.requireMaxBackup(1);
        outer:
        for (int i = 0; i < ingredientsSet.length; i++) {
            IIngredient[][] ingredients = ingredientsSet[i];
            int width = ingredients[0].length;
            int height = ingredients.length;
            if (supplier.getSuppliedHeight() != height || supplier.getSuppliedWidth() != width) continue;

            checkState.backupState();
            for (int top = 0; top < height; top++) {
                for (int left = 0; left < width; left++) {//transformer bug
                    if (!ingredients[top][left].check(supplier.getSupplied(left, top), checkState)) {
                        checkState.revertState();
                        continue outer;
                    }
                }
            }
            checkState.getTag(this, MATCHED_TRANSFORMATION).set(i);
            checkState.confirmState();
            return i;
        }

        return -1;
    }

    @Override
    public boolean checkPartial(IIngredientSupplier supplier, IPartialCheckState checkState) {
        throw new NotImplementedException("todo");
    }

    @Override
    public int getInputWidth() {
        return width;
    } //todo think about how to handle transformation that change(swap) dim

    @Override
    public int getInputHeight() {
        return height;
    }

    @Override
    public int getInputSize() {
        return width*height;
    }

    @Override
    public int process(IIngredientSupplier supplier, ICheckState checkState, ItemStackConsumerRep stackConsumer, int amount, boolean simulate) {
        if (simulate)return 0;//todo: if we know how to simulate stop doing that
        int trans = checkState.getTag(this, MATCHED_TRANSFORMATION).get();
        IIngredient[][] ingredients = ingredientsSet[trans];
        int width = ingredients[0].length;
        int height = ingredients.length;

        for (int top = 0; top < height; top++) {
            for (int left = 0; left < width; left++) {
                ItemStackRep supplied = supplier.getSupplied(left, top);
                ItemStackRep result = ingredients[top][left].consume(supplied, amount, checkState);
                //todo find good way to reduce amount if insufficient
                //todo find way to update base inventory, that is not as hacky as this
                supplied.setAmount(result.getAmount());
                result.getItemStackState().applyTo(supplied, false, true);
                //if (result.getAmount() < 0)
            }
        }

        return 0;
    }

}
