package com.huidisen.ep750.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;

import com.huidisen.ep750.bean.EventBusBean;
import com.huidisen.ep750.bean.TaskInfoBean;
import com.huidisen.ep750.db.TaskInfoManager;
import com.huidisen.ep750.db.TaskManager;
import com.huidisen.ep750.model.Callback;
import com.huidisen.ep750.model.TaskModelImpl;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.SPUtils;
import com.huidisen.ep750.view.CustomDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by miaoyichao on 16/7/11.
 */
public class TaskChangeSevice extends Service {
    private String taskId;
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(10);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.taskId = intent.getStringExtra(Commondata.EXTRA_TASK_ID);
        final String action = intent.getStringExtra(Commondata.ACTION);
        if (action.equals(Commondata.NEW_TASK)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    TaskModelImpl.getInstance(TaskChangeSevice.this).GetTaskInfoById(taskId, new Callback.OnGetTaskInfoByIdListener() {
                        @Override
                        public void onSuccess() {
                            EventBus.getDefault().post(new EventBusBean(Commondata.EVENT_UPDATE_TASK, taskId));
                        }

                        @Override
                        public void onError(String error) {
                        }
                    });
                }
            }).start();
        } else if (action.equals(Commondata.CANCEL_TASK)) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    String id = (String) SPUtils.get(TaskChangeSevice.this, Commondata.CURRENT_TASK_ID, "");

                    if (id.equals(taskId)) {
                        // 清空本地存储
                        SPUtils.put(TaskChangeSevice.this, Commondata.CURRENT_TASK_ID, "");
                        SPUtils.put(TaskChangeSevice.this, Commondata.CURRENT_JOB_NUMBER, "");
                    }

                    TaskInfoBean taskInfo = TaskInfoManager.getInstance(TaskChangeSevice.this).search(taskId);

                    if (TaskManager.getInstance(TaskChangeSevice.this).
                            deleteByTaskId(taskInfo.get_id()) > 0)
                        EventBus.getDefault().post(new EventBusBean(Commondata.EVENT_CANCEL_TASK, taskId));
                }
            }).start();

        } else if (action.equals(Commondata.TASK_NODE_CHANGE)) {
            // 收到先查询数据库，看看是不是已经完成



//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    TaskModelImpl.getInstance(TaskChangeSevice.this).GetTaskInfoById(taskId, new Callback.OnGetTaskInfoByIdListener() {
//                        @Override
//                        public void onSuccess() {
//                            EventBus.getDefault().post(new EventBusBean(Commondata.EVENT_UPDATE_TASK_NODE, taskId));
//                        }
//
//                        @Override
//                        public void onError(String error) {
////                            if (error != null) {
////                                new CustomDialog(TaskChangeSevice.this, "获取任务新节点失败").show();
////                            } else {
////                                new CustomDialog(TaskChangeSevice.this, error).show();
////                            }
//                        }
//                    });
//                }
//            }).start();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
