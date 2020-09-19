package org.oilmod.api.unification.material;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.oilmod.api.OilMod;
import org.oilmod.api.registry.DeferredObject;
import org.oilmod.api.registry.RegistryBase;
import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.registry.SimpleRegistryMPIBase;
import org.oilmod.api.unification.UniRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.oilmod.util.LamdbaCastUtils.cast;

public class UniMaterialRegistry extends RegistryBase<MaterialRequestBuilder<?>, UniMaterialRegistry, UniMaterialRegistry.MPI, UniMaterialRegistry.Helper<?>> {

    /**
     * Creates new instance of Registry
     *
     * @param mod            associated mod with this item registry
     * @param registryHelper
     */
    protected UniMaterialRegistry(OilMod mod, Helper<?> registryHelper) {
        super(mod, registryHelper, "uni_material");
    }


    public <T extends UniMaterial> void register(String id, Function<MaterialRequestBuilder<?>, MaterialRequestBuilder<T>> buiderPopulator, Consumer<T> future) {
        register(id, () -> buiderPopulator.apply(new MaterialRequestBuilder<>()).future(future));
    }

    public <T extends UniMaterial> void register(String id, Function<MaterialRequestBuilder<?>, MaterialRequestBuilder<T>> buiderPopulator) {
        register(id, () -> buiderPopulator.apply(new MaterialRequestBuilder<>()));
    }

    /**
     * Internal - should not be called by user code
     */
    public static abstract class Helper<Impl extends Helper<Impl>> extends RegistryHelperBase<MaterialRequestBuilder<?>, UniMaterialRegistry, MPI, Helper<?>, Impl> {
        private final Map<String, UniMaterial> materials = new Object2ObjectOpenHashMap<>();
        private static Helper instance;

        @Override
        public void onReady() {
            super.onReady();
            instance = this;
        }

        public static <Impl extends Helper<Impl>> Impl getInstance() {
            return (Impl)(Object)instance;
        }

        @Override
        public UniMaterialRegistry create(OilMod mod) {
            return new UniMaterialRegistry(mod, this);
        }

        @Override
        protected void afterAllMods() {
            super.afterAllMods();
            Map<String, MaterialBuilder> builders = new Object2ObjectOpenHashMap<>();

            for(MaterialRequestBuilder<?> request:getRegistered()) {
                MaterialBuilder current = null;

                //create the builder if there is none yet or find one from previous request. if there are multiple ids that all exist then fuse them
                for(String id: request.getIdentifiers()) {
                    MaterialBuilder old = builders.get(id);
                   if (old != null) { //
                       if (current == null) {
                           current = old;
                       } else {
                           //merge the two
                           current.mergeIn(old);
                       }
                   } else {
                       if (current == null) {
                           current = new MaterialBuilder(request.getRequester());
                       }
                   }
                   builders.put(id, current);
                }
                if (current == null)continue;
                if (current.getType() != MaterialType.Composite) {
                    current.setType(request.getType());
                }
                current.identifiers.addAll(request.getIdentifiers());
                current.requesters.add(request.getRequester());
                //System.out.printf("Request %s with", current.identifiers.iterator().next());
                request.getSpecialisations().stream()/*.peek(id -> System.out.printf(" specialisation: %s", id))*/.map(id -> builders.computeIfAbsent(id, id2 -> new MaterialBuilder(request.getRequester()).identifier(id2))).forEach(current::specialisation);
                request.getGeneralisations().stream()/*.peek(id -> System.out.printf(" generalisation: %s", id))*/.map(id -> builders.computeIfAbsent(id, id2 -> new MaterialBuilder(request.getRequester()).identifier(id2))).forEach(current::generalisation);
                request.getConstituents().stream()/*.peek(id -> System.out.printf(" constituents: %s", id))*/.map(id -> builders.computeIfAbsent(id, id2 -> new MaterialBuilder(request.getRequester()).identifier(id2))).forEach(current::constituent);
                for (Consumer<? extends UniMaterial> f:request.getFutures()) {
                    current.lazyFuture(cast(f));
                }
                //System.out.println("");
            }
            Set<MaterialBuilder> builderSet = new ObjectOpenHashSet<>(builders.values());


            //this builds all the materials. !!!at this stage they are not done, during the build process they are linked to the real instances by futures
            for (MaterialBuilder builder:builderSet) {
                UniMaterial result =builder.build();
                for (String id:result.getIdentifiers()) {
                    materials.put(id, result);
                }
            }

            //now they are done!
            for (MaterialBuilder builder:builderSet) {
                builder.getBuild().freeze();
                builder.callLazyFutures();
            }
        }
    }

    public static class DefaultHelper extends Helper<DefaultHelper>{}
    public static final class MPI extends SimpleRegistryMPIBase<MaterialRequestBuilder<?>, UniMaterialRegistry, MPI, Helper<?>> {
        @Override
        public Helper<?> createDefaultProvider() {
            return new DefaultHelper();
        }
    }
}