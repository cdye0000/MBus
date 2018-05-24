package com.cdye.mbus.response;

import com.alibaba.fastjson.JSON;
import com.cdye.mbus.bean.RequestBody;
import com.cdye.mbus.bean.RequestParameter;
import com.cdye.mbus.core.ObjectCenter;
import com.cdye.mbus.core.TypeCenter;
import com.cdye.mbus.two.Request;
import com.cdye.mbus.two.Response;
import com.google.gson.Gson;

/**
 * Created by cdy on 2018/5/22.
 */

public abstract class ResponseMake {
    protected Class<?> resultClass;
    protected Object[] paramters;
    protected TypeCenter typeCenter = TypeCenter.getInstance();
    protected ObjectCenter objectCenter = ObjectCenter.getInstance();
    Gson GSON = new Gson();

    protected abstract Object invokeMethod();

    protected abstract void setMethod(RequestBody requestBody);

    public Response makeResponse(Request request) {
        RequestBody requestBody = JSON.parseObject(request.getData(), RequestBody.class);
        resultClass = typeCenter.getClassType(requestBody.getResultClassName());
        RequestParameter[] requestParameters = requestBody.getRequestParameters();
        if (requestParameters != null && requestParameters.length > 0) {
            paramters = new Object[requestParameters.length];
            for (int i = 0; i < requestParameters.length; i++) {
                RequestParameter requestParameter = requestParameters[i];
                Class<?> clazz = typeCenter.getClassType(requestParameter.getParameterClassName());
                paramters[i] = GSON.fromJson(requestParameter.getParameterValue(), clazz);
            }
        } else {
            paramters = new Object[0];
        }
        setMethod(requestBody);
        Object resuleObject = invokeMethod();
        ResponseBody responseBody = new ResponseBody(resuleObject);
        String data = GSON.toJson(responseBody);
        Response response = new Response(data);
        return response;
    }
}
