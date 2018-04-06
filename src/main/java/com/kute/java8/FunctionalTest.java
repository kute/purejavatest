package com.kute.java8;

import com.kute.java8.service.CarFactory;
import com.kute.java8.service.Carable;
import com.kute.java8.service.impl.CarImpl;

/**
 * created by kute on 2018/04/06 09:36
 */
public class FunctionalTest {

    public static void main(String[] args) {

        // Suplier<T>
        Carable carable = CarFactory.create(CarImpl::new);
        carable.use();

        // ToDoubleFunction
        System.out.println(CarFactory.toDouble(Double::parseDouble, "2.22"));
    }

}
