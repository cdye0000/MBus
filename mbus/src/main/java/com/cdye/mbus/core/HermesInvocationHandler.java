package com.cdye.mbus.core;

import android.text.TextUtils;

import com.cdye.mbus.response.ResponseBody;
import com.cdye.mbus.two.Response;
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
        Response response= Hermes.getInstance().sendRequest(service,clazz,method,args,Hermes.TYPE_NEW);
        if (!TextUtils.isEmpty(response.getData())){
            ResponseBody responseBody=GSON.fromJson(response.getData(),ResponseBody.class);
            if (responseBody.getData()!=null){
                Object data = responseBody.getData();
                String dataStr=GSON.toJson(data);
                Class<?> returnType = method.getReturnType();
                Object o=GSON.fromJson(dataStr,returnType);
                return o;
            }
        }
        return null;
    }
}
