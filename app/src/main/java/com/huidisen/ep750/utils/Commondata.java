package com.huidisen.ep750.utils;

import android.os.Build;

/**
 * Created by miaoyichao on 16/6/2.
 */
public class Commondata {

    public static String DEVICE_CODE = "103";
//    public static String DEVICE_CODE = Build.SERIAL;

        public static final String HTTP_IP = "http://124.193.225.190:8082/";
//    public static final String HTTP_IP = "http://192.168.3.222:8083/";
//    public static final String HTTP_IP = "http://192.168.8.101:8083/";
//    public static final String HTTP_IP = "http://10.108.1.3:80/";

    public static final String GET_ALL_TASK = HTTP_IP + "task/getAllTaskByDeviceId.do";
    public static final String REPORT_NODE = HTTP_IP + "task/reportNode.do";
    public static final String DOWNLOAD_FILE = HTTP_IP + "mobileUser/taskConfig.do";
    public static final String SUCCESS = "success";
    public static final String CRASH_HANDLER_DIR = "/sdcard/huidesen/";

    /**
     * 判断是否是航班任务
     */
    public static boolean isFlight;

    /**
     *
     */
    public static final String TASK_DATA_ID = "task_data_id";
    public static final String TASK_ID = "task_id";
    public static final String USER_NAME = "user_name";
    public static final String IS_FLIGHT = "is_flight";
    public static final String TASK_DEF = "task_def";
    public static final String CAR_INFO = "car_info";
    public static final String FINISH = "finish";
    public static final String CAR_DEF = "car_def";
    public static final String DEP_ID = "dep_id";
    public static final String LOCATION_DATA = "location_data";
    public static final String NUMBER_LOGIN = "number_login";
    public static final String NUMBER_BOOT_COMPLETED = "number_boot_completed";

    public static final String IS_FIRST = "location_data";
    public static final String MD5 = "md5";
    public static final String CURRENT_TASK_ID = "current_task_id";
    public static final String CURRENT_JOB_NUMBER = "current_job_number";
    public static final String CURRENT_TASK_NODE = "current_task_node";
    public static final String CURRENT_TASK_POSITION = "current_task_position";


    public static final int ALARM_DJS_ID = 1;
    public static final int ALARM_WWC_ID = 2;

    /**
     * 广播类型
     */
    public static final String NEW_TASK = "com.ep750.newTask";
    public static final String TASK_NODE_CHANGE = "com.ep750.taskNode_change";
    public static final String CANCEL_TASK = "com.ep750.cancelTask";

    public static final String EXTRA_TASK_ID = "task_id";
    public static final String ALARM = "com.ep750.alarm";
    public static final String ACTION = "action";


    /**
     * Eventbus 消息类型
     */
    public static final String EVENT_UPDATE_TASK = "updata_task";
    public static final String EVENT_UPDATE_TASK_NODE = "updata_task_node";
    public static final String EVENT_CANCEL_TASK = "cancel_task";

    // 所有的任务类型
    // 摆渡车
    public static final String TASKTYPE_BAIDU = "baidu";
    // 全顺车
    public static final String TASKTYPE_QUANSHUN = "quanshun";
    // 残疾人保障车
    public static final String TASKTYPE_CANJIRENBAOZHANG = "canjirenbaozhang";
    // 搭客梯
    public static final String TASKTYPE_DAKETI = "daketi";
    // 撤客梯
    public static final String TASKTYPE_CHEKETI = "cheketi";
    // 引导
    public static final String TASKTYPE_YINDAO = "yindao";
    // 牵引
    public static final String TASKTYPE_QIANYIN = "qianyin";
    // 电源
    public static final String TASKTYPE_DIANYUAN = "dianyuan";
    // 气源
    public static final String TASKTYPE_QIYUAN = "qiyuan";
    // 空调
    public static final String TASKTYPE_KONGTIAO = "kongtiao";
    // 清水
    public static final String TASKTYPE_QINGSHUI = "qingshui";
    // 污水
    public static final String TASKTYPE_WUSHUI = "wushui";
    // 垃圾
    public static final String TASKTYPE_LAJI = "laji";
    // 除冰
    public static final String TASKTYPE_CHUBING = "chubing";
    // 进港地勤
    public static final String TASKTYPE_DIQIN_IN = "diqin_in";
    // 出港地勤
    public static final String TASKTYPE_DIQIN_OUT = "diqin_out";
    // 进港登机桥
    public static final String TASKTYPE_DENGJIQIAO_IN = "dengjiqiao_in";
    // 出港登机桥
    public static final String TASKTYPE_DENGJIQIAO_OUT = "dengjiqiao_out";
    // 行李牵引
    public static final String TASKTYPE_XINGLIQIANYIN = "xingliqianyin";
    // 传送带
    public static final String TASKTYPE_CHUANSONGDAI = "chuansongdai";
    // 装卸队
    public static final String TASKTYPE_ZHUANGXIEDUI = "zhuangxiedui";

    // 所有的任务节点
    // 待接受
    public static final String TASKNODE_DJS = "djs";
    // 司机接受
    public static final String TASKNODE_SJJS = "sjjs";
    // 人车绑定
    public static final String TASKNODE_RCBD = "rcbd";
    // 接受
    public static final String TASKNODE_JS = "js";
    // 到位
    public static final String TASKNODE_DW = "dw";
    // 开始
    public static final String TASKNODE_KS = "ks";
    // 完成
    public static final String TASKNODE_WC = "wc";
    // kjwc 靠接完成
    public static final String TASKNODE_KJWC = "kjwc";
    // cmgb 舱门关闭
    public static final String TASKNODE_CMGB = "cmgb";
    // djwc 牵引对接完成
    public static final String TASKNODE_DJWC = "djwc";
    // kcmgb 客舱门关闭
    public static final String TASKNODE_KCMGB = "kcmgb";
    // hcmkq 货舱门开启
    public static final String TASKNODE_HCMKQ = "hcmkq";
    // zxhwc 装/卸货完成
    public static final String TASKNODE_ZXHWC = "zxhwc";
    // cldwc 撤轮档完成
    public static final String TASKNODE_CLDWC = "cldwc";
    // 任务完成
    public static final String TASKNODE_RWWC = "rwwc";


}
