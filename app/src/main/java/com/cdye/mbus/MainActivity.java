package com.cdye.mbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cdye.mbus.one.MBus;
import com.cdye.mbus.one.Subscriber;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册
        MBus.getDefault().register(this);
    }

    /**
     * 发送一条消息，带有标签“0”的可以接收
     * @param view
     */
    public void main(View view) {
        MBus.getDefault().post("0");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册
        MBus.getDefault().unregister(this);
    }

    /**
     * 接收带有标签"1",或者"2"的消息，携带参数message和no，可能内容为null
     * @param message
     * @param no
     */
    @Subscriber({"1","2"})
    public void fromFragment1(String message,Integer no){
        Log.e(TAG,"fromFragment1 message="+message+"no="+no);
    }

    /**
     * 接收带有标签"1"的消息，携带参数message和no，可能内容为null
     * @param message
     * @param no
     */
    @Subscriber("2")
    public void fromFragment2(String message,Integer no){
        Log.e(TAG,"fromFragment2 message="+message+"no="+no);
    }

    public void sec(View view) {
        startActivity(new Intent(this,SecActivity.class));
        startActivity(new Intent(this,SecActivity.class));
        startActivity(new Intent(this,SecActivity.class));
    }
}
