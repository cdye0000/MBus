package com.cdye.mbus.two;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cdy on 2018/5/22.
 */

public class Response implements Parcelable {
    private String data;

    protected Response(Parcel in) {
        data = in.readString();
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
    }
}
