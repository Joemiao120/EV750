package com.huidisen.ep750.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.DataUtils;
import com.huidisen.ep750.utils.DeviceControlUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class PollingDeviceService extends Service {
    private static final String TAG = "PollingDeviceService";

    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);

    private static int COUNT = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onStart(Intent intent, int startId) {
        SCHEDULER.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "wait 30 seconds, will start to checkNewVersion the device connection status.");
                    Thread.sleep(30000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                try {
                    while (true) {

                        Log.d(TAG, "Try to checkNewVersion the device connection status.");
                        //获得设备连接状态
                        boolean status = getDeviceConnectionStatus();
                        if (!status) {
                            //重新对设备上电
                            COUNT += 1;
                            Log.d(TAG, "Find the device is not connected, the count is: " + COUNT);
                        } else {
                            COUNT = 0;
                            Log.d(TAG, "Find the device is connected, so set the count back to 0.");
                        }

                        if (COUNT >= 3) {
                            Log.d(TAG, "Try to restart device.");
                            restartDevice();
                            COUNT = 0;
                        }
                        //每10秒跑一次
                        Thread.sleep(10000);
                    }
                } catch (Exception e) {
                    Log.d(TAG, "Get error when checkNewVersion device connection, the error is " + e.getMessage());
                    Log.d(TAG, "The stack trace is: " + e.getStackTrace());
                }
            }
        });
    }

    private void restartDevice() {
        try {

            DeviceControlUtils.resetUsbnet();
            Thread.sleep(1000);
            DeviceControlUtils.powerOffDevice();
            //等待1秒钟
            Thread.sleep(1000);
            DeviceControlUtils.powerOnDevice();
            Log.d(TAG, "Wait 1 minute for device to start up.");
            //等待1分钟让设备启动完成。
            Thread.sleep(60000);
            Log.d(TAG, "Restart device finished.");
        } catch (InterruptedException e) {
            Log.d(TAG, "Get error when restart device, the error is " + e.getMessage());
            Log.d(TAG, "The stack trace is: " + e.getStackTrace());
        }

    }

    private boolean getDeviceConnectionStatus() {
        // 有一个断的就会重启
        return DataUtils.getIN_PORT_STATUS() && DataUtils.getOUT_PORT_STATUS();
    }
}
