package com.cdye.mbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdye.mbus.MBus;
import com.cdye.mbus.Subscriber;

/**
 * Created by cdy on 2018/4/24.
 */

public class Fragment2 extends Fragment {
    private static final String TAG = "Fragment2";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.f2,null);
        init(view);
        MBus.getDefault().register(this);
        return view;
    }

    private void init(View view) {
        view.findViewById(R.id.f2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MBus.getDefault().post("2",2);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MBus.getDefault().unregister(this);
    }
    @Subscriber("0")
    public void fromMain(){
        Log.e(TAG,"fromMain");
    }
    @Subscriber("1")
    public void fromFragment1(){
        Log.e(TAG,"fromFragment1");
    }
}
