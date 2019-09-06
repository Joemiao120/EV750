package com.huidisen.ep750.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.huidisen.ep750.R;
import com.huidisen.ep750.model.Callback;
import com.huidisen.ep750.model.TaskModelImpl;
import com.huidisen.ep750.service.WebSocketService;
import com.huidisen.ep750.utils.AlarmUtils;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.DialogUtil;
import com.huidisen.ep750.utils.SPUtils;
import com.huidisen.ep750.view.CustomDialog;

public class MainActivity extends BaseActivity implements View.OnClickListener, Callback.OnApplyTaskListener {
    private Button taskButton, flightInfoButton, checkCarButton,
            settingButton, useShowButton, applyTaskButton;

    Intent wsServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("航班调度");

        initService();
        initView();
    }

    private void initService() {
        // 推送
        wsServiceIntent = new Intent(MainActivity.this, WebSocketService.class);
        startService(wsServiceIntent);

        // 开始定时任务
        AlarmUtils.startAlarm(this, Commondata.ALARM_DJS_ID);
        AlarmUtils.startAlarm(this, Commondata.ALARM_WWC_ID);

        SPUtils.put(this, Commondata.NUMBER_LOGIN, 1);
        SPUtils.put(this, Commondata.NUMBER_BOOT_COMPLETED, 2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(wsServiceIntent);

        // 开始定时任务
        AlarmUtils.cancelAlarm(this, Commondata.ALARM_DJS_ID);
        AlarmUtils.cancelAlarm(this, Commondata.ALARM_WWC_ID);
    }

    private void initView() {
        taskButton = (Button) findViewById(R.id.task);
        flightInfoButton = (Button) findViewById(R.id.flight_info);
        checkCarButton = (Button) findViewById(R.id.check_car);
        settingButton = (Button) findViewById(R.id.setting);
        useShowButton = (Button) findViewById(R.id.use_show);
        applyTaskButton = (Button) findViewById(R.id.apply_task);

        // 非航班任务时显示
        if (!(boolean) SPUtils.get(this, Commondata.IS_FLIGHT, false))
            applyTaskButton.setVisibility(View.VISIBLE);

        taskButton.setOnClickListener(this);
        flightInfoButton.setOnClickListener(this);
        checkCarButton.setOnClickListener(this);
        settingButton.setOnClickListener(this);
        useShowButton.setOnClickListener(this);
        applyTaskButton.setOnClickListener(this);

        // Button聚焦代码
        taskButton.setFocusable(true);
        taskButton.setFocusableInTouchMode(true);
        taskButton.requestFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task:
                startActivity(new Intent(this, TaskActivity.class));
//                new CustomDialog(this,"nihaoa").show();
                break;
            case R.id.flight_info:
                Intent flightInfo = new Intent(MainActivity.this, FlightInfoActivity.class);
                startActivity(flightInfo);
                break;
            case R.id.check_car:
                startActivity(new Intent(this, CheckCarActivity.class));
                break;
            case R.id.setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.use_show:
                startActivity(new Intent(this, ExplainActivity.class));
                break;
            case R.id.apply_task:
//                startActivity(new Intent(this, ApplyTaskActivity.class));
                String carDef = (String) SPUtils.get(this, Commondata.CAR_DEF, "");
                Log.e("MainActivity", carDef);

                TaskModelImpl.getInstance(this).ApplyNoFlightTask(
                        carDef, this);

                DialogUtil.showDialog(this, "正在申请任务");
                applyTaskButton.setClickable(false);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void onSuccess() {
        DialogUtil.dismissDialog();

        applyTaskButton.setClickable(true);
        startActivity(new Intent(this, TaskActivity.class));
    }


    @Override
    public void onError(String error) {
        // 申请任务失败

        DialogUtil.dismissDialog();
        applyTaskButton.setClickable(true);

        if (error != null) {
            new CustomDialog(this, error).show();
        } else {
            new CustomDialog(this, "申请失败").show();
        }

    }
}
