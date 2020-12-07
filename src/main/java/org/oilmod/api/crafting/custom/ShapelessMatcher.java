package org.oilmod.api.crafting.custom;

import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.oilmod.api.rep.crafting.*;
import org.oilmod.api.rep.itemstack.ItemStackConsumerRep;
import org.oilmod.api.util.checkstate.ICheckState;
import org.oilmod.api.util.checkstate.StateHolderFactory;
import org.oilmod.api.util.checkstate.immutable.ImmutableState;
import org.oilmod.util.IntFixedRangeSet;

import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

public class ShapelessMatcher implements IMatcher {
    private static final StateHolderFactory<ImmutableState<int[]>, ShapelessMatcher> MATCHED_TRANSFORMATION =  (currentBackup, maxBackup, key) -> new ImmutableState<>(int[].class);

    private final int size;
    private final IIngredient[] ingredientsStatic;
    private final int[] ingredientsStaticCount;
    private final IIngredient[] ingredientsCompelex;
    private final int[] ingredientsCompelexCount;
    private final IIngredient[] ingredientsAll;

    public ShapelessMatcher(IIngredient[] ingredients) {
        int size = ingredients.length;
        Object2IntMap<IIngredient> statics = new Object2IntOpenHashMap<>(ingredients.length);
        Object2IntMap<IIngredient> complex = new Object2IntOpenHashMap<>(ingredients.length);
        for (int i = 0; i < ingredients.length; i++) {
            IIngredient ingre = ingredients[i];
            if (ingre.isEmpty()) {
                size--;
            } else if (ingre.isStatic()) {
                statics.mergeInt(ingre, 1, Integer::sum);
            } else {
                complex.mergeInt(ingre, 1, Integer::sum);
            }
        }
        this.size = size;
        ingredientsStatic = statics.keySet().toArray(new IIngredient[0]); //actually we could also do duplicate detection for static ingredients, allows us to skip some
        ingredientsStaticCount = statics.values().toIntArray(); //actually we could also do duplicate detection for static ingredients, allows us to skip some
        ingredientsCompelex = complex.keySet().toArray(new IIngredient[0]);
        ingredientsCompelexCount = complex.values().toIntArray(); //yolo, obviously there is no contract that ensures that both will have the same order, but i think that bug would be quickly reported because of wildly wrong crafting recipes
        if (ingredientsStatic.length == size) {
            ingredientsAll = ingredientsStatic;
        } else if (ingredientsStatic.length == 0) {
            ingredientsAll = ingredientsCompelex;
        } else {
            ingredientsAll = ArrayUtils.addAll(ingredientsStatic, ingredientsCompelex);
        }
    }

    @Override
    public boolean isShaped() {
        return false;
    }

    @Override
    public boolean check(IIngredientSupplier supplier, ICheckState checkState) {
        //omg i made the most efficient matcher ever. do you see all those allocations, lambdas and whatsoever? i hate myself
        if (supplier.getSuppliedAmount() != size)return false;
        checkState.requireMaxBackup(1);
        checkState.backupState();
        int[] mapping = new int[size];

        //matching static to simplify problem
        IntSortedSet unused = new IntFixedRangeSet(size, true); //i hate myself   //size == supplier.getSuppliedAmount()
        for (int i = 0; i < ingredientsStatic.length; i++) {
            IntIterator iter= unused.iterator(); //we keep the iterator around to skip already failed slots for this matcher


            boolean found = 2==checkSlots(supplier, checkState, this.ingredientsStatic[i], i, ingredientsStaticCount[i], mapping, iter, null, disclaimSlot -> false, reclaimSlot -> false, claimSlot -> {});

            if (!found) {
                checkState.revertState();
                return false;
            }
        }

        if (unused.size() == 0) {
            if (ingredientsCompelex.length > 0) throw new IllegalStateException("How can we match all static ingre, when there are still complex left?!");
            checkState.getTag(this, MATCHED_TRANSFORMATION).set(mapping);
            checkState.confirmState();
            return true;
        }


        IntSortedSet[] claims = new IntSortedSet[ingredientsCompelex.length];
        IntSortedSet matchersTBD = new IntFixedRangeSet(ingredientsCompelex.length, true);

        //try minimum check strategy (will work when there are no conflicts

        int failFlag = -1;
        for (int i = 0; i < ingredientsCompelex.length; i++) {

            //this is succeed-fast
            if (!normalCheck(supplier, checkState, mapping, unused, claims, matchersTBD, i)) {
                failFlag = i;
                break;
            }
        }

        if (failFlag != -1) { //okay we failed with ingredientsCompelex[failFlag]
            //this is fail-fast
            if (!backtrack(supplier, checkState, mapping, unused, claims, matchersTBD, failFlag, 0)) {
                return false;
            }

        }

        checkState.getTag(this, MATCHED_TRANSFORMATION).set(mapping);
        checkState.confirmState();
        return true;
    }

    private boolean backtrack(IIngredientSupplier supplier, ICheckState checkState, int[] mapping, IntSortedSet unused, IntSortedSet[] claims, IntSortedSet matchersTBD, int ingreId, int min) {
        System.out.printf("%s called backtrack with min=%d\n", Thread.currentThread().getName(), min);
        IIngredient failIngredient = ingredientsCompelex[ingreId];
        int timesNeeded = ingredientsCompelexCount[ingreId];



        IntSortedSet claim = claims[ingreId];
        IntPredicate disclaimer = getDisclaimer(claim, unused, mapping);
        IntPredicate reclaimer = getReclaimer(claim, unused, mapping, ingredientsStatic.length + ingreId);

        //check for all that were claimed by previous ingredients, this might allow us to complete this ingredient
        for (int i = 0; i < ingreId && !failIngredient.confirmState(supplier, claim, claim.size(), timesNeeded, checkState, disclaimer); i++) {
            IntSortedSet claimOther = claims[i];
            IntIterator iter= claimOther.iterator(min);

            boolean found = 0<checkSlots(supplier, checkState, failIngredient, ingredientsStatic.length + ingreId, timesNeeded, mapping, iter, claim, disclaimer, reclaimer, claim::add);


            //as that matcher is incomplete now, we need to recheck it
            if (found) {
                matchersTBD.add(i);
            }
        }

        if (failIngredient.confirmState(supplier, claim, claim.size() , timesNeeded, checkState, disclaimer)) {
            matchersTBD.remove(ingreId);
        } else {
            //this matcher could not be satisfied. we tried everything
            checkState.revertState();
            return false;
        }

        //we backtracked, so there are remaining matchers, lets do them now
        int failFlag = -1;
        IntIterator ingreIter= matchersTBD.iterator(min);
        while (ingreIter.hasNext()) {
            int i = ingreIter.nextInt();
            if (!normalCheck(supplier, checkState, mapping, unused, claims, matchersTBD, i)) {
                failFlag = i;
                break;
            }
        }

        if (failFlag != -1) { //okay we failed with ingredientsCompelex[failFlag]
            //this is fail-fast
            if (!backtrack(supplier, checkState, mapping, unused, claims, matchersTBD, failFlag, min+1)) {
                return false;
            }
        }

        return true;
    }

    private boolean normalCheck(IIngredientSupplier supplier, ICheckState checkState, int[] mapping, IntSortedSet unused, IntSortedSet[] claims, IntSortedSet matchersTBD, int i) {
        IntIterator iter= unused.iterator();
        IntSortedSet claim = claims[i];
        if (claim == null) {
            claim = new IntFixedRangeSet(size, false);
            claims[i] = claim;
        }

        IntPredicate disclaimer = getDisclaimer(claim, unused, mapping);
        IntPredicate reclaimer = getReclaimer(claim, unused, mapping, ingredientsStatic.length + i);
        boolean found = 2==checkSlots(supplier, checkState, this.ingredientsCompelex[i], ingredientsStatic.length + i, ingredientsCompelexCount[i], mapping, iter, claim, disclaimer, reclaimer, claim::add);
        //either recipe is not matched or we have a conflict. to make sure we need to use more complicated algorithm
        if (found) {
            matchersTBD.remove(i);
        }
        return found;
    }

    private int checkSlots(IIngredientSupplier supplier, ICheckState checkState, IIngredient ingredient, int ingreId, int timesNeeded, int[] mapping, IntIterator iter, IntSortedSet claim, IntPredicate disclaimer, @NotNull IntPredicate reclaimer, IntConsumer claimer) {
        boolean found = false;
        boolean foundAll = false;
        int current = claim==null?0:claim.size();
        while ((current < timesNeeded || !(foundAll = ingredient.confirmState(supplier, claim, current, timesNeeded, checkState, disclaimer))) && iter.hasNext()) {
            int slot = iter.nextInt();
            if (ingredient.check(supplier.getSuppliedShapeless(slot), checkState, slot, size, disclaimer, reclaimer)) {
                iter.remove();
                found = true;
                mapping[slot] = ingreId;
                claimer.accept(slot);
                current++;
            }
        }
        return foundAll?2:found?1:0;
    }

    @NotNull
    private IntPredicate getReclaimer(IntSortedSet claim, IntSortedSet unused, int[] mapping, int ingreId) {
        return reclaimSlot -> {
            Validate.isTrue(unused.contains(reclaimSlot), "Shapeless matcher failed, ingredient tried reclaiming other ingredient's claim! - this means the shapeless matcher algorithm failed not the ingredient, as there are legitimate reasons for doing so, like delegating to different ingredient");
            unused.remove(reclaimSlot);
            claim.add(reclaimSlot);
            mapping[reclaimSlot] = ingreId;
            return true;
        };
    }

    @NotNull
    private IntPredicate getDisclaimer(IntSortedSet claim, IntSortedSet unused, int[] mapping) {
        return disclaimSlot -> {
            Validate.isTrue(claim.contains(disclaimSlot), "Shapeless matcher failed, ingredient tried disclaiming other ingredient's claim! - this means the shapeless matcher algorithm failed not the ingredient, as there are legitimate reasons for doing so, like delegating to different ingredient");
            claim.remove(disclaimSlot);
            unused.add(disclaimSlot);
            mapping[disclaimSlot] = 0;
            return true;
        };
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
            if (!ingredientsAll[ingre].check(accessor, checkState, i, size, disclaimSlot -> false, reclaimSlot -> false)) return 0;
            int newAmount = ingredientsAll[ingre].consume(accessor, i, stackConsumer, amount, checkState, simulate);
            amount = Math.min(amount, newAmount);
        }


        return amount;
    }

}
