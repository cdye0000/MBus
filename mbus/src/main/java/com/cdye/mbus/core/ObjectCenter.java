package com.cdye.mbus.core;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cdy on 2018/5/24.
 */

public class ObjectCenter {
    private static ObjectCenter instance=null;
    private ConcurrentHashMap<String,Object> objects;

    private ObjectCenter(){
        objects=new ConcurrentHashMap<>();
    }
    public static ObjectCenter getInstance(){
        if (instance==null){
            synchronized (ObjectCenter.class){
                if (instance==null){
                    instance=new ObjectCenter();
                }
            }
        }
        return instance;
    }
    public Object get(String name){
        return objects.get(name);
    }
    public void put(String name,Object o){
        objects.put(name,o);
    }

}
