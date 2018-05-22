package com.cdye.mbus.two;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cdy on 2018/5/22.
 */

public class Request implements Parcelable {
    private String data;
    private int type;

    protected Request(Parcel in) {
        data = in.readString();
        type = in.readInt();
    }

    public String getData() {
        return data;
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
        dest.writeInt(type);
    }
}