package com.huidisen.ep750.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.huidisen.ep750.activity.SettingActivity;
import com.huidisen.ep750.bean.GISData;
import com.huidisen.ep750.bean.ResultStatusBean;
import com.huidisen.ep750.net.NetService;
import com.huidisen.ep750.net.NetTask;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.DataUtils;
import com.huidisen.ep750.utils.NMEAParserUtils;
import com.huidisen.ep750.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eason Lu on 2014/4/15.
 */
public class BackgroundLoadDataService extends Service {
    private static final String TAG = "BackgroundLoadData";
    private static final String TAG2 = "READ Data";
    private static final String TAG3 = "WRITE Data";
    private static boolean SEND_START_DATA = false;
    private static boolean SEND_INPUT_PORT_DATA = false;
    private static boolean SEND_OUTPUT_PORT_DATA = false;
    private static boolean START_INPUT_THREAD = false;
    private static boolean START_OUTPUT_THREAD = false;
    private static boolean INPUT_THREAD_CONNECTED = true;
    private static boolean OUTPUT_THREAD_CONNECTED = true;

    private static  ExecutorService SCHEDULER;
    private static String BUSY = "1";
    private static String FREE = "0";

    public IBinder onBind(Intent intent) {
        return null;
    }

    /* 服务器端口 */
    private final int SERVER_OUT_HOST_PORT = 8888;
    private final int SERVER_INPUT_HOST_PORT = 8088;

    private Runnable socketThread;
    private Runnable server2Thread;
    private Socket socket1;
    private Socket socket2;

    private OutputStreamWorker outputStreamWorker;
    boolean disconnectFlag = false;

    @Override
    public void onStart(Intent intent, int startId) {
        Log.i(TAG, "start socket");

        SCHEDULER.submit(socketThread);
        SCHEDULER.submit(server2Thread);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (SCHEDULER != null){
            SCHEDULER.shutdown();
            SCHEDULER = null;
        }
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        SCHEDULER = Executors.newCachedThreadPool();

        if (!SEND_START_DATA) {
            sendStartData();
        }
        socketThread = new Runnable() {

            public void run() {
                try {
                    if (START_INPUT_THREAD) {
                        return;
                    }
                    ServerSocket server1 = new ServerSocket(SERVER_INPUT_HOST_PORT);
                    START_INPUT_THREAD = true;
                    while (true) {
                        if (INPUT_THREAD_CONNECTED) {
                            EventBus.getDefault().post("try to accept client1");
                            socket1 = server1.accept();
                            EventBus.getDefault().post("accept client1" + "socket1 = " + socket1);
                            SCHEDULER.submit(new InputStreamWorker(socket1));
                            INPUT_THREAD_CONNECTED = false;
                        }
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Get error when run input thread: " + e.toString());
                    EventBus.getDefault().post("Get error when run input thread: " + e.toString());
                    START_INPUT_THREAD = false;
                }
            }
        };
        server2Thread = new Runnable() {

            public void run() {
                try {
                    if (START_OUTPUT_THREAD) {
                        return;
                    }
                    ServerSocket server2 = new ServerSocket(SERVER_OUT_HOST_PORT);

                    START_OUTPUT_THREAD = true;
                    while (true) {
                        if (OUTPUT_THREAD_CONNECTED) {
                            Log.i(TAG, "try to accept client2");
                            socket2 = server2.accept();
                            Log.i(TAG, "accept client2");

                            outputStreamWorker = new OutputStreamWorker(socket2);
                            SCHEDULER.submit(outputStreamWorker);
                            OUTPUT_THREAD_CONNECTED = false;
                        }
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Get error when run output thread: " + e.getMessage());
                    EventBus.getDefault().post("Get error when run output thread: " + e.toString());
                    START_OUTPUT_THREAD = false;
                }
            }
        };
    }

    private void sendStartData() {
        SCHEDULER.submit(new Runnable() {
            @Override
            public void run() {
                uploadExtraData("750_STARTED=TRUE");

                SEND_START_DATA = true;
            }
        });
    }

    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public String writeDataToFile(String data) {
        StringBuffer sb = new StringBuffer();
        sb.append(data);

        try {
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + ".log";
            FileOutputStream fos = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Commondata.CRASH_HANDLER_DIR;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // 存在就追加,不存在就新建
                if (new File(path + fileName).exists()) {
                    fos = new FileOutputStream(path + fileName, true);
                } else {
                    fos = new FileOutputStream(path + fileName);
                }

                fos.write(sb.toString().getBytes());
                fos.write("#############################\n".getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }

        return null;
    }


    private void uploadGISData(final GISData data) {

        SCHEDULER.submit(new Runnable() {
            @Override
            public void run() {
//                writeDataToFile(data.toString() + "\n");

                NetService netService = NetTask.generateRequest();
                retrofit2.Call<ResultStatusBean> uploadGISDataCall = netService.uploadLocationData(data.getLongitude(),
                        data.getLatitude(), data.getAlt(), data.getSpeed(), data.getAccuracy(),
                        data.getStarCount(), data.getGpsStatus(),
                        data.getTimeStamp(), data.getVehicleId(),
                        data.getJobNumber(), data.getTaskId());
                uploadGISDataCall.enqueue(new Callback<ResultStatusBean>() {
                    @Override
                    public void onResponse(Call<ResultStatusBean> call, Response<ResultStatusBean> response) {
//                        EventBus.getDefault().post(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<ResultStatusBean> call, Throwable t) {
//                        EventBus.getDefault().post("失败");
                    }
                });
            }
        });
    }

    class InputStreamWorker implements Runnable {

        private Socket socket = null;

        public InputStreamWorker(Socket socket) {
            this.socket = socket;
            Log.i("zsg1", "socket connected");
            EventBus.getDefault().post("socket connected");
        }

        @Override
        public void run() {
            BufferedReader in = null;
            try {
                int count = 0;
                int allSize = 0;
                Log.i(TAG, "server1 accepted");

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if (in == null) {
                    return;
                }
                DataUtils.setIN_PORT_STATUS(true);

                // 把8888断开，并不发送显示连接的消息
                if (outputStreamWorker != null) {
                    disconnectFlag = false;
                    outputStreamWorker.disconnectOutput();
                }

                // 通知设置界面显示端口状态
                sendMessageToActivity(SettingActivity.IN_CONNECTED_ACTION);

                if (!SEND_INPUT_PORT_DATA) {
                    uploadExtraData("InputPort=CONNECTED");
                    SEND_INPUT_PORT_DATA = true;
                }

                while (true) {
                    char[] buffer = new char[32768];

                    socket.setSoTimeout(10 * 1000);

                    if ((count = in.read(buffer)) != -1) {
                        String formServerStr = "";
                        allSize = allSize + count;
                        if (count > 1024) {
                            Log.d(TAG2, "max size is : " + count);
                        }
                        formServerStr = getInfoBuff(buffer, count);
                        Log.d("zsg", formServerStr.toString());

                        Map<String, String> result = NMEAParserUtils.parser(formServerStr);
                        GISData data = generateGISData(result);

                        if (data != null) {
                            Log.i("zsg", data.toString());
                            uploadGISData(data);
                        } else {
                            Log.i("zsg", "...");
                        }

                    } else {
                        Log.d("zsg", "count =" + count);

                        INPUT_THREAD_CONNECTED = true;
                        socket1 = null;
                        in = null;
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                EventBus.getDefault().post("Get exception when read to socket 8088: " + ex.toString()
                        + "\n");

            } finally {
                try {
                    Log.e("finally", "in");
                    DataUtils.setIN_PORT_STATUS(false);
                    sendMessageToActivity(SettingActivity.IN_DISCONNECTED_ACTION);

                    INPUT_THREAD_CONNECTED = true;
                    if (!DataUtils.getOUT_PORT_STATUS()) {
                        uploadExtraData("InputPort=DISCONNECTED");
                        SEND_INPUT_PORT_DATA = false;
                        SEND_OUTPUT_PORT_DATA = false;
                    }
                    if (socket != null) {
                        socket.close();
                        socket = null;
                    }

                    // 把8888断开，并不发送显示连接的消息
                    if (outputStreamWorker != null) {

                        disconnectFlag = true;
                        outputStreamWorker.disconnectOutput();
                    }

                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
                Log.i("zsg", "Server1 closed");
            }
        }
    }


    class OutputStreamWorker implements Runnable {

        private Socket socket = null;
        private byte[] bytes;

        public OutputStreamWorker(Socket socket) {
            this.socket = socket;
        }

        public void disconnectOutput() {
            try {
                Log.e("finally", "out");
                DataUtils.setOUT_PORT_STATUS(false);
                sendMessageToActivity(SettingActivity.OUT_DISCONNECTED_ACTION);
                OUTPUT_THREAD_CONNECTED = true;
                if (!DataUtils.getIN_PORT_STATUS()) {
                    uploadExtraData("OutputPort=DISCONNECTED");
                    SEND_INPUT_PORT_DATA = false;
                    SEND_OUTPUT_PORT_DATA = false;
                }
                if (socket != null) {
                    socket.close();
                    socket = null;
                }

            } catch (Exception e) {
                Log.i(TAG, e.toString());
            }
        }

        @Override
        public void run() {
            OutputStream out = null;
            try {
                Log.i(TAG, "server2 accepted");
                out = socket.getOutputStream();
                if (out == null) {
                    return;
                }
                DataUtils.setOUT_PORT_STATUS(true);

                if (DataUtils.getIN_PORT_STATUS() && !disconnectFlag) {
                    sendMessageToActivity(SettingActivity.OUT_CONNECTED_ACTION);
                }

                if (!SEND_OUTPUT_PORT_DATA) {
                    uploadExtraData("OutputPort=CONNECTED");
                    SEND_OUTPUT_PORT_DATA = true;
                }

                while (true) {
                    try {
                        Thread.sleep(800);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    // Log.i(TAG3, "write……");
                    String content = null;
                    try {
                        // 接受要往硬件写入的内容
                        content = (String) SPUtils.get(BackgroundLoadDataService.this, Commondata.LOCATION_DATA, "");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Log.d(TAG3, "catch exception when get location data. The exception is: " + ex.getMessage());

                    }

                    if (content != null) {
                        bytes = Base64.decode(content, Base64.DEFAULT);
                        try {
                            out.write(bytes);
                            out.flush();
                        } catch (IOException e) {
                            break;
                        }
                        // }
                        Log.i(TAG3, "the byte data length is: " + bytes.length);

                    } else {
                        Log.d(TAG3, "content is null..." + content);
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                EventBus.getDefault().post("Get exception when write to socket 8888: " + ex.getMessage()
                        + "\n");

            } finally {
                try {
                    Log.e("finally", "out");
                    DataUtils.setOUT_PORT_STATUS(false);
                    sendMessageToActivity(SettingActivity.OUT_DISCONNECTED_ACTION);
                    OUTPUT_THREAD_CONNECTED = true;
                    if (!DataUtils.getIN_PORT_STATUS()) {
                        uploadExtraData("OutputPort=DISCONNECTED");
                        SEND_INPUT_PORT_DATA = false;
                        SEND_OUTPUT_PORT_DATA = false;
                    }
                    if (socket != null) {
                        socket.close();
                        socket = null;
                    }

                } catch (Exception e) {
                    Log.i(TAG, e.toString());
                }
                Log.i(TAG, "Server2 closed");
            }
        }

    }

    private GISData generateGISData(Map<String, String> result) {
        GISData data = new GISData();
        if (null == result) {
            return null;
        } else {
            String taskId = (String) SPUtils.get(BackgroundLoadDataService.this, Commondata.CURRENT_TASK_ID, "");
            String number = (String) SPUtils.get(BackgroundLoadDataService.this, Commondata.CURRENT_JOB_NUMBER, "");

            String time = result.get("timestamp");

            data.setTimeStamp(utc2Local(time));
            data.setLongitude(Double.parseDouble(result.get("lon")));
            data.setLatitude(Double.parseDouble(result.get("lat")));

            data.setSpeed(Float.parseFloat(result.get("speed")));
            data.setAccuracy(Float.parseFloat(result.get("accuracy")));

            data.setStarCount(Integer.parseInt(result.get("starCount")));
            data.setGpsStatus(Integer.parseInt(result.get("gpsStatus")));

            data.setVehicleId(Commondata.DEVICE_CODE);
            if (TextUtils.isEmpty(taskId))
                data.setTaskId(0);
            else
                data.setTaskId(Integer.valueOf(taskId).intValue());
            data.setJobNumber(number);

            return data;
        }
    }

    public static long utc2Local(String utcTime) {
        // 格式一定要写好,区分大小写
        SimpleDateFormat utcFormater = new SimpleDateFormat("ddMMyyhhmmss");
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return gpsUTCDate.getTime();
    }

    /**
     * 上传拓展数据，把extraInfo放到说明之中，告诉后台开始接受
     * 端口连接和断开
     *
     * @param extraInfo
     */
    private void uploadExtraData(String extraInfo) {

        try {
            GISData data = buildExtraData(extraInfo);
            if (data != null) {
                Log.i(TAG, "update extra data to gps server: " + extraInfo);
                // 上传数据
//                lbsManager.updateLBSData(data);
                Log.i(TAG, "update extra data completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GISData buildExtraData(String extraInfo) {
        GISData data = new GISData();
//        data.setDeviceId(Commondata.DEVICE_CODE);
//        data.setLongitude("0.0");
//        data.setLatitude("0.0");
//        data.setTaskId(DataUtils.getTaskId());
//        data.setDriverId(checkNullString(DataUtils.getJobNumber()));
//        // data.setTaskId("5555");
//        // data.setDriverId(result.get("fix"));
////        data.setStatus(getDeviceStatus());
////        data.setDepartment(checkNullString(getDepartId()));
////        data.setDescription(buildString(null, extraInfo));
//        data.setCreateTime(new Date());
        return data;
    }

    private String buildString(Map<String, String> result, String extraInfo) {
        StringBuffer buffer = new StringBuffer();
        if (result != null && !result.isEmpty()) {
            buffer.append("star=");
            buffer.append(result.get("star"));
            buffer.append(", fix=");
            buffer.append(result.get("fix"));
        }

        buffer.append(", Port8088=");
        buffer.append(DataUtils.getIN_PORT_STATUS());
        buffer.append(", Port8888=");
        buffer.append(DataUtils.getOUT_PORT_STATUS());
        if (extraInfo != null && !extraInfo.isEmpty()) {
            buffer.append(", ");
            buffer.append(extraInfo);
        }

        return buffer.toString();
    }

    private String checkNullString(String strForCheck) {
        if (strForCheck == null || strForCheck.trim().isEmpty()) {
            return "null";
        }
        return strForCheck;
    }

    private String getInfoBuff(char[] buff, int count) {
        char[] temp = new char[count];
        for (int i = 0; i < count; i++) {
            temp[i] = buff[i];
        }
        return new String(temp);
    }

    private void sendMessageToActivity(String action) {
        EventBus.getDefault().post(action);
        EventBus.getDefault().post("action = " + action + "\n");
    }
}
