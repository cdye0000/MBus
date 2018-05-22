package com.cdye.mbus.response;

import com.alibaba.fastjson.JSON;
import com.cdye.mbus.bean.RequestBody;
import com.cdye.mbus.core.TypeCenter;
import com.cdye.mbus.two.Request;
import com.cdye.mbus.two.Response;

/**
 * Created by cdy on 2018/5/22.
 */

public abstract class ResponseMake {
    protected Class<?> resultClass;
    private Object[] paramters;
    protected TypeCenter typeCenter=TypeCenter.getInstance();

    protected abstract void invokeMethod();
    protected abstract void setMethod(RequestBody requestBody);
    public Response makeResponse(Request request){
        RequestBody requestBody= JSON.parseObject(request.getData(),RequestBody.class);
        resultClass=typeCenter.getClassType(requestBody.getResultClassName());
        setMethod(requestBody);
        return null;

    }
}
