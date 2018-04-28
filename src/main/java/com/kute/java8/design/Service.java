package com.kute.java8.design;

/**
 * created on 2018-04-18 20:29
 */
public final class Service extends Handlerable {

    private static final Service INSTANCE = new Service();

    public static Service getInstance() {
        return INSTANCE;
    }

    @Override
    protected Object addHandler(Object o, AbstractHandler abstractHandler) throws Exception {
        return abstractHandler.handle(o);
    }
}
