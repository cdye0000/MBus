package com.cdye.mbus.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.cdye.mbus.service.HermesService;
import com.cdye.mbus.two.EventBusService;
import com.cdye.mbus.two.Request;
import com.cdye.mbus.two.Response;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cdy on 2018/5/24.
 */

public class ServiceConnectionManager {
    private static ServiceConnectionManager instance=new ServiceConnectionManager();
    public static ServiceConnectionManager getInstance(){return instance;}
    private final ConcurrentHashMap<Class<?extends HermesService>,EventBusService>  hermesServices=new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Class<?extends HermesService>,HermesServiceConnection> hermesServiceConnections=new ConcurrentHashMap<>();


    public void bind(Context context ,String packageName,Class<?extends HermesService> service){
        HermesServiceConnection hermesServiceConnection=new HermesServiceConnection(service);
        hermesServiceConnections.put(service,hermesServiceConnection);
        Intent intent;
        if (TextUtils.isEmpty(packageName)){
            intent=new Intent(context,service);
        }else {
            intent=new Intent();
            intent.setClassName(packageName,service.getName());
        }
        context.bindService(intent,hermesServiceConnection,Context.BIND_AUTO_CREATE);


    }
    public Response request(Class<HermesService> clazz, Request request){
        EventBusService service=hermesServices.get(clazz);
        try {
            Response response = service.send(request);
            return response;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

   private class HermesServiceConnection implements ServiceConnection{
       private Class<?extends HermesService> clazz;

       public HermesServiceConnection(Class<? extends HermesService> clazz) {
           this.clazz = clazz;
       }

       @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            EventBusService eventBusService=EventBusService.Stub.asInterface(service);
            hermesServices.put(clazz,eventBusService);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }








}
