package org.oilmod.api.unification.material;

import org.oilmod.api.OilMod;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.StreamSupport.stream;

public class UniMaterialWrapper implements IUniMaterial {
    private UniMaterial wrapped;

    protected UniMaterialWrapper(UniMaterial wrapped) {
        this.wrapped = wrapped;
    }

    /**
     * The wrapped can be exchanged at any point, so only referring to the wrapper is save. This can be due to material unification or implementation specific things.
     * @return
     */
    public UniMaterial getWrapped() {
        return wrapped;
    }

    protected void setWrapped(UniMaterial wrapped) {
        this.wrapped = wrapped;
    }

    public boolean isSpecialisation(UniMaterial mat, boolean directOnly) {
        return mat.isSpecialisation(mat, directOnly);
    }

    public boolean isGeneralisation(UniMaterial mat, boolean directOnly) {
        return mat.isGeneralisation(mat, directOnly);
    }

    @Override
    public boolean isVariantSupplier(UniMaterial mat, boolean directOnly) {
        return mat.isVariantSupplier(mat, directOnly);
    }

    /*public Stream<UniMaterialWrapper<?>> getSpecialisations(boolean directOnly) {
        return () ->  stream(wrapped.getSpecialisations(directOnly).spliterator(), false).map(o -> o.getWrapper()).iterator();
    }*/

    public Stream<? extends UniMaterial> getSpecialisations(boolean directOnly, boolean includeSelf) {
        return wrapped.getGeneralisations(directOnly, includeSelf);
    }

    public Stream<? extends UniMaterial> getGeneralisations(boolean directOnly, boolean includeSelf) {
        return wrapped.getGeneralisations(directOnly, includeSelf);
    }

    @Override
    public Stream<? extends IUniMaterial> getVariantSuppliers(boolean directOnly) {
        return wrapped.getVariantSuppliers(directOnly);
    }

    @Override
    public Stream<? extends IUniMaterial> getVariants(boolean directOnly) {
        return wrapped.getVariants(directOnly);
    }

    @Override
    public Set<OilMod> getRequesters() {
        return wrapped.getRequesters();
    }

    @Override
    public Set<String> getIdentifiers() {
        return wrapped.getIdentifiers();
    }

    @Override
    public UniMaterialWrapper getWrapper() {
        return this;
    }

    @Override
    public MaterialType getType() {
        return wrapped.getType();
    }

    @Override
    public boolean equals(Object obj) {
        return wrapped.equals(obj);
    }

    @Override
    public int hashCode() {
        return wrapped.hashCode();
    }
}
