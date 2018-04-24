package com.cdye.mbus;

/**
 *  n.订阅
 *  subscriber          订阅者对象，Subscriber注解的方法所在对象
 *  subscriberMethod    Subscriber注解的方法
 *
 *
 *
 */

public class Subscription {
    private Object subscriber;
    private SubscriberMethod subscriberMethod;

    public Subscription(Object subscriber, SubscriberMethod subscriberMethod) {
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
    }

    public Object getSubscriber() {
        return subscriber;
    }

    public SubscriberMethod getSubscriberMethod() {
        return subscriberMethod;
    }
}
