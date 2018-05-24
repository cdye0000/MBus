package com.cdye.mbus.core;

import android.content.Context;

import com.cdye.mbus.service.HermesService;

import java.lang.reflect.Proxy;

/**
 * Created by cdy on 2018/5/24.
 */

public class Hermes {
    //得到对象
    public static final int TYPE_NEW = 0;
    //得到单例
    public static final int TYPE_GET = 1;
    private static Hermes instance=new Hermes();
    public static Hermes getInstance(){return instance;}
    private ServiceConnectionManager serviceConnectionManager;
    private TypeCenter typeCenter;
    private Context appContext;

    public Hermes() {
        serviceConnectionManager = ServiceConnectionManager.getInstance();
        typeCenter=TypeCenter.getInstance();
    }

    public void register(Class<?> clazz){
        typeCenter.register(clazz);
    }
    public void init(Context context){
        appContext=context.getApplicationContext();
    }

    public void connect(Context context, String packageName, Class<?extends HermesService> service){
        connectApp(context,packageName,service);
    }

    private void connectApp(Context context, String packageName, Class<? extends HermesService> service) {
        init(context);
        serviceConnectionManager.bind(context,packageName,service);
    }
    public <T> T getInstance(Class<T> clazz,Object... parameters){

        return getProxy(HermesService.class,clazz);
    }
    private <T> T getProxy(Class<?extends HermesService> service,Class clazz){
        ClassLoader classLoader=service.getClassLoader();
        T proxy= (T) Proxy.newProxyInstance(classLoader,new Class[]{clazz},new HermesInvocationHandler(clazz,service));
        return proxy;

    }

}
