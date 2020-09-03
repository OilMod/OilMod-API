package org.oilmod.api.unification.material;

import org.oilmod.api.OilMod;

import java.util.HashMap;

public class MaterialHelper {

    public static UniMaterial[] unwrap(IUniMaterial[] mats) {
        UniMaterial[] mats2 = new UniMaterial[mats.length];
        HelperImpl helper = HelperImpl.getInstance();
        for (int i = 0; i < mats.length; i++) {
            mats2[i] = helper.unwrap(mats[i]);
        }
        return mats2;
    }

    public static class HelperImpl{
        private static HelperImpl instance;
        private static final Object MUTEX = new Object();
        private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

        public static void setInstance(HelperImpl instance) {
            if (HelperImpl.instance == null) {
                synchronized (MUTEX) {
                    if (HelperImpl.instance == null) {
                        HelperImpl.instance = instance;
                    } else {
                        throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                    }
                }
            } else {
                throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
            }
        }

        public static HelperImpl getInstance() {
            if (HelperImpl.instance == null) {
                synchronized (MUTEX) {
                    if (HelperImpl.instance == null) {
                        HelperImpl.instance = new HelperImpl();
                    }
                }
            }
            return instance;
        }
        protected HashMap<String, UniMaterial> map;



        protected void addRequester(UniMaterial material, OilMod mod) {
            material.addRequester(mod);
        }
        protected void addIdentifiers(UniMaterial material, String... identifiers) {
            material.addIdentifiers(identifiers);
        }
        protected void addSpecialisations(UniMaterial material, UniMaterial... specialisations) {
            for (UniMaterial mat2:specialisations) {
                material.addSpecialisation(mat2);
            }
        }
        protected void addGeneralisations(UniMaterial material, UniMaterial... generalisations) {
            for (UniMaterial mat2:generalisations) {
                material.addGeneralisation(mat2);
            }
        }

        protected UniMaterialWrapper createWrapper(UniMaterial material) {
            return new UniMaterialWrapper(material);
        }

        protected Category createCategory(OilMod[] requesters, String... identifiers) {
            return new Category(requesters, identifiers);
        }

        protected Composite createComposite(OilMod[] requesters, UniMaterial[] constituents, String... identifiers) {
            return new Composite(requesters, constituents, identifiers);
        }

        protected Element createElement(OilMod[] requesters, String... identifiers) {
            return new Element(requesters, identifiers);
        }

        protected OrganicComponent createOrganic(OilMod[] requesters, String... identifiers) {
            return new OrganicComponent(requesters, identifiers);
        }
        protected UniMaterial unwrap(IUniMaterial mat) {
            if (mat instanceof UniMaterial) {
                return (UniMaterial) mat;
            } else if (mat instanceof UniMaterialWrapper) {
                return ((UniMaterialWrapper) mat).getWrapped();
            }
            throw new IllegalArgumentException("Cannot use unknown custom implementation of IUniMaterial");
        }


        protected <T extends UniMaterial> T registerMaterial(T result) {
            return result;
        }
    }
}
