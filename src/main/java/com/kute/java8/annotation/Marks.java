package com.kute.java8.annotation;

import java.lang.annotation.*;

/**
 * created by kute on 2018/04/06 10:03
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Marks {
    Mark[] value() default {};
}
