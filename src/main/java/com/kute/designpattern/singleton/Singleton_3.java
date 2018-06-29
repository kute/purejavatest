package com.kute.designpattern.singleton;

/**
 * created on 2018-06-29 15:33
 *
 * 饿汉 变种
 * 类如果多次加载则会多次执行初始化
 */
public class Singleton_3 {

    private Singleton_3() {
    }

    private static Singleton_3 instance;
    
    static {
        instance = new Singleton_3();
    }

    public static Singleton_3 getInstance() {
        return instance;
    }
    
}
