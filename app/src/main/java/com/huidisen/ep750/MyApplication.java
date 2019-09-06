package com.huidisen.ep750;

import android.app.Activity;
import android.app.Application;

import com.huidisen.ep750.utils.CrashHandler;
import com.huidisen.ep750.utils.LogcatUtil;
import com.huidisen.ep750.utils.SoundPoolUtils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by miaoyichao on 16/5/31.
 */
public class MyApplication extends Application {
    private static MyApplication application;
    private List<Activity> activitys;

    @Override
    public void onCreate() {
        application = this;

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        Logger.init()
                .logLevel(LogLevel.FULL)
                .methodCount(2);
        LogcatUtil.getInstance(this).start();

        SoundPoolUtils.initSound(this);


        super.onCreate();
    }


    //在Activity的OnCreate方法中调用,添加Activity实例
    public void addActivity(Activity act) {
        if (activitys == null) {
            activitys = new ArrayList<Activity>();
        }
        activitys.add(act);
    }

    //退出程序时调用，调用所有Activity的finish方法
    public void finishAll() {
        for (Activity act : activitys) {
            if (!act.isFinishing()) {
                act.finish();
            }
        }
        activitys = null;
    }
}
