package com.kute.service;

import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kute
 */
public class ServiceBusinessUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBusinessUtil.class);

    public static <R> R getByCatchException(AbstractServiceExecutor<R> serviceExecutor, R defaultValue) {
        R result = null;
        try {
            result = serviceExecutor.execute();
        } catch(Exception e) {
            result = defaultValue;
        }
        return result;
    }

    public static <T, R> R getByCatchException(T params, Function<T, R> function, R defaultValue) {
        R result = null;
        try {
            result = function.apply(params);
        } catch(Exception e) {
            result = defaultValue;
        }
        return result;
    }

}

abstract class AbstractServiceExecutor<R> {

    public abstract R execute() throws Exception;

}

