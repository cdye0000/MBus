package com.cdye.mbus.core;

import com.cdye.mbus.utils.TypeUtils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cdy on 2018/5/22.
 */

public class TypeCenter {
    private static final TypeCenter ourInstance = new TypeCenter();

    public static TypeCenter getInstance() {
        return ourInstance;
    }

    private final ConcurrentHashMap<String,Class<?>> annotationedCalss;

    private final ConcurrentHashMap<Class<?>,ConcurrentHashMap<String,Method>> rawMethod;



    private TypeCenter() {
        annotationedCalss=new ConcurrentHashMap<>();
        rawMethod=new ConcurrentHashMap<>();
    }
    public void register(Class<?> clazz){
        registerClass(clazz);
        registerMethod(clazz);
    }

    private void registerMethod(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            rawMethod.putIfAbsent(clazz,new ConcurrentHashMap<String, Method>());
            ConcurrentHashMap map=rawMethod.get(clazz);
            String key= TypeUtils.getMethodId(method);
            map.put(key,map);

        }
    }

    private void registerClass(Class<?> clazz) {
        String className=clazz.getName();
        annotationedCalss.putIfAbsent(className,clazz);
    }

    public Class<?> getClassType(String resultClassName) {
        Class<?> clazz = annotationedCalss.get(resultClassName);
        if (clazz==null){
            try {
                Class.forName(resultClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return clazz;
    }
}
