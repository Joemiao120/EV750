package com.huidisen.ep750.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.huidisen.ep750.receiver.AlarmReceiver;

/**
 * Created by mac on 16/7/11.
 */

public class AlarmUtils {

    public static long DjsTime = 5 * 60 * 1000;
    public static long WwcTime = 20 * 60 * 1000;


//    public static long DjsTime = 1 * 30 * 1000;
//    public static long WwcTime = 2 * 30 * 1000;

    public static void startAlarm(Context context, int requestId) {
        long time = 0;
        if (requestId == Commondata.ALARM_DJS_ID) {
            time = DjsTime;
        } else if (requestId == Commondata.ALARM_WWC_ID) {
            time = WwcTime;
        }

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("flag", requestId);

        PendingIntent pi = PendingIntent.getBroadcast(context, requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 循环查询
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, time, pi);
    }

    public static void cancelAlarm(Context context, int requestId) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        manager.cancel(pi);
    }

}
