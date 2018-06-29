package com.kute.designpattern.singleton;

/**
 * created on 2018-06-29 15:35
 *
 * 静态内部类 ，延迟初始化
 */
public class Singleton_4 {

    private Singleton_4() {
    }

    private static class SingletonHoler {
        private static final Singleton_4 instance = new Singleton_4();
    }

    public static final Singleton_4 getInstance() {
        return SingletonHoler.instance;
    }
}
