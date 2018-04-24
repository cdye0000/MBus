package com.cdye.mbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by cdy on 2018/4/24.
 */

public class MBus {
    private static MBus defaultInstance;

    public static MBus getDefault() {
        if (defaultInstance == null) {
            synchronized (MBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new MBus();
                }
            }
        }
        return defaultInstance;
    }

    /**
     * 订阅者缓存
     * key 是订阅者所在对象，value 是订阅者对象中所有的订阅方法
     */
    private static final Map<Class<?>, List<SubscriberMethod>> METHOD_CACHE = new HashMap<>();
    /**
     * 订阅集合
     * key 是订阅标签 value是标签对应的所有订阅，在哪个对象中被订阅，被订阅的方法
     */
    private static final Map<String, List<Subscription>> SUBSCRIBES = new HashMap<>();
    /**
     * 注册的订阅
     * key 是注册的对象，value是该对象所有的订阅标签
     * 方便解除注册
     */
    private static final Map<Class<?>, List<String>> REGISTERS = new HashMap<>();

    /**
     * 注册
     * 获取该对象所有订阅方法并缓存
     * 获取所有订阅集合
     * 获取对象与标签集合，便于解除注册
     *
     * @param subscriber
     */
    public void register(Object subscriber) {
        Class<?> subscriberClass = subscriber.getClass();
        List<SubscriberMethod> subscriberMethodList = findSubscribe(subscriberClass);
        List<String> tags = REGISTERS.get(subscriberClass);
        if (tags == null) {
            tags = new ArrayList<>();
        }
        for (SubscriberMethod subscriberMethod : subscriberMethodList) {
            String tag = subscriberMethod.getTag();
            if (!tags.contains(tag)) {
                tags.add(tag);
            }
            List<Subscription> subscriptions = SUBSCRIBES.get(tag);
            if (subscriptions == null) {
                subscriptions = new ArrayList<>();
            }
            Subscription newSubScription = new Subscription(subscriber, subscriberMethod);
            subscriptions.add(newSubScription);
            SUBSCRIBES.put(tag, subscriptions);
        }
        REGISTERS.put(subscriberClass, tags);

    }

    /**
     * 解除注册
     * 移除所有订阅
     * 注册时订阅所在对象中所有的tag标签都需要清除
     *
     * @param subscriber
     */
    public void unregister(Object subscriber) {
        List<String> tags = REGISTERS.remove(subscriber.getClass());
        if (tags != null) {
            for (String tag : tags) {
                List<Subscription> subscriptions = SUBSCRIBES.get(tag);
                if (subscriptions != null) {
                    Iterator<Subscription> iterator = subscriptions.iterator();
                    while (iterator.hasNext()) {
                        Subscription next = iterator.next();
                        if (next.getSubscriber() == subscriber) {
                            iterator.remove();
                        }
                    }
                }
            }
        }

    }

    public void clear() {
        METHOD_CACHE.clear();
        SUBSCRIBES.clear();
        REGISTERS.clear();
    }

    /**
     * 找到注册的对象中被Subscriber注解的方法
     *
     * @param subscriberClass
     * @return
     */
    private List<SubscriberMethod> findSubscribe(Class<?> subscriberClass) {

        List<SubscriberMethod> subscriberMethodList = METHOD_CACHE.get(subscriberClass);
        if (subscriberMethodList == null) {
            subscriberMethodList = new ArrayList<>();
            Method[] declaredMethods = subscriberClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                Subscriber subscriberAnnotation = declaredMethod.getAnnotation(Subscriber.class);
                if (subscriberAnnotation != null) {
                    String[] values = subscriberAnnotation.value();
                    Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                    for (String value : values) {
                        declaredMethod.setAccessible(true);
                        SubscriberMethod subscriberMethod = new SubscriberMethod(value, declaredMethod, parameterTypes);
                        subscriberMethodList.add(subscriberMethod);
                    }
                }
            }
            METHOD_CACHE.put(subscriberClass, subscriberMethodList);
        }
        return subscriberMethodList;
    }

    /**
     * 发送事件
     * @param tag 可以接收的标签
     * @param params 发送的内容
     */
    public void post(String tag,Object...params){
        List<Subscription> subscriptions = SUBSCRIBES.get(tag);
        for (Subscription subscription : subscriptions) {
            SubscriberMethod subscriberMethod = subscription.getSubscriberMethod();
            Class<?>[] parameterTypes = subscriberMethod.getParameterTypes();
            Object[] realParams=new Object[parameterTypes.length];
            if (parameterTypes!=null){
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (i<params.length&&parameterTypes[i].isInstance(params[i])){
                        realParams[i]=params[i];
                    }else {
                        realParams[i]=null;
                    }
                }
            }
            try {
                subscriberMethod.getMethod().invoke(subscription.getSubscriber(),realParams);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }


        }

    }
}
