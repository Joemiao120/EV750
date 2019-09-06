package com.huidisen.ep750.model;

import java.util.Map;

/**
 * Created by miaoyichao on 16/6/3.
 */
public interface WorkerModel {
    void CarCheck(String jobNumber,
                  String vehicleId,
                  Map<String, Object> conditionMap,
                  Callback.OnUploadInfoListener listener

//                  int carInOut,
//                  int fuelOil,
//                  int engineOil,
//                  int water,
//                  int electricity,
//                  int tirePressure,
//                  int hydraulicOil,
//                  int lighting,
//                  int brake,
//                  int normal,
//                  int generator,
//                  int airSupply,
//                  int ketiSystem

    );// 后期增加检查状态的形式
}
