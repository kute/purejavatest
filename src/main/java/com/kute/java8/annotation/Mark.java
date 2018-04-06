package com.kute.java8.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
// 重复注解（能存放该重复注解数组的注解类.class）
@Repeatable(Marks.class)
@Documented
public @interface Mark {

    String value() default "";

}
