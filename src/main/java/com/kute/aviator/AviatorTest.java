package com.kute.aviator;

import com.google.common.collect.Lists;
import com.googlecode.aviator.AviatorEvaluator;

import java.util.*;

/**
 * created by bailong001 on 2018/08/19 09:52
 */
public class AviatorTest {


    public static void main(String[] args) {

        AviatorTest at = AviatorTest.getINSTANCE();

        String expression = "4 + 8 * 9 - 2 % 1";
        System.out.println(at.execute(expression, null, false));

        Map<String, Object> env = new HashMap<>();
        env.put("list", Lists.newArrayList(3, 4, 5));
        env.put("array", new String[]{"a", "c", "d"});
        env.put("var", 9);
        expression = "list[1] + ';' + array[0] + ';' + var";
        System.out.println(AviatorEvaluator.execute(expression, env));

        expression = "city > 7 && var < 10";
        env.put("city", 8);
        System.out.println(AviatorEvaluator.execute(expression, env));

        // 自定义函数
        AviatorEvaluator.addFunction(new AddFunction());
        System.out.println(AviatorEvaluator.execute("add(1,2)"));
        System.out.println(AviatorEvaluator.execute("add(add(1,2),100)"));


        final List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add(" world");

        final int[] array = new int[3];
        array[0] = 0;
        array[1] = 1;
        array[2] = 3;

        final Map<String, Date> map = new HashMap<String, Date>();
        map.put("date", new Date());

        env = new HashMap<String, Object>();
        env.put("list", list);
        env.put("array", array);
        env.put("mmap", map);

        System.out.println(AviatorEvaluator.execute(
                "list[0]+list[1]+'\narray[0]+array[1]+array[2]='+(array[0]+array[1]+array[2]) +' \ntoday is '+mmap.date ",
                env));


        AviatorEvaluator.compile("reportTypeOne == 1101");
    }


    private static final AviatorTest INSTANCE = new AviatorTest();

    public static AviatorTest getINSTANCE() {
        return INSTANCE;
    }

    public Object execute(String expression, Map<String , Object> env, boolean cached) {
        return AviatorEvaluator.execute(expression, env, cached);
    }

}
