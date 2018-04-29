package com.kute.java8.design;

@FunctionalInterface
public interface Handler {

    Object handle(Object o) throws Exception;

}
