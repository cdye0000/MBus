package com.cdye.mbus.bean;

/**
 * Created by cdy on 2018/5/22.
 */

public class RequestBody {
    private String requestClassName;
    private String resultClassName;
    private String requestObject;
    private String methodName;
    private RequestParameter[] requestParameters;

    public String getRequestClassName() {
        return requestClassName;
    }

    public void setRequestClassName(String requestClassName) {
        this.requestClassName = requestClassName;
    }

    public String getResultClassName() {
        return resultClassName;
    }

    public void setResultClassName(String resultClassName) {
        this.resultClassName = resultClassName;
    }

    public String getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(String requestObject) {
        this.requestObject = requestObject;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public RequestParameter[] getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(RequestParameter[] requestParameters) {
        this.requestParameters = requestParameters;
    }
}
