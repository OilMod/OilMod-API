package org.oilmod.api.crafting.custom;

import org.oilmod.api.rep.crafting.*;

public class RecipeSizeKey {
    final boolean[] shaped;
    final int[] highDim;
    final int[] lowDim;
    private int hashCode;

    private RecipeSizeKey(boolean[] shaped, int[] highDim, int[] lowDim) {
        this.shaped = shaped;
        this.highDim = highDim;
        this.lowDim = lowDim;
    }


    /**
     * ordering of categories matters!
     * @param recipe
     * @param categories
     */
    public RecipeSizeKey(IRecipeRep recipe, IIngredientCategory... categories) {
        this(new boolean[categories.length], new int[categories.length], new int[categories.length]);
        for (int i = 0; i < categories.length; i++) {
            IMatcher matcher = recipe.getMatcher(categories[i]);
            if (matcher == null) {
                shaped[i] = false;
                highDim[i] = 0;
                lowDim[i] = 0;
            } else  if (matcher.isShaped()) {
                shaped[i] = true;
                int width = matcher.getInputWidth();
                int height = matcher.getInputHeight();
                highDim[i] = Math.max(width, height);
                lowDim[i] = Math.min(width, height);
            } else {
                shaped[i] = false;
                highDim[i] = matcher.getInputSize();
                lowDim[i] = -1;
            }
        }
    }


    //IIngredientSupplier

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Mask)return o.equals(this); //mask do equality based on current state, not based on fields
        if (o == null || getClass() != o.getClass()) return false;
        RecipeSizeKey that = (RecipeSizeKey) o;

        //i am aware that equals should not through exceptions, however this allows us to discover bugs as such comparisons should never occur
        if (shaped.length != that.shaped.length)throw new IllegalStateException("Keys have different length of categories!");
        //if (isMasked && that.isMasked) throw new IllegalStateException("Equals can only be performed if only one of the RecipeSizeIdentifier has an mask applied! (was a masked one accidentally set as a key for a map?");

        for (int i = 0; i < shaped.length; i++) {
            if (shaped[i] != that.shaped[i])return false;
            if (highDim[i] != that.highDim[i])return false;
            if (lowDim[i] != that.lowDim[i])return false;
        }
        return true;
    }

    private static int getHashCodeRow(int result, boolean shaped, int high, int low) {
        result = 31 * result + (shaped ? 1231 : 1237);
        result = 31 * result + high;
        result = 31 * result + low;
        return result;
    }

    @Override
    public final int hashCode() {
        if (hashCode == 0) {
            hashCode = calcHashCode();
            if (hashCode == 0)hashCode--;
        }
        return hashCode;
    }

    int calcHashCode() {
        int result = 1;
        for (int i = 0; i < shaped.length; i++) {
            result = getHashCodeRow(result, shaped[i], highDim[i], lowDim[i]);
        }
        return result;
    }

    void resetHashCodeCache() {
        hashCode = 0;
    }



    public static class Mask extends RecipeSizeKey{

        private final byte[] mask; //three-state
        private final int[] shapeless;

        /**
         * ordering of suppliers matters!
         * @param suppliers
         */
        public Mask(IIngredientSupplier[] suppliers) {
            super(new boolean[suppliers.length], new int[suppliers.length], new int[suppliers.length]);
            mask = new byte[suppliers.length];
            shapeless = new int[suppliers.length];
            for (int i = 0; i < suppliers.length; i++) {
                IIngredientSupplier supplier = suppliers[i];
                if (supplier == null || supplier.getSuppliedAmount() == 0) {
                    mask[i] = 3; //nothing here, we can skip it already! (this is 3 to make sure it doesnt get reset!)
                    shaped[i] = false;
                    highDim[i] = 0;
                    lowDim[i] = 0;
                } else  {
                    shapeless[i] = supplier.getSuppliedAmount();
                    if (supplier.isShaped()) {
                        shaped[i] = true;
                        int width = supplier.getSuppliedWidth();
                        int height = supplier.getSuppliedHeight();
                        highDim[i] = Math.max(width, height);
                        lowDim[i] = Math.min(width, height);
                    } else {
                        mask[i] = 1; //we skip shaped testing
                        shaped[i] = false;
                        highDim[i] = shapeless[i];
                        lowDim[i] = -1;
                    }
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof Mask)throw new IllegalStateException("Equals can only be performed if only one of the RecipeSizeIdentifier has an mask applied! (was a masked one accidentally set as a key for a map?");
            if (!(o instanceof RecipeSizeKey)) return false;
            RecipeSizeKey that = (RecipeSizeKey) o;

            //i am aware that equals should not through exceptions, however this allows us to discover bugs as such comparisons should never occur
            if (shaped.length != that.shaped.length)throw new IllegalStateException("Keys have different length of categories!");
            //if (isMasked && that.isMasked)
            for (int i = 0; i < shaped.length; i++) {
                if (mask[i]>=2)continue; //todo this needs to calculate shapeless dimensions. also just taken width*height is not correct for shapeless
                boolean shaped = mask[i]==0;
                if (shaped != that.shaped[i])return false;
                if (shaped) {
                    if (highDim[i] != that.highDim[i])return false;
                    if (lowDim[i] != that.lowDim[i])return false;
                } else {
                    if (-1 != that.lowDim[i])return false;
                    if (shapeless[i] != that.highDim[i])return false;
                }


            }
            return true;
        }

        @Override
        int calcHashCode() {
            int result = 1;
            for (int i = 0; i < shaped.length; i++) {
                switch (mask[i]) {
                    case 0:
                        result = getHashCodeRow(result, true, highDim[i], lowDim[i]);
                        break;
                    case 1:
                        result = getHashCodeRow(result, false, shapeless[i], -1); //needs to be same as if shaped=false in constructor
                        break;
                    case 2:
                    case 3:
                        result = getHashCodeRow(result, false, 0, 0); //needs to be same as if matcher=null in constructor
                        break;
                    default:
                        throw new IllegalStateException("mask has to be trit!");
                }
            }
            return result;
        }


        public boolean nextMask() {
            resetHashCodeCache();
            //this is basically performing the operation ++ as mask.length trit integer (shapeless inputs are treated as bit tho)!
            boolean flag = false;
            int max = mask.length -1;
            loop: for (int i = 0; i <= max; i++) {
                switch (mask[i]) {
                    case 2: //reset and carry over
                        mask[i] = shaped[i]?(byte) 0:1;
                        if (i==max)flag=true;
                        break;
                    case 3: //no input so always skipped
                        if (i==max)flag=true;
                        break;
                    case 1:
                    default: //no carry over, we are done!
                        mask[i]++;
                        break loop;
                }
            }
            return !flag;
        }
    }
}
