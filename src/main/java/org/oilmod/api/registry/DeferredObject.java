package org.oilmod.api.registry;

import org.apache.commons.lang3.Validate;
import org.oilmod.api.util.OilKey;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.oilmod.util.LamdbaCastUtils.cast;

public class DeferredObject<Type> implements Supplier<Type> {
    private final OilKey key;
    private final RegistryBase<? super Type, ?, ?, ?> registry;
    private Type value;

    protected DeferredObject(OilKey key, RegistryBase<? super Type, ?, ?, ?> registry) {
        this.key = key;
        this.registry = registry;
    }

    public Type get() {
        if (this == EMPTY)return null;
        if (value == null) {
            if (registry.isDeferred()) return null;
            value = cast(registry.getRegistryHelper().get(key)); //get from the global one!
        }
        return value;
    }


    //<editor-fold desc="EMPTY instance" defaultstate="collapsed">
    private DeferredObject() {
        Validate.isTrue(EMPTY == null, "This ctor is only there to create the static empty instance");
        key = null;
        registry = null;
    }

    private static DeferredObject<?> EMPTY = new DeferredObject<>();

    public static <T> DeferredObject<T> empty() {
        @SuppressWarnings("unchecked")
        DeferredObject<T> t = (DeferredObject<T>) EMPTY;
        return t;
    }
    //</editor-fold>

    //<editor-fold desc="Functional Interface Helpers" defaultstate="collapsed">

    public Stream<Type> stream() {
        return isPresent() ? Stream.of(get()) : Stream.of();
    }

    /**
     * Return {@code true} if there is a mod object present, otherwise {@code false}.
     *
     * @return {@code true} if there is a mod object present, otherwise {@code false}
     */
    public boolean isPresent() {
        return get() != null;
    }

    /**
     * If a mod object is present, invoke the specified consumer with the object,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a mod object is present
     * @throws NullPointerException if mod object is present and {@code consumer} is
     * null
     */
    public void ifPresent(Consumer<? super Type> consumer) {
        if (get() != null)
            consumer.accept(get());
    }

    /**
     * If a mod object is present, and the mod object matches the given predicate,
     * return an {@code RegistryObject} describing the value, otherwise return an
     * empty {@code RegistryObject}.
     *
     * @param predicate a predicate to apply to the mod object, if present
     * @return an {@code RegistryObject} describing the value of this {@code RegistryObject}
     * if a mod object is present and the mod object matches the given predicate,
     * otherwise an empty {@code RegistryObject}
     * @throws NullPointerException if the predicate is null
     */
    public DeferredObject<Type> filter(Predicate<? super Type> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent())
            return this;
        else
            return predicate.test(get()) ? this : empty();
    }

    /**
     * If a mod object is present, apply the provided mapping function to it,
     * and if the result is non-null, return an {@code Optional} describing the
     * result.  Otherwise return an empty {@code Optional}.
     *
     * @apiNote This method supports post-processing on optional values, without
     * the need to explicitly check for a return status.
     *
     * @param <U> The type of the result of the mapping function
     * @param mapper a mapping function to apply to the mod object, if present
     * @return an {@code Optional} describing the result of applying a mapping
     * function to the mod object of this {@code RegistryObject}, if a mod object is present,
     * otherwise an empty {@code Optional}
     * @throws NullPointerException if the mapping function is null
     */
    public<U> Optional<U> map(Function<? super Type, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return Optional.empty();
        else {
            return Optional.ofNullable(mapper.apply(get()));
        }
    }

    /**
     * If a value is present, apply the provided {@code Optional}-bearing
     * mapping function to it, return that result, otherwise return an empty
     * {@code Optional}.  This method is similar to {@link #map(Function)},
     * but the provided mapper is one whose result is already an {@code Optional},
     * and if invoked, {@code flatMap} does not wrap it with an additional
     * {@code Optional}.
     *
     * @param <U> The type parameter to the {@code Optional} returned by
     * @param mapper a mapping function to apply to the mod object, if present
     *           the mapping function
     * @return the result of applying an {@code Optional}-bearing mapping
     * function to the value of this {@code Optional}, if a value is present,
     * otherwise an empty {@code Optional}
     * @throws NullPointerException if the mapping function is null or returns
     * a null result
     */
    public<U> Optional<U> flatMap(Function<? super Type, Optional<U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return Optional.empty();
        else {
            return Objects.requireNonNull(mapper.apply(get()));
        }
    }

    /**
     * If a mod object is present, lazily apply the provided mapping function to it,
     * returning a supplier for the transformed result. If this object is empty, or the
     * mapping function returns {@code null}, the supplier will return {@code null}.
     *
     * @apiNote This method supports post-processing on optional values, without
     * the need to explicitly check for a return status.
     *
     * @param <U> The type of the result of the mapping function
     * @param mapper A mapping function to apply to the mod object, if present
     * @return A {@code Supplier} lazily providing the result of applying a mapping
     * function to the mod object of this {@code RegistryObject}, if a mod object is present,
     * otherwise a supplier returning {@code null}
     * @throws NullPointerException if the mapping function is {@code null}
     */
    public<U> Supplier<U> lazyMap(Function<? super Type, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        return () -> isPresent() ? mapper.apply(get()) : null;
    }

    /**
     * Return the mod object if present, otherwise return {@code other}.
     *
     * @param other the mod object to be returned if there is no mod object present, may
     * be null
     * @return the mod object, if present, otherwise {@code other}
     */
    public Type orElse(Type other) {
        return isPresent() ? get() : other;
    }

    /**
     * Return the mod object if present, otherwise invoke {@code other} and return
     * the result of that invocation.
     *
     * @param other a {@code Supplier} whose result is returned if no mod object
     * is present
     * @return the mod object if present otherwise the result of {@code other.get()}
     * @throws NullPointerException if mod object is not present and {@code other} is
     * null
     */
    public Type orElseGet(Supplier<? extends Type> other) {
        return isPresent() ? get() : other.get();
    }

    /**
     * Return the contained mod object, if present, otherwise throw an exception
     * to be created by the provided supplier.
     *
     * @apiNote A method reference to the exception constructor with an empty
     * argument list can be used as the supplier. For example,
     * {@code IllegalStateException::new}
     *
     * @param <X> Type of the exception to be thrown
     * @param exceptionSupplier The supplier which will return the exception to
     * be thrown
     * @return the present mod object
     * @throws X if there is no mod object present
     * @throws NullPointerException if no mod object is present and
     * {@code exceptionSupplier} is null
     */
    public <X extends Throwable> Type orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (get() != null) {
            return get();
        } else {
            throw exceptionSupplier.get();
        }
    }
    //</editor-fold>
}
