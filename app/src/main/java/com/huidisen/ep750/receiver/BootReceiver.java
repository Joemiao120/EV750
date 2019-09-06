package com.huidisen.ep750.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.huidisen.ep750.activity.SplashActivity;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.SPUtils;

/**
 * Created by miaoyichao on 16/6/2.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent arg1) {

        SPUtils.put(context, Commondata.NUMBER_BOOT_COMPLETED, 1);

        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
