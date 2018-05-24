package com.cdye.mbus.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.cdye.mbus.core.Hermes;
import com.cdye.mbus.response.InstanceResponseMake;
import com.cdye.mbus.response.ResponseMake;
import com.cdye.mbus.two.EventBusService;
import com.cdye.mbus.two.Request;
import com.cdye.mbus.two.Response;


/**
 * Created by cdy on 2018/5/24.
 */

public class HermesService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    private EventBusService.Stub binder=new EventBusService.Stub() {

        @Override
        public Response send(Request request) throws RemoteException {
            ResponseMake responseMake=null;
            switch (request.getType()){
                case Hermes.TYPE_GET:
                    responseMake=new InstanceResponseMake();
                    break;
                case Hermes.TYPE_NEW:
                    break;
            }

            return responseMake.makeResponse(request);
        }
    };
}
