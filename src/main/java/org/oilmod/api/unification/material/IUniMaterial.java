package org.oilmod.api.unification.material;

import org.oilmod.api.OilMod;

import java.util.Set;
import java.util.stream.Stream;

public interface IUniMaterial {
    default boolean isSpecialisation(UniMaterialWrapper mat, boolean directOnly) {
        return isSpecialisation(mat.getWrapped(), directOnly);
    }
    boolean isSpecialisation(UniMaterial mat, boolean directOnly);
    default Stream<? extends IUniMaterial> getSpecialisations() {
        return getSpecialisations(false);
    }
    default Stream<? extends IUniMaterial> getSpecialisations(boolean directOnly) {
        return getSpecialisations(directOnly, false);
    }
    Stream<? extends IUniMaterial> getSpecialisations(boolean directOnly, boolean includeSelf);


    default boolean isGeneralisation(UniMaterialWrapper mat, boolean directOnly) {
        return isGeneralisation(mat.getWrapped(), directOnly);
    }
    boolean isGeneralisation(UniMaterial mat, boolean directOnly);
    
    default Stream<? extends IUniMaterial> getGeneralisations() {
        return getGeneralisations(false);
    }
    default Stream<? extends IUniMaterial> getGeneralisations(boolean directOnly) {
        return getGeneralisations(directOnly, false);
    }
    Stream<? extends IUniMaterial> getGeneralisations(boolean directOnly, boolean includeSelf);


    default boolean isVariantSupplier(UniMaterialWrapper mat, boolean directOnly) {
        return isVariantSupplier(mat.getWrapped(), directOnly);
    }
    boolean isVariantSupplier(UniMaterial mat, boolean directOnly);
    default Stream<? extends IUniMaterial> getVariantSuppliers() {
        return getVariantSuppliers(false);
    }
    Stream<? extends IUniMaterial> getVariantSuppliers(boolean directOnly);
    default Stream<? extends IUniMaterial> getVariants() {
        return getVariants(false);
    }
    Stream<? extends IUniMaterial> getVariants(boolean directOnly);

    Set<OilMod> getRequesters();
    Set<String> getIdentifiers();
    default String getMainIdentifier() {//todo this must be a) deterministic and b) consistent -> probably save as config setting after determination
        return getIdentifiers().iterator().next(); //this is not
    }
    UniMaterialWrapper getWrapper();

    MaterialType getType();


}
