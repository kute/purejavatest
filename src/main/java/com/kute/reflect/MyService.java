package com.kute.reflect;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * created by kute on 2018/08/10 16:37
 */
@ServiceClass
public class MyService implements IMyService, Serializable {

    @Kute
    @Kute(name = "doAttach")
    @Override
    public String doAttach(
            @Kute String a,
            @Kute Integer b,
            @Kute @Kute(name = "innerC") Long c,
            Boolean d) {
        return null;
    }

    public static void main(String[] args) {

        Class<MyService> clazz = MyService.class;

        Method[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if ("doAttach".equalsIgnoreCase(method.getName())) {

                Class<?>[] paramTypes = method.getParameterTypes();

                System.out.println(method.getDeclaringClass());

                // 方法参数类型 数组
                System.out.println(Arrays.toString(paramTypes));

                // 方法参数个数
                System.out.println(method.getParameterCount());

                // 方法参数 注解 二维数组
                Annotation[][] paramAnnotationArray = method.getParameterAnnotations();
                printParamAnnotationArray(paramAnnotationArray);
            }
        }
    }

    private static void printParamAnnotationArray(Annotation[][] paramAnnotationArray) {
        for (int i = 0; i < paramAnnotationArray.length; i++) {
            System.out.println(i + ":" + Arrays.toString(paramAnnotationArray[i]));
        }
    }


}
