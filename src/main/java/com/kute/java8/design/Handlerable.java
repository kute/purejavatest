package com.kute.java8.design;

/**
 * created on 2018-04-18 20:28
 */
public abstract class Handlerable {

    protected abstract Object addHandler(Object o, AbstractHandler abstractHandler)  throws Exception;

    public Object execute(Object o, Handler handler)  throws Exception {
        return addHandler(o, AbstractHandler.create(o, handler));
    }
}
