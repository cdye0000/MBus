package com.cdye.mbus.testbean;


import com.cdye.mbus.annotation.ClassId;

/**
 * Created by Xiaofei on 16/4/25.
 */
//接口的方式  描述 一个类
@ClassId("com.cdye.mbus.testbean.UserManager")
public interface IUserManager {

    public Person getPerson();

    public void setPerson(Person person);
}
