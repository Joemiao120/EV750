package com.huidisen.ep750.bean;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lovexiaov on 16/6/2.
 * 航班信息实体类，内含异步回调类
 */
public class FlightInfoBean implements Serializable {


    public static final int ERROR_CODE = -1;
    public static final int SUCCESS_CODE = 200;

    /**
     * result : success
     * data : {"frontNoFly":[{"incomingFlyNo":"CZ1165T","planedArrival":"香港-郑州-香港","planedFly":"0","id":"00004","location":"B738"}],"frontFly":[{"incomingFlyNo":"CZ1165T","planedArrival":"新郑-上海","planedFly":"0","id":"00003","location":"B738"}],"flying":[{"incomingFlyNo":"CZ1165T","planedArrival":"香港-郑州-香港","planedFly":"0","id":"00005","location":"B738"}],"arriveHere":[{"incomingFlyNo":"CZ1165T","planedArrival":"香港-广州-香港","planedFly":"0","id":"00007","location":"B738"},{"incomingFlyNo":"CZ1165T","planedArrival":"香港-郑州-香港","planedFly":"0","id":"00006","location":"B738"},{"incomingFlyNo":"CZ1165T","planedArrival":"香港-郑州-香港","planedFly":"0","id":"00002","location":"B738"},{"incomingFlyNo":"CZ1165T","planedArrival":"香港-郑州-香港","planedFly":"0","id":"00001","location":"B738"}]}
     * status : 200
     */

    @SerializedName("result") public String result;
    @SerializedName("data") public DataBean data;
    @SerializedName("status") public int status;

    @Override
    public String toString() {
        return "FlightInfoBean{" +
                "\n result='" + result + '\'' +
                ",\n data=" + data +
                ",\n status=" + status +
                "\n}";
    }
    public static class Error {
        public String message;
        public Error(String message) {
            this.message = message;
        }
    }

    public static class CallBack implements Callback<FlightInfoBean> {
        private Context context;


        public CallBack(Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(Call<FlightInfoBean> call, Response<FlightInfoBean> response) {
            FlightInfoBean body = response.body();
            if (body != null) {
                Logger.d(body.toString());
                EventBus.getDefault().post(body);
            } else {
                EventBus.getDefault().post(new Error("Request body is null!"));
            }
        }

        @Override
        public void onFailure(Call<FlightInfoBean> call, Throwable t) {
            Logger.e(t.getLocalizedMessage());
            EventBus.getDefault().post(new Error(t.getLocalizedMessage()));
        }
    }

    public static class DataBean implements Serializable {

        /**
         * incomingFlyNo : CZ1165T
         * planedArrival : 香港-郑州-香港
         * planedFly : 0
         * id : 00004
         * location : B738
         */
        @SerializedName("frontNoFly") public List<FlightBean> frontNoFly;

        /**
         * incomingFlyNo : CZ1165T
         * planedArrival : 新郑-上海
         * planedFly : 0
         * id : 00003
         * location : B738
         */
        @SerializedName("frontFly") public List<FlightBean> frontFly;

        /**
         * incomingFlyNo : CZ1165T
         * planedArrival : 香港-郑州-香港
         * planedFly : 0
         * id : 00005
         * location : B738
         */
        @SerializedName("flying") public List<FlightBean> flying;

        /**
         * incomingFlyNo : CZ1165T
         * planedArrival : 香港-广州-香港
         * planedFly : 0
         * id : 00007
         * location : B738
         */
        @SerializedName("arriveHere") public List<FlightBean> arriveHere;
        /**
         * incomingFlyNo : CZ1165T
         * planedArrival : 香港-广州-香港
         * planedFly : 0
         * id : 00007
         * location : B738
         */
        @SerializedName("farLocation") public List<FlightBean> farLocation;

        @Override
        public String toString() {
            return "DataBean{" +
                    "frontNoFly=" + frontNoFly +
                    ",\n frontFly=" + frontFly +
                    ",\n flying=" + flying +
                    ",\n arriveHere=" + arriveHere +
                    ",\n farLocation=" + farLocation +
                    '}';
        }

    }
}
