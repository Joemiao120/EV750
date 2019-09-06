package com.huidisen.ep750.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.huidisen.ep750.db.TaskManager;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.SoundPoolUtils;

/**
 * Created by mac on 16/7/11.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("flag", 0);
        if (id == 0)
            return;

        if (id == Commondata.ALARM_DJS_ID && (TaskManager.getInstance(context).obtainDjsTask() == 1)) {
            //播放待接收
            SoundPoolUtils.playSound(context, 1, SoundPoolUtils.NEW_TASK);

        } else if (id == Commondata.ALARM_WWC_ID && (TaskManager.getInstance(context).obtainWwcTask() == 1)) {
            // 播放未完成
            Log.e("AlarmReceiver", "未完成");

        }
    }
}
