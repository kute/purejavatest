package com.kute.mybatis.handler;

/**
 * 若跟数据库交互的java bean中使用了枚举类型，定义的枚举需实现此接口
 */
public interface BaseEnum {

    int getValue();

    default String getKey() {
        return null;
    }

}