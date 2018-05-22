package com.cdye.mbus.two;

import android.os.Handler;
import android.os.Looper;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cdy on 2018/5/21.
 */

public class EventBus {
    private static EventBus instance=null;

    private Map<Object,List<SubscribeMethod>> cacheMap;

    private ExecutorService executorService;

    private Handler handler;

    public EventBus() {
        cacheMap=new HashMap<>();
        executorService= Executors.newCachedThreadPool();
        handler=new Handler(Looper.getMainLooper());
    }
    public static EventBus getDefault(){
        if (instance==null){
            synchronized (EventBus.class){
                if (instance==null){
                    instance=new EventBus();
                }
            }
        }
        return instance;
    }
    public  void register(Object target){
        Class<?> clazz=target.getClass();
        List<SubscribeMethod> subscriberMethods=cacheMap.get(target);
        if (subscriberMethods==null){
            subscriberMethods=findSubscribeMethods(clazz);
            cacheMap.put(target,subscriberMethods);
        }
    }

    private List<SubscribeMethod> findSubscribeMethods(Class<?> clazz) {
        List<SubscribeMethod> subscribeMethods=new ArrayList<>();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method:declaredMethods){
            Subscribe annotation = method.getAnnotation(Subscribe.class);
            if (annotation==null)continue;
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length!=1){
                throw new IllegalArgumentException("只能接收一个参数");
            }
            ThreadMode threadMode=annotation.threadMode();
            SubscribeMethod subscribeMethod=new SubscribeMethod(method,threadMode,clazz);
            subscribeMethods.add(subscribeMethod);
        }
        return subscribeMethods;
    }
    public void post(final Object object){
        Set<Object> keySet = cacheMap.keySet();
        Iterator<Object> iterator = keySet.iterator();
        while (iterator.hasNext()){
            final Object target = iterator.next();
            List<SubscribeMethod> subscribeMethods = cacheMap.get(target);
            for (final SubscribeMethod subscribeMethod : subscribeMethods) {
                if (subscribeMethod.getClazz().isAssignableFrom(object.getClass())){
                    switch (subscribeMethod.getThreadMode()){
                        case Async:
                            if (Looper.myLooper()==Looper.getMainLooper()){
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscribeMethod,target,object);
                                    }
                                });
                            }else {
                                invoke(subscribeMethod,target,object);
                            }
                            break;
                        case MainThread:
                            if (Looper.myLooper()==Looper.getMainLooper()){
                                invoke(subscribeMethod,target,object);
                            }else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscribeMethod,target,object);
                                    }
                                });
                            }

                            break;
                        case PostThread:
                            invoke(subscribeMethod,target,object);
                            break;
                    }
                }
            }
        }

    }

    private void invoke(SubscribeMethod subscribeMethod, Object target, Object object) {
        Method method = subscribeMethod.getMethod();
        try {
            method.invoke(target,object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public void unregister(Object target){
        List<SubscribeMethod> remove = cacheMap.remove(target);
        if (remove!=null){
            remove.clear();
            remove=null;
        }
    }

}
