package com.kute.designpattern.singleton;

/**
 * 枚举 实现单例
 *
 * 线程安全 且无 序列化与反序列化问题
 */
public enum Singleton_5 {

    INSTANCE;

    Singleton_5(){}

    public void whateverMethod() {
    }

}
