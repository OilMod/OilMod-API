package org.oilmod.api.unification.material;

import org.oilmod.api.OilMod;

import java.util.Set;

public interface IUniMaterial {
    default boolean isSpecialisation(UniMaterialWrapper mat, boolean directOnly) {
        return isSpecialisation(mat.getWrapped(), directOnly);
    }
    boolean isSpecialisation(UniMaterial mat, boolean directOnly);
    default Iterable<? extends IUniMaterial> getSpecialisations() {
        return getSpecialisations(false);
    }
    default Iterable<? extends IUniMaterial> getSpecialisations(boolean directOnly) {
        return getSpecialisations(directOnly, false);
    }
    Iterable<? extends IUniMaterial> getSpecialisations(boolean directOnly, boolean includeSelf);


    default boolean isGeneralisation(UniMaterialWrapper mat, boolean directOnly) {
        return isGeneralisation(mat.getWrapped(), directOnly);
    }
    boolean isGeneralisation(UniMaterial mat, boolean directOnly);
    
    default Iterable<? extends IUniMaterial> getGeneralisations() {
        return getGeneralisations(false);
    }
    default Iterable<? extends IUniMaterial> getGeneralisations(boolean directOnly) {
        return getGeneralisations(directOnly, false);
    }
    Iterable<? extends IUniMaterial> getGeneralisations(boolean directOnly, boolean includeSelf);


    default boolean isVariantSupplier(UniMaterialWrapper mat, boolean directOnly) {
        return isVariantSupplier(mat.getWrapped(), directOnly);
    }
    boolean isVariantSupplier(UniMaterial mat, boolean directOnly);

    default Iterable<? extends IUniMaterial> getVariantSuppliers() {
        return getVariantSuppliers(false);
    }
    Iterable<? extends IUniMaterial> getVariantSuppliers(boolean directOnly);
    
    Set<OilMod> getRequesters();
    Set<String> getIdentifiers();
    UniMaterialWrapper getWrapper();


}
