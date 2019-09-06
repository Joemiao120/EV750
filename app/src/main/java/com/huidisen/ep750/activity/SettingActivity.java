package com.huidisen.ep750.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.huidisen.ep750.R;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.DataUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by miaoyichao on 16/6/26.
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.version_name)
    TextView versionName;
    @BindView(R.id.devices_id)
    TextView devicesId;

//    @BindView(R.id.data)
//    TextView data;
    private StringBuilder stringBuilder;

    public static String IN_CONNECTED_ACTION = "IN_CONNECTED_ACTION";
    public static String IN_DISCONNECTED_ACTION = "IN_DISCONNECTED_ACTION";
    public static String OUT_CONNECTED_ACTION = "OUT_CONNECTED_ACTION";
    public static String OUT_DISCONNECTED_ACTION = "OUT_DISCONNECTED_ACTION";

    @BindView(R.id.tv_in_status)
    TextView tvInStatus;
    @BindView(R.id.tv_out_status)
    TextView tvOutStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        setTitle("设置");
        stringBuilder = new StringBuilder();

        if (DataUtils.getIN_PORT_STATUS()) {
            tvInStatus.setText("连接正常");
        }

        if (DataUtils.getOUT_PORT_STATUS()) {
            tvOutStatus.setText("连接正常");
        }

        devicesId.setText(Commondata.DEVICE_CODE);
        versionName.setText(getVersionName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateStatus(String action) {
        if (SettingActivity.IN_CONNECTED_ACTION.equals(action)) {
            if (DataUtils.getIN_PORT_STATUS()) {
                tvInStatus.setText("连接正常");
            }
        } else if (SettingActivity.OUT_CONNECTED_ACTION.equals(action)) {
            if (DataUtils.getOUT_PORT_STATUS()) {
                tvOutStatus.setText("连接正常");
            }

        } else if (SettingActivity.IN_DISCONNECTED_ACTION.equals(action)) {
            if (!DataUtils.getIN_PORT_STATUS()) {
                tvInStatus.setText("连接断开");
            }
        } else if (SettingActivity.OUT_DISCONNECTED_ACTION.equals(action)) {
            if (!DataUtils.getOUT_PORT_STATUS()) {
                tvOutStatus.setText("连接断开");
            }
        }
//        else {
//            stringBuilder.append(action);
//            data.setText(stringBuilder.toString());
//        }
    }

    private String getVersionName() {

        try {
            // 获取packagemanager的实例
            PackageManager packageManager = getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = null;
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return String.valueOf(packInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
