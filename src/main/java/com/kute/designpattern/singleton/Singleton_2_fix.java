package com.kute.designpattern.singleton;

import java.io.Serializable;

/**
 * created on 2018-06-29 15:30
 * 懒汉 线程安全
 * 解决 有序列化反序列化 问题
 */
public class Singleton_2_fix implements Serializable {

    private Singleton_2_fix() {
    }

    private volatile static Singleton_2_fix instance;

    public static Singleton_2_fix getInstance() {
        if(null == instance) {
            synchronized (Singleton_2_fix.class) {
                if(null == instance) {
                    instance = new Singleton_2_fix();
                }
            }
        }
        return instance;
    }

    /**
     * 定义  readResolve  语义
     * @return
     */
    private Object readResolve() {
        return instance;
    }
}
