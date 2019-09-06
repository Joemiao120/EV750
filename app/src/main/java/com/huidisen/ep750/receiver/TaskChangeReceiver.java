package com.huidisen.ep750.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.huidisen.ep750.service.TaskChangeSevice;
import com.huidisen.ep750.utils.AlarmUtils;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.SoundPoolUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by mac on 16/6/28.
 */

public class TaskChangeReceiver extends BroadcastReceiver {
    private String taskId;
    private Context context;
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(10);

    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context = context;
        this.taskId = intent.getStringExtra(Commondata.EXTRA_TASK_ID);
        Log.e("TaskChangeReceiver", "taskId = " + taskId);

        if (intent.getAction().equals(Commondata.NEW_TASK)) {
            SoundPoolUtils.playSound(context,1,SoundPoolUtils.NEW_TASK);

            // 重置定时器
            AlarmUtils.cancelAlarm(context, Commondata.ALARM_DJS_ID);
            AlarmUtils.startAlarm(context, Commondata.ALARM_DJS_ID);

            startTaskService(Commondata.NEW_TASK);

        } else if (intent.getAction().equals(Commondata.TASK_NODE_CHANGE)) {
            startTaskService(Commondata.TASK_NODE_CHANGE);

        } else if (intent.getAction().equals(Commondata.CANCEL_TASK)) {

            startTaskService(Commondata.CANCEL_TASK);

        }
    }

    private void startTaskService(String action) {
        Intent intent = new Intent(context, TaskChangeSevice.class);
        intent.putExtra(Commondata.EXTRA_TASK_ID, taskId);
        intent.putExtra(Commondata.ACTION, action);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.stopService(intent);
        context.startService(intent);
    }
}
