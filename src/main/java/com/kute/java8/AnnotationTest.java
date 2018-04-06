package com.kute.java8;

import com.kute.java8.annotation.Mark;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * created by kute on 2018/04/06 10:12
 */


//@Marks(value = {@Mark("a"),@Mark("b")})

@Mark("b")
@Mark("a")
public class AnnotationTest {

    @Target(ElementType.TYPE_USE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TypeUseAnnotation {

    }

    public static @TypeUseAnnotation class TypeUseClass<@TypeUseAnnotation T> extends @TypeUseAnnotation Object {
        public void foo(@TypeUseAnnotation T t) throws @TypeUseAnnotation Exception {

        }
    }

    public static void main(String[] args) {
        //重复注解
        for (Mark mark : AnnotationTest.class.getAnnotationsByType(Mark.class)) {
            System.out.println(mark.value());
        }
    }

}
