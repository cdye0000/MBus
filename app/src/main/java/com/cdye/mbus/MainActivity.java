package com.cdye.mbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cdye.mbus.MBus;
import com.cdye.mbus.Subscriber;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MBus.getDefault().register(this);
    }

    public void main(View view) {
        MBus.getDefault().post("0");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MBus.getDefault().unregister(this);
    }
    @Subscriber({"1","2"})
    public void fromFragment1(String message,Integer no){
        Log.e(TAG,"fromFragment1 message="+message+"no="+no);
    }
    @Subscriber("2")
    public void fromFragment2(String message,Integer no){
        Log.e(TAG,"fromFragment2 message="+message+"no="+no);
    }
}
