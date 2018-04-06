package com.kute.java8.service;

import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

/**
 * created by kute on 2018/04/06 09:44
 */
public class CarFactory<T> {

    public static Carable create(final Supplier<Carable> supplier) {
        return supplier.get();
    }

    public static <T> Double toDouble(final ToDoubleFunction<T> toDoubleFunction, final T value) {
        return toDoubleFunction.applyAsDouble(value);
    }
}
