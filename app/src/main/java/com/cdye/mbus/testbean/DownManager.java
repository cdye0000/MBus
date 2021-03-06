package com.cdye.mbus.testbean;


import com.cdye.mbus.annotation.ClassId;

/**
 * Created by Administrator on 2018/5/23.
 */
@ClassId("com.cdye.mbus.testbean.DownManager")
public class DownManager implements IDownManager {
    private FileRecord fileRecord;
    private static DownManager sInstance = null;

    private DownManager() {

    }
    //约定这个进程A  单例对象的     规则    getInstance()
    public static synchronized DownManager getInstance() {
        if (sInstance == null) {
            sInstance = new DownManager();
        }
        return sInstance;
    }
    
    public FileRecord getFileRecord() {
        return fileRecord;
    }

    public void setFileRecord(FileRecord fileRecord) {
        this.fileRecord = fileRecord;
    }
}
