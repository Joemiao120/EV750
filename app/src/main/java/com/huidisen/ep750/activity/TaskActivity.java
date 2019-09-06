package com.huidisen.ep750.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.huidisen.ep750.R;
import com.huidisen.ep750.adapter.NonflightTaskInfoAdapter;
import com.huidisen.ep750.adapter.TaskInfoAdapter;
import com.huidisen.ep750.bean.EventBusBean;
import com.huidisen.ep750.bean.InfoShowBean;
import com.huidisen.ep750.bean.TaskBean;
import com.huidisen.ep750.bean.TaskFlyInfoBean;
import com.huidisen.ep750.bean.TaskInfoBean;
import com.huidisen.ep750.bean.TaskInfoNameBean;
import com.huidisen.ep750.db.TaskManager;
import com.huidisen.ep750.model.Callback;
import com.huidisen.ep750.model.TaskModelImpl;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.DateUtils;
import com.huidisen.ep750.utils.DialogUtil;
import com.huidisen.ep750.utils.SPUtils;
import com.huidisen.ep750.utils.TaskNameUtils;
import com.huidisen.ep750.view.CustomDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miaoyichao on 16/5/29.
 */
public class TaskActivity extends BaseActivity implements AdapterView.OnItemClickListener, Callback.OnTaskListener, View.OnClickListener {
    private ListView taskListView;
    private List<TaskInfoBean> taskInfoList;
    private BaseAdapter mAdapter;
    private List<TaskBean.DataBean> taskDatas;
    private List<InfoShowBean> infoShows = new ArrayList<InfoShowBean>();
    private Button reflesh;
    private boolean isFinish = false;
    private boolean isFilght;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_info);
        setTitle("任务");

        isFilght = (boolean) SPUtils.get(TaskActivity.this, Commondata.IS_FLIGHT, false);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();

        taskRefresh();
    }

    private void taskRefresh() {
        DialogUtil.showDialog(this, "获取任务中");

        TaskModelImpl.getInstance(this).getTask(this);
    }

    private void initView() {
        taskListView = (ListView) findViewById(R.id.date_list);
        taskListView.setOnItemClickListener(this);
        taskListView.setEmptyView(findViewById(R.id.empty_view));

        reflesh = (Button) findViewById(R.id.reflesh);
        reflesh.setOnClickListener(this);

        if (isFilght) {
            ((ViewStub) findViewById(R.id.title_layout)).inflate();
            mAdapter = new TaskInfoAdapter(this);
        } else {
            ((ViewStub) findViewById(R.id.title_layout_noFlight)).inflate();
            mAdapter = new NonflightTaskInfoAdapter(this);
        }

        taskListView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String taskId = infoShows.get(position).getTaskId();
        String jobNumber = infoShows.get(position).getJobNumber();

        SPUtils.put(this, Commondata.CURRENT_TASK_POSITION, position);

        if (jobNumber == null) {
            Intent intent = new Intent(this, TaskInfoDetailActivity.class);
            // 数据库的id
            intent.putExtra(Commondata.TASK_DATA_ID, taskId);
            intent.putExtra(TaskInfoDetailActivity.FLAG, true);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, TaskPointActivity.class);
            // 数据库的id
            intent.putExtra(Commondata.TASK_ID, taskId);
            intent.putExtra(Commondata.USER_NAME, jobNumber);
            startActivity(intent);
        }

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                if (!isFinish) {
//                    reflesh.setFocusable(true);
//                    reflesh.setFocusableInTouchMode(true);
//                    reflesh.requestFocus();
//                    isFinish = true;
//                } else {
//                    finish();
//                }
//                return true;
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//                isFinish = false;
////                }
//            default:
//                isFinish = false;
//                break;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void eventBusHandler(EventBusBean message) {
        dataShow();
    }

    @Override
    public void onSuccess() {
        dataShow();

        reflesh.setClickable(true);
        DialogUtil.dismissDialog();
    }

    private void dataShow() {
        infoShows.clear();

        if (isFilght) {
            getFlightTaskInfo();
            ((TaskInfoAdapter) mAdapter).setTaskDatas(infoShows);
        } else {
            getNonFlightTaskInfo();
            ((NonflightTaskInfoAdapter) mAdapter).setTaskDatas(infoShows);
        }
        mAdapter.notifyDataSetChanged();

        setListViewFocus();
    }

    private void setListViewFocus() {
        taskListView.clearFocus();
        taskListView.requestFocus();
        taskListView.setSelection(0);
    }

    /**
     * 获取任务失败
     */

    @Override
    public void onError(String error) {
        reflesh.setClickable(true);

        DialogUtil.dismissDialog();

        if (error == null){
            new CustomDialog(this, "获取失败").show();
        } else {
            new CustomDialog(this, error).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reflesh:
                reflesh.setClickable(false);

                taskRefresh();
                break;
        }
    }

    public void getFlightTaskInfo() {
        taskDatas = TaskManager.getInstance(this).obtainAllInfo();
        if (taskDatas != null) {

            for (TaskBean.DataBean taskData : taskDatas) {

                InfoShowBean infoShow = new InfoShowBean();
                TaskInfoBean taskInfo = new TaskInfoBean();
                TaskFlyInfoBean taskFlyInfo = new TaskFlyInfoBean();
                taskInfo = taskData.getTaskInfo();
                taskFlyInfo = taskData.getTaskFlyInfo();


                if (taskFlyInfo != null) {
                    if (!TextUtils.isEmpty(taskFlyInfo.getLocation())) {
                        infoShow.setRealArrival(String.valueOf(taskFlyInfo.getLocation()));
                    }
                    if (taskFlyInfo.getIncomingFlyNo() != null)
                        infoShow.setIncomingFlyNo(taskFlyInfo.getIncomingFlyNo());
                    if (taskFlyInfo.getRealArrival() != 0)
                        infoShow.setPlaneType(DateUtils.getTimeByTimeLongMillis(taskFlyInfo.getPlanedArrival()));
                }


                if (taskInfo != null) {

                    TaskInfoNameBean taskInfoName = TaskNameUtils.getTaskInfoName(this,
                            taskInfo.getTaskDefType(), taskInfo.getFlightType(), taskInfo.getStatusCode());

                    // 节点
                    infoShow.setStatusCode(taskInfoName.getStatusName());
                    // 任务类型
                    infoShow.setTaskDefType(taskInfoName.getTaskName());

                    if (!TextUtils.isEmpty(taskInfo.getId()))
                        infoShow.setTaskId(taskInfo.getId());
                    if (!TextUtils.isEmpty(taskInfo.getJobNumber()))
                        infoShow.setJobNumber(taskInfo.getJobNumber());

                }

                infoShows.add(infoShow);
            }
        }
    }


    public void getNonFlightTaskInfo() {
        taskDatas = TaskManager.getInstance(this).obtainAllInfo();
        if (taskDatas != null) {
            for (TaskBean.DataBean taskData : taskDatas) {

                InfoShowBean infoShow = new InfoShowBean();
                TaskInfoBean taskInfo = new TaskInfoBean();
                taskInfo = taskData.getTaskInfo();


                if (taskInfo != null) {

                    TaskInfoNameBean taskInfoName = TaskNameUtils.getTaskInfoName(this,
                            taskInfo.getTaskDefType(), taskInfo.getFlightType(), taskInfo.getStatusCode());

                    // 节点
                    infoShow.setStatusCode(taskInfoName.getStatusName());
                    // 任务类型
                    infoShow.setTaskDefType(taskInfoName.getTaskName());

                    if (!TextUtils.isEmpty(taskInfo.getId()))
                        infoShow.setTaskId(taskInfo.getId());
                    if (!TextUtils.isEmpty(taskInfo.getJobNumber()))
                        infoShow.setJobNumber(taskInfo.getJobNumber());

                    infoShows.add(infoShow);
                }
            }


        }
    }
}