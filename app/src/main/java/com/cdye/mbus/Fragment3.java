package com.cdye.mbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdye.mbus.one.MBus;
import com.cdye.mbus.one.Subscriber;

/**
 * Created by cdy on 2018/4/25.
 */

public class Fragment3  extends Fragment{
    private static final String TAG = "Fragment1";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.f1,null);
        init(view);
        MBus.getDefault().register(this);
        return view;
    }

    private void init(View view) {
        view.findViewById(R.id.f1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MBus.getDefault().post("1","fragment1发送");
            }
        });
    }
    @Subscriber("0")
    public void fromMain(){
        Log.e(TAG,"fromMain");
    }
    @Subscriber("2")
    public void fromFragment2(){
        Log.e(TAG,"fromFragment2");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MBus.getDefault().unregister(this);
    }
    public interface FragmentInterFace{
        void fragmentdosomething();
    }
}
