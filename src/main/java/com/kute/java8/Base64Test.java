package com.kute.java8;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * created by kute on 2018/04/06 10:37
 */
public class Base64Test {

    public static void main(String[] args) {

        String text = "中文";

        String encode = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));

        System.out.println(encode);

        String decode = new String(Base64.getDecoder().decode(encode));

        System.out.println(decode);

    }
}
