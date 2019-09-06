package com.huidisen.ep750.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huidisen.ep750.R;
import com.huidisen.ep750.bean.EventBusBean;
import com.huidisen.ep750.bean.UpdateInfoBean;
import com.huidisen.ep750.utils.ActivityContainer;
import com.huidisen.ep750.utils.AppManageUtil;
import com.huidisen.ep750.utils.DialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by miaoyichao on 16/5/29.
 */
public class BaseActivity extends Activity {
    public String eventTaskId;
    private TextView mTitleTx;
    private View mBack;
    private LinearLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        initTitleBar();

        EventBus.getDefault().register(this);
        ActivityContainer.getInstance().addActivity(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        AppManageUtil.checkNewVersion(this);

    }

    public void initTitleBar() {
        mTitleTx = (TextView) findViewById(R.id.titlebar_title);
    }

    private void initContentView() {
        ViewGroup content = (ViewGroup) findViewById(android.R.id.content);
        content.removeAllViews();
        contentLayout = new LinearLayout(this);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        content.addView(contentLayout);
        LayoutInflater.from(this).inflate(R.layout.title_bar, contentLayout, true);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, contentLayout, true);

    }

    @Override
    public void setContentView(View customContentView) {
        contentLayout.addView(customContentView);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusHandler(EventBusBean message) {
        
    }

//    @Subscribe
//    public void forceUpdate(final UpdateInfoBean updateInfo) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("软件有更新").setMessage("请更新到最新版本后再使用!");
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                DialogUtil.showDialogUnCancellable(BaseActivity.this, "正在下载新版本");
//                AppManageUtil.downloadAndInstallAPK(BaseActivity.this, updateInfo.updateUrl);
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                // TODO exit app
//                dialog.dismiss();
//                if (EventBus.getDefault().isRegistered(BaseActivity.this)) {
//                    EventBus.getDefault().unregister(BaseActivity.this);
//                }
//                ActivityContainer.getInstance().finishAllActivity();
//            }
//        });
//        dialog.show();
//    }


    @Override
    public void setTitle(CharSequence title) {
        mTitleTx.setText(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityContainer.getInstance().removeActivity(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
