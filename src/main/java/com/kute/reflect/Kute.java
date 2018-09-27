package com.kute.reflect;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Repeatable(Kutes.class)
public @interface Kute {

    String name() default "";
}
