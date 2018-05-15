package com.nearit.ui_bindings.utils;

import java.util.ArrayList;
import java.util.List;

public class CollectionsUtils {

    public interface Predicate<T> { boolean apply(T item); }

    public static <T> List<T> filter(List<T> col, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T element: col) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }
}
