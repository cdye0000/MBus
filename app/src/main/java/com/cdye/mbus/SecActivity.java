package com.cdye.mbus;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by cdy on 2018/4/26.
 */

public class SecActivity extends AppCompatActivity {
    private static final String TAG = "SecActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);


       Log.d(TAG,String.valueOf(isSystemClass(String.class)));
        Log.d(TAG,String.valueOf(isSystemClass(Fragment1.class)));
        Log.d(TAG,String.valueOf(isSystemClass(MainActivity.class)));
        Log.d(TAG,String.valueOf(isSystemClass(Activity.class)));
    }
    public boolean isSystemClass(Class<?> clazz){

        return clazz!=null&&clazz.getClassLoader()==null;

    }
}
