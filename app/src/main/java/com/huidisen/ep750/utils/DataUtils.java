package com.huidisen.ep750.utils;

/**
 * Created by miaoyichao on 16/7/6.
 */
public class DataUtils {
    private static String JOB_NUMBER = "";
    private static String TASK_ID = "";

    // 端口状态
    private static boolean IN_PORT_STATUS = false;
    private static boolean OUT_PORT_STATUS = false;


    public static boolean getIN_PORT_STATUS() {
        return IN_PORT_STATUS;
    }

    public static void setIN_PORT_STATUS(boolean IN_PORT_STATUS) {
        DataUtils.IN_PORT_STATUS = IN_PORT_STATUS;
    }

    public static boolean getOUT_PORT_STATUS() {
        return OUT_PORT_STATUS;
    }

    public static void setOUT_PORT_STATUS(boolean OUT_PORT_STATUS) {
        DataUtils.OUT_PORT_STATUS = OUT_PORT_STATUS;
    }


    public static String getJobNumber() {
        return JOB_NUMBER;
    }

    public static void setJobNumber(String jobNumber) {
        JOB_NUMBER = jobNumber;
    }

    public static String getTaskId() {
        return TASK_ID;
    }

    public static void setTaskId(String taskId) {
        TASK_ID = taskId;
    }
}
