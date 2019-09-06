package com.huidisen.ep750.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mac on 16/6/21.
 */

public class DateUtils {

    public static String getTimeByTimeLongMillis(long timeMillis) {
        StringBuilder sb = new StringBuilder();

        if (timeMillis == 0) {
            return "";
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(timeMillis);

        sb.append(d.substring(11, 13));
        sb.append(d.substring(14, 16));

        return sb.toString();
    }


    public static String getTimeByTimeMillis(String timeMillis) {
        if (timeMillis == null) {
            return "";
        }

        Long time = Long.valueOf(timeMillis);

        StringBuilder sb = new StringBuilder();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(time);

        sb.append(d.substring(11, 13));
        sb.append(d.substring(14, 16));

        return sb.toString();
    }

    public static String convertTime(long time) {
        // 返回时间格式: 1830 表示 18:30
        try {
            return time == 0 ? "" : String.valueOf(time).substring(8, 12);
        } catch (Exception e) {
            return "";
        }
    }

    public static String convertTime(String time) {
        // 返回时间格式: 1830 表示 18:30
        try {
            return time == null ? "" : time.substring(8, 12);
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatDate(long time) {
        try {
            String date = String.valueOf(time);
            // 返回日期格式: 2016-06-16
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;// 2012年10月03日 23:41:31
    }

    public static String getDateEN() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;// 2012-10-03 23:41:31
    }

}
