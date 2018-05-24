// EventBusService.aidl
package com.cdye.mbus.two;

// Declare any non-default types here with import statements
import com.cdye.mbus.two.Response;
import com.cdye.mbus.two.Request;

interface EventBusService {
    Response send(in Request request);

}
