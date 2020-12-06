package org.oilmod.api.crafting.custom;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.apache.commons.lang3.NotImplementedException;
import org.oilmod.api.rep.crafting.*;
import org.oilmod.api.rep.itemstack.ItemStackConsumerRep;
import org.oilmod.api.util.checkstate.ICheckState;
import org.oilmod.api.util.checkstate.StateHolderFactory;
import org.oilmod.api.util.checkstate.immutable.ImmutableIntState;
import org.oilmod.util.IntFixedRangeSet;

import java.util.Map;

public class ShapedMatcher implements IMatcher {
    private static final StateHolderFactory<ImmutableIntState, ShapedMatcher> MATCHED_TRANSFORMATION =  (currentBackup, maxBackup, key) -> new ImmutableIntState();

    private final int width;
    private final int height;
    private final IIngredient[][][] ingredientsSet;
    private final Object2IntMap<IIngredient> ingreMap;

    public ShapedMatcher(Transformation[] transformations, IIngredient[][] ingredientsSet) {
        this.ingredientsSet = new IIngredient[transformations.length+1][][]; //+1 as we always assume identity matrix
        this.width = ingredientsSet[0].length;
        this.height = ingredientsSet.length;
        this.ingreMap = new Object2IntOpenHashMap<>(getInputSize());
        this.ingredientsSet[0] = ingredientsSet;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                ingreMap.mergeInt(ingredientsSet[i][j], 1, Integer::sum);
            }
        }
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

                    //it is important that slotId for any specific slot matches in all calls used the same valid checkstate
                    if (!ingredients[top][left].check(supplier.getSuppliedShaped(left, top), checkState, top * width + left, getInputSize(), disclaimSlot -> false, reclaimSlot -> false)) {
                        checkState.revertState();
                        continue outer;
                    }
                }
            }

            //this is a bit ridiculous but this is required, todo: how about we change this API to not require this shit
            Object2ObjectMap<IIngredient, IntFixedRangeSet> ingrePosMap = new Object2ObjectOpenHashMap<>(ingreMap.size());
            for (int top = 0; top < height; top++) {
                for (int left = 0; left < width; left++) {//transformer bug
                    IntFixedRangeSet bits = ingrePosMap.computeIfAbsent(ingredients[top][left], iIngredient -> new IntFixedRangeSet(getInputSize(), false));
                    bits.add(top * width + left);
                }
            }

            for (Map.Entry<IIngredient, IntFixedRangeSet> entry:ingrePosMap.entrySet()) {
                if (!entry.getKey().confirmState(supplier, entry.getValue(), entry.getValue().size(), entry.getValue().size(), checkState, disclaimSlot -> false)) {
                    checkState.revertState();
                    continue outer;
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
        int trans = checkState.getTag(this, MATCHED_TRANSFORMATION).get();
        IIngredient[][] ingredients = ingredientsSet[trans];
        int width = ingredients[0].length;
        int height = ingredients.length;

        for (int top = 0; top < height; top++) {
            for (int left = 0; left < width; left++) {
                IIngredientAccessor accessor = supplier.getSuppliedShaped(left, top);
                //it is important that slotId for any specific slot matches in all calls used the same valid checkstate
                int slotId =top * width + left;
                //we do not rematch during processing but in the last round some resource might have run out, so this check catches that. checking if a stack is empty is not sufficient as some stack dont get emptied but changed during crafting. e.g. water bucket -> bucket
                if (!ingredients[top][left].check(accessor, checkState, slotId, getInputSize(), disclaimSlot -> false, reclaimSlot -> false)) return 0;
                int newAmount = ingredients[top][left].consume(accessor, slotId, stackConsumer, amount, checkState, simulate);
                amount = Math.min(amount, newAmount);
            }
        }

        return amount;
    }

}
