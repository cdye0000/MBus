package com.cdye.mbus.response;

import com.cdye.mbus.bean.RequestBody;
import com.cdye.mbus.bean.RequestParameter;
import com.cdye.mbus.core.TypeCenter;
import com.cdye.mbus.utils.TypeUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by cdy on 2018/5/22.
 */

public class InstanceResponseMake extends ResponseMake {
    private Method method;
    @Override
    protected Object invokeMethod() {
        Object object=null;
        try {
            object=method.invoke(null,paramters);
            objectCenter.put(object.getClass().getName(),object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return object;
    }

    @Override
    protected void setMethod(RequestBody requestBody) {
        RequestParameter[] requestParameters = requestBody.getRequestParameters();
        Class<?>[] parameterTypes=null;
        if (requestParameters!=null&&requestParameters.length>0){
            parameterTypes=new Class[requestParameters.length];
            for (int i = 0; i < requestParameters.length; i++) {
                parameterTypes[i]= TypeCenter.getInstance().getClassType(requestParameters[i].getParameterClassName());
            }
        }
        String methodName=requestBody.getMethodName();
         method= TypeUtils.getMethodForGettingInstance(resultClass,methodName,parameterTypes);



    }
}
