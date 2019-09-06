package com.huidisen.ep750.model;

import android.content.ComponentName;
import android.content.Context;
import android.widget.Toast;

import com.huidisen.ep750.bean.ResultStatusBean;
import com.huidisen.ep750.net.NetService;
import com.huidisen.ep750.net.NetTask;
import com.huidisen.ep750.utils.Commondata;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by miaoyichao on 16/6/3.
 */
public class WorkerModelImpl implements WorkerModel {

    private Context context;
    private static WorkerModelImpl instance;

    public WorkerModelImpl(Context context) {
        this.context = context.getApplicationContext();

    }

    public static WorkerModelImpl getInstance(Context context) {

        if (instance == null) {
            instance = new WorkerModelImpl(context);
        }
        return instance;
    }

    //    int carInOut, int fuelOil, int engineOil, int water, int electricity, int tirePressure, int hydraulicOil,
//    int lighting, int brake, int normal, int generator, int airSupply, int ketiSystem
    @Override
    public void CarCheck(String jobNumber, String vehicleId, Map<String, Object> conditionMap, final Callback.OnUploadInfoListener listener) {
        NetService netService = NetTask.generateRequest();
        retrofit2.Call<ResultStatusBean> flightInfoCall = netService.uploadCarInfo(
                jobNumber, Commondata.DEVICE_CODE, conditionMap);
        flightInfoCall.enqueue(new retrofit2.Callback<ResultStatusBean>() {
            @Override
            public void onResponse(Call<ResultStatusBean> call, Response<ResultStatusBean> response) {
                if (response.body() != null && response.body().getResult().equals(Commondata.SUCCESS) &&
                        response.body().getStatus() == 200) {
                    listener.onSuccess();
                } else {
                    if (response.body() != null && response.body().getResult() != null) {
                        listener.onError("车辆检查失败");
                    } else {
                        listener.onError(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultStatusBean> call, Throwable t) {
                listener.onError(null);
            }
        });
    }
}
