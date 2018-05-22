package com.cdye.mbus.utils;

import java.lang.reflect.Method;

/**
 * Created by cdy on 2018/5/22.
 */

public class TypeUtils {
    public static String getMethodId(Method method) {
        StringBuilder stringBuilder=new StringBuilder(method.getName());
        stringBuilder.append("(").append(getParameters(method.getParameterTypes())).append(")");

        return stringBuilder.toString();
    }

    private static String getParameters(Class<?>[] parameterTypes) {
        StringBuilder stringBuilder=new StringBuilder();
        if (parameterTypes.length==0){
            return stringBuilder.toString();
        }
        stringBuilder.append(getClassName(parameterTypes[0]));
        if (parameterTypes.length>1){
            for (int i=1;i<parameterTypes.length;i++){
                stringBuilder.append(",").append(getClassName(parameterTypes[i]));
            }
        }

        return stringBuilder.toString();
    }

    private static String getClassName(Class<?> clazz) {
        if (clazz == Boolean.class) {
            return "boolean";
        } else if (clazz == Byte.class) {
            return "byte";
        } else if (clazz == Character.class) {
            return "char";
        } else if (clazz == Short.class) {
            return "short";
        } else if (clazz == Integer.class) {
            return "int";
        } else if (clazz == Long.class) {
            return "long";
        } else if (clazz == Float.class) {
            return "float";
        } else if (clazz == Double.class) {
            return "double";
        } else if (clazz == Void.class) {
            return "void";
        } else {
            return clazz.getName();
        }
    }
    public static Method getMethodForGettingInstance(Class<?> clazz ,String methodName,Class<?>[] parameterTypes){
        Method[] methods = clazz.getMethods();
        Method result=null;
        for (Method method : methods) {
            String tempMethodName=method.getName();
            if ("getInstance".equals(tempMethodName)){

            }
        }

        return result;
    }
}
