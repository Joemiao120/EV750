package com.huidisen.ep750.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.huidisen.ep750.bean.InstantMessage;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.SPUtils;
import com.huidisen.ep750.utils.SoundPoolUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.ws.WebSocket;
import okhttp3.ws.WebSocketCall;
import okhttp3.ws.WebSocketListener;
import okio.Buffer;

/**
 * Created by lovexiaov on 16/6/11.
 */
public class WebSocketService extends Service {

    public static final String FLY_INFO_UPDATE = "flyInfoUpdate";
    private static final String NEW_TASK = "newTask";
    private static final String BASE_STATION = "baseStation";
    private static final String TASK_STATUS = "taskStatus";
    private static final String TASK_CANCEL = "taskCancel";
    private static final String ALARM_INFO = "alarmInfo";
    private static final String wsUrlTemplate = "ws://124.193.225.190:8082/websocket/%s/%s"; // TODO IP modification
//        private static final String wsUrlTemplate = "ws://10.108.1.3:80/websocket/%s/%s"; // TODO IP 1modification
    private boolean isConnect = false;
    private WebSocket webSocket;

    @Override
    public void onCreate() {
        super.onCreate();
        connect();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Logger.d("SocketService Started...");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("WebSocketService has Destroyed.");

        if (webSocket != null) {
            try {
                webSocket.close(4444, "Service has destroyed.");
                isConnect = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void connect() {

        Logger.t("lovexiaov").d("Start websocket connecting...");

        String departId = (String) SPUtils.get(this, Commondata.DEP_ID, "");
        if (departId == null) {
            return;
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        OkHttpClient client = builder.connectTimeout(15, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
                                     .readTimeout(0, TimeUnit.SECONDS).build();

        String wsUrl = String.format(wsUrlTemplate, departId, Commondata.DEVICE_CODE);

        Logger.d("url: %s", wsUrl);

//        Request request = new Request.Builder().url("ws://124.193.225.188:8082/websocket/1/864488012013134").build();
//        Request request = new Request.Builder().url("ws://124.193.225.188:8082/websocket/1/864488012010001").build();
//        Request request = new Request.Builder().url("ws://124.193.225.188:8082/websocket/1/101").build();
        Request request = new Request.Builder().url(wsUrl).build();

        final WebSocketCall wsCall = WebSocketCall.create(client, request);

        wsCall.enqueue(new WebSocketListener() {
            long pingTime = SystemClock.uptimeMillis();
            long pongTime = SystemClock.uptimeMillis();

            @Override
            public void onOpen(final WebSocket webSocket, Response response) {
                isConnect = true;

                WebSocketService.this.webSocket = webSocket;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (isConnect) {
                            if (pingTime - pongTime > 30 * 1000) {
                                Logger.e("Didn't received pong message for 3 times, try to close websocket.");
                                isConnect = false;
                                try {
                                    webSocket.close(4001, "Closed: cannot received pong message.");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
//                                connect();
                                return;
                            }
                            try {
                                pingTime = SystemClock.uptimeMillis();
                                webSocket.sendPing(new Buffer().writeLong(pingTime));
                            } catch (IOException e) {
                                isConnect = false;
                                Logger.e("send ping msg fail: %s", e.getLocalizedMessage());
//                                connect();
                                return;
                            } catch (IllegalStateException e) {
                                Logger.e("IllegalStateException: %s", e.getLocalizedMessage());
                            }
//                            SystemClock.sleep(10 * 1000);
                            try {
                                Thread.sleep(10 * 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }).start();

            }

            @Override
            public void onFailure(IOException e, Response response) {
                Logger.d(
                        "WebSocket onFailure~~\n%s\n%s %s",
                        e.getLocalizedMessage(),
                        response == null ? "" : response.code(),
                        response == null ? "" : response.message()
                );
                isConnect = false;
                e.printStackTrace();
//                SystemClock.sleep(1000);
                if (response == null || response.code() != 4444) {
                    connect();
                }
            }

            @Override
            public void onMessage(ResponseBody responseBody) throws IOException {

                String result = responseBody.string();
                Logger.d(result);

                InstantMessage realTimeMsg = new Gson().fromJson(result, InstantMessage.class);

                switch (realTimeMsg.type) {
                    case NEW_TASK:
                        SoundPoolUtils.playSound(WebSocketService.this, 1, SoundPoolUtils.NEW_TASK);
                        broadcast(Commondata.NEW_TASK, realTimeMsg.dataId);
                        break;
                    case TASK_STATUS:
                        broadcast(Commondata.TASK_NODE_CHANGE, realTimeMsg.dataId);
                        break;
                    case TASK_CANCEL:
                        broadcast(Commondata.CANCEL_TASK, realTimeMsg.dataId);
                        break;
                    case BASE_STATION:
                        SPUtils.put(WebSocketService.this, Commondata.LOCATION_DATA, realTimeMsg.info);
                        break;

                    case FLY_INFO_UPDATE:
                        EventBus.getDefault().post(realTimeMsg);
                        break;
                    case ALARM_INFO:
                        if (realTimeMsg.info.equals("越界")) {
                            SoundPoolUtils.playSound(WebSocketService.this, 1, SoundPoolUtils.BEYOND_THE_MARK);
                        } else if (realTimeMsg.info.equals("超速")) {
                            SoundPoolUtils.playSound(WebSocketService.this, 1, SoundPoolUtils.OVER_SPEED);
                        }
                        break;
                    default:
                        break;
                }


            }

            @Override
            public void onPong(Buffer buffer) {
                pongTime = SystemClock.uptimeMillis();
                Logger.v("Received from client ping: " + buffer.readLong());

            }

            @Override
            public void onClose(int code, String reason) {
                Logger.w("WebSocket has closed,\n code: %s,\n reason: %s", code, reason);
                isConnect = false;
                EventBus.getDefault().post(new ReconnectedEvent());
//                SystemClock.sleep(1000);
                if (code != 4444) {
                    connect();
                }
            }
        });
    }

    private void broadcast(String newTask, String dataId) {
        Intent intent = new Intent();
        intent.setAction(newTask);
        intent.putExtra(Commondata.EXTRA_TASK_ID, dataId);
        sendBroadcast(intent);
    }

    public static class ReconnectedEvent {
        public static final String msg = "Websocket Closed!";
    }

}
