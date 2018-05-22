package com.cdye.mbus.one;

import java.lang.reflect.Method;

/**
 * @description  被Subscriber注解的方法 被订阅的方法
 *  tag            注解标签，含有tag标签的方法可以接收发送时指定tag的消息
 *  method         被注解的方法的Method
 *  parameterTypes 方法的参数类型，可能时多个
 */

public class SubscriberMethod {
    private String tag;
    private Method method;
    private Class<?>[] parameterTypes;

    public SubscriberMethod(String tag, Method method, Class<?>[] parameterTypes) {
        this.tag = tag;
        this.method = method;
        this.parameterTypes = parameterTypes;
    }

    public String getTag() {
        return tag;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }
}
