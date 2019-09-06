package com.huidisen.ep750.net;

import com.huidisen.ep750.bean.EmployeeInfoBean;
import com.huidisen.ep750.bean.FlightInfoBean;
import com.huidisen.ep750.bean.FlightInfoDetailBean;
import com.huidisen.ep750.bean.ResultStatusBean;
import com.huidisen.ep750.bean.TaskBean;
import com.huidisen.ep750.bean.UpdateInfoBean;
import com.huidisen.ep750.bean.VehicleInfoBean;
import com.huidisen.ep750.bean.SingleTaskBean;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by lovexiaov on 16/6/2.
 * 请求接口类，需要请求哪个接口可以按 requestAllFlights 方法格式添加。
 */
public interface NetService {

    @FormUrlEncoded
    @POST("flyInfo/getAllByKeys.do")
    Call<FlightInfoBean> requestAllFlights(@Field("columnList") String columnList);

    @FormUrlEncoded
    @POST("flyInfo/getFlyInfoById.do")
    Call<FlightInfoDetailBean> requestFlightDetail(@Field("dataId") String dataId);

    @FormUrlEncoded
    @POST("task/getAllTaskByDeviceId.do")
    Call<TaskBean> requestAllTasks(@Field("deviceId") String deviceId);

    @FormUrlEncoded
    @POST("mobileUser/getInfoByJobNum.do")
    Call<EmployeeInfoBean> getEmployeeInfo(@Field("jobNum") String jobNum, @Field("vehicleId") String deviceId);

    @FormUrlEncoded
    @POST("task/getTaskById.do")
//    Call<TaskBean.DataBean> getTaskInfoById(@Field("dataId") String dataId);
    Call<SingleTaskBean> getTaskInfoById(@Field("dataId") String dataId);

    @FormUrlEncoded
    @POST("task/vehicleReportNode.do")
    Call<ResultStatusBean> reportTaskNode(@Field("jobNumber") String jobNumber,
                                          @Field("vehicleId") String vehicleId,
                                          @Field("taskId") String taskId,
                                          @Field("nodeCode") String nodeCode);

    @FormUrlEncoded
    @POST("/flyInfo/searchByCondition.do")
    Call<FlightInfoBean> searchFlightByCondition(
            @Field("searchKey") String key,
            @Field("searchValue") String value,
            @Field("columnList") String columnList
    );

    @FormUrlEncoded
    @POST("mobileUser/getVehicleInfo.do")
    Call<VehicleInfoBean> getCarInfo(@Field("vehicleId") String vehicleId,
                                     @Field("versionName") String versionName,
                                     @Field("versionCode") String versionCode);

    @FormUrlEncoded
    @POST("task/vehicleApplyForTask.do")
    Call<ResultStatusBean> applyNoFlightTask(@Field("type") String type,
                                             @Field("vehicleId") String vehicleId);

    @Streaming
    @GET
    Call<ResponseBody> download(@Url String fileUrl);

    @FormUrlEncoded
    @POST("/vehicleCheck/uploadInfo.do")
    Call<ResultStatusBean> uploadCarInfo(
            @Field("jobNumber") String jobNumber,
            @Field("vehicleId") String vehicleId,
            @QueryMap Map<String, Object> conditionMap
//            @Field("carInOut") int carInOut,
//            @Field("fuelOil") int fuelOil,
//            @Field("engineOil") int engineOil,
//            @Field("water") int water,
//            @Field("electricity") int electricity,
//            @Field("tirePressure") int tirePressure,
//            @Field("hydraulicOil") int hydraulicOil,
//            @Field("lighting") int lighting,
//            @Field("brake") int brake,
//            @Field("normal") int normal,
//            @Field("generator") int generator,
//            @Field("airSupply") int airSupply,
//            @Field("ketiSystem") int ketiSystem
    );

    @FormUrlEncoded
    @POST("mobileUser/taskConfigValidate.do")
    Call<ResultStatusBean> compareMD5(@Field("md5") String md5);


    @FormUrlEncoded
    @POST("/location/upload.do")
    Call<ResultStatusBean> uploadLocationData(@Field("lon") double lon,
                                              @Field("lat") double lat,
                                              @Field("alt") double alt,
                                              @Field("speed") float speed,
                                              @Field("accuracy") float accuracy,
                                              @Field("starCount") int starCount,
                                              @Field("gpsStatus") int gpsStatus,
                                              @Field("timestamp") long timestamp,
                                              @Field("vehicleId") String vehicleId,
                                              @Field("jobNumber") String jobNumber,
                                              @Field("taskId") int taskId);

//    @GET("http://192.168.3.215:8080/HDS_EV750app/update.json")
    @GET("http://10.201.1.199:8888//update.json")
//    @GET("http://10.108.1.4:8080/HDS_EV750app/update.json")
    Call<UpdateInfoBean> checkUpdate();
}

