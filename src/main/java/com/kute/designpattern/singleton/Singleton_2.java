package com.kute.designpattern.singleton;

/**
 * created on 2018-06-29 15:30
 * 懒汉 线程安全
 * 但是 有序列化反序列化 问题(见Singleton_2_fix）
 */
public class Singleton_2 {

    private Singleton_2() {
    }

    private volatile static Singleton_2 instance;

    public static Singleton_2 getInstance() {
        if(null == instance) {
            synchronized (Singleton_2.class) {
                if(null == instance) {
                    instance = new Singleton_2();
                }
            }
        }
        return instance;
    }
}
