package com.kute.designpattern.singleton;

/**
 * created on 2018-06-29 15:28
 * 饿汉
 * 类加载时初始化，别不同的类加载器加载时可能会创建多份示例
 */
public class Singleton_1 {

    private Singleton_1() {
    }

    private static Singleton_1 instance = new Singleton_1();

    public static Singleton_1 getInstance() {
        return instance;
    }
}
