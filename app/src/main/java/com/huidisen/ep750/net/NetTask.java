package com.huidisen.ep750.net;

import com.huidisen.ep750.utils.Commondata;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求任务帮助类，可以通过 generateRequest 方法生成 {@link NetService} 请求对象。
 */
public class NetTask {
    private static Retrofit retrofit;
    private static NetService netService;
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(0, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS).build();

    private static Retrofit retrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Commondata.HTTP_IP).addConverterFactory(GsonConverterFactory.create())
                    .client(client).build();

        }
        return retrofit;
    }

    public static NetService generateRequest() {
        if (netService == null) netService = retrofit().create(NetService.class);

        return netService;
    }

}