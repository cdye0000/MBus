package com.cdye.mbus.core;

import com.google.gson.Gson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by cdy on 2018/5/24.
 */

public class HermesInvocationHandler implements InvocationHandler {
    private Class clazz;
    private static final Gson GSON=new Gson();
    private Class service;

    public HermesInvocationHandler(Class clazz, Class service) {
        this.clazz = clazz;
        this.service = service;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
