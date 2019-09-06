package com.huidisen.ep750.utils;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lovelife on 2015-05-02.
 */
public class DeviceControlUtils {
    // 用于格式化日期,作为日志文件名的一部分
    private static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String TAG = "DeviceControlUtils";

    public static void powerOnDevice() {

        EventBus.getDefault().post("Try to power on device.\n");

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(new File("/proc/dvic4_poweron"));
            if (null != fos) {
                fos.write("ON".getBytes());
                EventBus.getDefault().post("Power on device finish.\n");
            }
//            EventBus.getDefault().post("powerOnDevice()<<<<<<<<" + formatter.format(new Date()));

        } catch (Exception e) {
//            EventBus.getDefault().post("Get error when power on devices, the error is " + e.getMessage() + "\n");
//            EventBus.getDefault().post("The stack trace is: " + e.getStackTrace() + "\n");

            Log.d(TAG, "Get error when power on devices, the error is " + e.getMessage());
            Log.d(TAG, "The stack trace is: " + e.getStackTrace());
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void powerOffDevice() {

        FileOutputStream fos = null;

//        EventBus.getDefault().post("Try to power off device.\n");
//        EventBus.getDefault().post("powerOffDevice()>>>>>>" + formatter.format(new Date()));

        try {
            fos = new FileOutputStream(new File("/proc/dvic4_poweron"));
            if (null != fos) {
                fos.write("OFF".getBytes());
                EventBus.getDefault().post("Power off device finish.\n");
            }

//            EventBus.getDefault().post("powerOffDevice()<<<<<<<<" + formatter.format(new Date()));

        } catch (Exception e) {
//            EventBus.getDefault().post("Get error when power off devices, the error is " + e.getMessage() + "\n");
//            EventBus.getDefault().post("The stack trace is: " + e.getStackTrace() + "\n");

        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void resetUsbnet() {
        FileOutputStream fos = null;

//        EventBus.getDefault().post("resetUsbnet()>>>>>>" + formatter.format(new Date()));
        try {
            fos = new FileOutputStream(new File("/proc/usbnet_reset"));
            if (null != fos) {
                //先拉高reset，保留高状态500ms，然后拉低reset管脚
//                EventBus.getDefault().post("Set usbnet to HIGH.\n");
                fos.write("HIGH".getBytes());
//                EventBus.getDefault().post("Set usbnet to HIGH" + formatter.format(new Date()));

//                EventBus.getDefault().post("Set usbnet to LOW.\n");
                fos.write("LOW".getBytes());
//                EventBus.getDefault().post("Set usbnet to LOW" + formatter.format(new Date()));
//                EventBus.getDefault().post("Reset usbnet finish.\n");
            }
        } catch (Exception e) {

//            EventBus.getDefault().post("Get error when reset usbnet, the error is " + e.getMessage() + "\n");
//            EventBus.getDefault().post("The stack trace is: " + e.getStackTrace() + "\n");

        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
