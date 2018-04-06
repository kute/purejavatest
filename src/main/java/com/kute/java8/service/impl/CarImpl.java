package com.kute.java8.service.impl;

import com.kute.java8.service.Carable;

/**
 * created by kute on 2018/04/06 09:42
 */
public class CarImpl implements Carable {

    @Override
    public String use() {
        System.out.println("CarImpl impl use");
        return null;
    }
}
