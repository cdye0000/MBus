package com.cdye.mbus.two;

import java.lang.reflect.Method;

/**
 * Created by cdy on 2018/5/22.
 */

public class SubscribeMethod {
    private Method method;
    private ThreadMode threadMode;
    private Class<?> clazz;

    public SubscribeMethod(Method method, ThreadMode threadMode, Class<?> clazz) {
        this.method = method;
        this.threadMode = threadMode;
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
