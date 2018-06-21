package com.cdye.mbus.core;

import android.content.Context;

import com.cdye.mbus.annotation.ClassId;
import com.cdye.mbus.bean.RequestBody;
import com.cdye.mbus.bean.RequestParameter;
import com.cdye.mbus.service.HermesService;
import com.cdye.mbus.two.Request;
import com.cdye.mbus.two.Response;
import com.cdye.mbus.utils.TypeUtils;
import com.google.gson.Gson;

import java.lang.reflect.Method;
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
    Gson GSON = new Gson();
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
        sendRequest(HermesService.class,clazz,null,parameters,TYPE_GET);
        return getProxy(HermesService.class,clazz);
    }
    private <T> T getProxy(Class<?extends HermesService> service,Class clazz){
        ClassLoader classLoader=service.getClassLoader();
        T proxy= (T) Proxy.newProxyInstance(classLoader,new Class[]{clazz},new HermesInvocationHandler(clazz,service));
        return proxy;

    }
    public  <T>Response sendRequest(Class<HermesService> hermesServiceClass, Class<T> clazz, Method method,Object[] paremeters,int type){
        RequestBody requestBody=new RequestBody();
        String className=null;
        if (clazz.getAnnotation(ClassId.class)==null){
            requestBody.setRequestClassName(clazz.getName());
            requestBody.setResultClassName(clazz.getName());
        }else {
            requestBody.setRequestClassName(clazz.getAnnotation(ClassId.class).value());
            requestBody.setResultClassName(clazz.getAnnotation(ClassId.class).value());
        }
        if (method!=null){
            requestBody.setMethodName(TypeUtils.getMethodId(method));
        }
        RequestParameter[] requestParameters=null;
        if (paremeters!=null&&paremeters.length>0){
            requestParameters=new RequestParameter[paremeters.length];
            for (int i = 0; i < paremeters.length; i++) {
                Object paramter=paremeters[i];
                String parameterClassName=paramter.getClass().getName();
                String parameterValue=GSON.toJson(paramter);
                requestParameters[i]=new RequestParameter(parameterClassName,parameterValue);
            }
            if (requestParameters!=null){
                requestBody.setRequestParameters(requestParameters);
            }

        }
        Request request=new Request(GSON.toJson(requestBody),type);
        return serviceConnectionManager.request(hermesServiceClass,request);

    }


}
