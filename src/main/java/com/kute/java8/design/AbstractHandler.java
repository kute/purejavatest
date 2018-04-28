package com.kute.java8.design;

/**
 * created on 2018-04-18 20:22
 */
public abstract class AbstractHandler implements Handler {

    private Object o;

    protected AbstractHandler(Object o) {
        this.o = o;
    }

    public static AbstractHandler create(Object o, final Handler handler) {
        return new AbstractHandler(o) {
            @Override
            public Object handle(Object o) throws Exception {
                return handler.handle(o);
            }
        };
    }

}
