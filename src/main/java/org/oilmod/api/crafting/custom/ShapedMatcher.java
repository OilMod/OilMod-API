package org.oilmod.api.crafting.custom;

import org.apache.commons.lang3.NotImplementedException;
import org.oilmod.api.rep.crafting.*;
import org.oilmod.api.util.checkstate.ICheckState;

public class ShapedMatcher implements IMatcher {
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
        checkState.requireMaxBackup(1);
        outer:
        for (IIngredient[][] ingredients : ingredientsSet) {
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

            checkState.confirmState();
            return true;

        }


        return false;
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

}
