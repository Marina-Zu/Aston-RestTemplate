package org.example.context;

import java.util.HashMap;
import java.util.Map;

public class AppContext {

    private AppContext() {
    }

    private static final Map<Class<?>, Object> CONTEXT = new HashMap<>();

    public static <T> T getBean(Class<T> type) {
        return (T) CONTEXT.get(type);
    }

    public static  <T> void putBean(Class<T> type, T bean) {
        CONTEXT.put(type, bean);
    }

}
