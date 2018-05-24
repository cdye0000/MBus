package com.cdye.mbus.response;

/**
 * Created by cdy on 2018/5/24.
 */

public class ResponseBody {
    private Object data;

    public ResponseBody(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
