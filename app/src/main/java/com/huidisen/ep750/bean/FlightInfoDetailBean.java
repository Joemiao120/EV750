package com.huidisen.ep750.bean;

import com.google.gson.annotations.SerializedName;

public class FlightInfoDetailBean {
    public String result;
    public int status;

    @SerializedName("data")
    public TaskFlyInfoBean data;

    @Override
    public String toString() {
        return "FlightInfoDetailBean{" +
                "result='" + result + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}