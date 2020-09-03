package org.oilmod.api.util;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;
import static java.util.stream.StreamSupport.stream;

public class Util {
    public static final Pattern alphanumericalPattern = Pattern.compile("^[a-z][a-z0-9_]*$");

    public static <T> boolean containsRecursive(T toFind, Collection<T> col, Function<T, Collection<T>> getInner) {
        if (col.contains(toFind)) return true;
        for (T inner:col) {
            if (containsRecursive(toFind, getInner.apply(inner), getInner)) return true;
        }
        return false;
    }

    public static <T> Stream<T> resolveRecursive(Stream<T> stream, Function<T, Stream<T>> f) {
        return stream.flatMap(uniMaterial -> concat(of(uniMaterial), resolveRecursive(f.apply(uniMaterial), f)));
    }

    public static void checkName(String str) {
        Validate.isTrue(alphanumericalPattern.matcher(str).find(), "Only lowercase alphanumerical characters and underscores are allowed, but got '" + str + "'");
    }

    public static void checkName(String... strs) {
        for (String str:strs) {
            checkName(str);
        }
    }

    public static <T1, T2 extends T1> boolean hasCommon(Iterable<? extends T1> set1, Iterable<? extends T2> set2) {
        return getFirstCommon(set1, set2) != null;
    }

    public static <T1, T2 extends T1> T1 getFirstCommon(Iterable<? extends T1> set1, Iterable<? extends T2> set2) {
        Set<T1> helperSet = new ObjectOpenHashSet<>();
        set1.forEach(helperSet::add);
        for (T2 other:set2) {
            if (helperSet.contains(other))return other;
        }
        return null;
    }

}
