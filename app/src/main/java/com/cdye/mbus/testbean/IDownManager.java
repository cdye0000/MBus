package com.cdye.mbus.testbean;


import com.cdye.mbus.annotation.ClassId;

/**
 * Created by Administrator on 2018/5/23.
 */
@ClassId("com.cdye.mbus.testbean.DownManager")
public interface IDownManager  {
    public FileRecord getFileRecord();

    public void setFileRecord(FileRecord fileRecord);

}
