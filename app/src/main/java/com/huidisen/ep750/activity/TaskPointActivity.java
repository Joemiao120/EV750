package com.huidisen.ep750.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huidisen.ep750.R;
import com.huidisen.ep750.bean.EventBusBean;
import com.huidisen.ep750.bean.TaskDefBean;
import com.huidisen.ep750.bean.TaskInfoBean;
import com.huidisen.ep750.bean.TaskNodeBean;
import com.huidisen.ep750.db.TaskInfoManager;
import com.huidisen.ep750.db.TaskManager;
import com.huidisen.ep750.model.Callback;
import com.huidisen.ep750.model.TaskModelImpl;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.DialogUtil;
import com.huidisen.ep750.utils.ObjectSaveUtil;
import com.huidisen.ep750.utils.SPUtils;
import com.huidisen.ep750.utils.SoundPoolUtils;
import com.huidisen.ep750.view.CustomDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by miaoyichao on 16/6/2.
 */
public class TaskPointActivity extends BaseActivity implements View.OnClickListener, Callback.OnReportTaskNodeListener {
    @BindView(R.id.task_node)
    TextView taskNodeShow;
    @BindView(R.id.report_node)
    Button reportNode;

    private String taskId;
    private String userName;
    private String stayNode;
    private TaskInfoBean taskInfo;
    private List<TaskNodeBean> taskNodes;
    private Button taskInfoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.task_point);
        ButterKnife.bind(this);
        setTitle("任务节点");

        initData();

        initView();
        taskInfoDetail = (Button) findViewById(R.id.task_info);
        taskInfoDetail.setOnClickListener(this);
        reportNode.setOnClickListener(this);

    }

    private void initView() {
        taskInfoDetail = (Button) findViewById(R.id.task_info);
        taskInfoDetail.setOnClickListener(this);
        reportNode.setOnClickListener(this);

        reportNode.setFocusable(true);
        reportNode.setFocusableInTouchMode(true);
        reportNode.requestFocus();
    }

    private void initData() {
        taskId = getIntent().getStringExtra(Commondata.TASK_ID);
        userName = getIntent().getStringExtra(Commondata.USER_NAME);

        getNodeInfo();
    }

    private String getNodeName(String node) {
        for (int i = 0; i < taskNodes.size(); i++) {
            if (taskNodes.get(i).code.equals(node))
                return taskNodes.get(i).name;
        }

        return null;
    }

    private String getNextNode(String stayNode) {
        int size = taskNodes.size();

        for (int i = 0; i < size; i++) {
            if (stayNode.equals(taskNodes.get(i).code)) {
                if (i == size - 1) {
                    return Commondata.FINISH;
                }
                return taskNodes.get(i + 1).code;
            }
        }

        return null;
    }

    @Override
    public void onClick(View v) {
        // 先上报人车绑定节点
        switch (v.getId()) {
            case R.id.report_node:
                // 上报节点
                reportNode.setClickable(false);
                DialogUtil.showDialog(this, "正在上报");

                if (stayNode.equals(Commondata.TASKNODE_RCBD)) {
                    TaskModelImpl.getInstance(this).ReportTaskNode(taskId, userName, stayNode,
                            new Callback.OnReportTaskNodeListener() {
                                @Override
                                public void onSuccess() {
                                    reportNode.setClickable(true);
                                    DialogUtil.dismissDialog();

                                    // 更新数据库
                                    taskInfo.setStatusCode(stayNode);
                                    TaskInfoManager.getInstance(TaskPointActivity.this).update(taskInfo);

                                    stayNode = getNextNode(stayNode);
                                    TaskModelImpl.getInstance(TaskPointActivity.this).ReportTaskNode(taskId, userName, stayNode, this);
                                }

                                @Override
                                public void onError(String error) {
                                    errorDialog(error);
                                }
                            });
                } else {
                    TaskModelImpl.getInstance(this).ReportTaskNode(taskId, userName, stayNode, this);
                }
                break;
            case R.id.task_info:
                Intent intent = new Intent(TaskPointActivity.this, TaskInfoDetailActivity.class);
                intent.putExtra(TaskInfoDetailActivity.FLAG, false);
                intent.putExtra(Commondata.TASK_DATA_ID, taskId);
                startActivity(intent);
                break;
        }

    }


    @Override
    public void onSuccess() {
        reportNode.setClickable(true);
        DialogUtil.dismissDialog();


        // 更新数据库
        taskInfo.setStatusCode(stayNode);
        TaskInfoManager.getInstance(this).update(taskInfo);

        // 获取下个节点
        stayNode = getNextNode(stayNode);
        if (stayNode.equals(Commondata.FINISH)) {
            // 删除数据库的任务
            TaskManager.getInstance(this).deleteByTaskId(taskInfo.get_id());

            SoundPoolUtils.playSound(this, 1, SoundPoolUtils.TASK_FINISHED);

            // 清空本地存储
            SPUtils.put(this, Commondata.CURRENT_TASK_ID, "");
            SPUtils.put(this, Commondata.CURRENT_JOB_NUMBER, "");

            // 返回主任务页面
            startActivity(new Intent(this, TaskActivity.class));
            finish();
            return;
        }
        // 更新界面显示
        taskNodeShow.setText(getNodeName(stayNode));
    }

    @Override
    public void onError(String error) {
        errorDialog(error);
    }

    private void errorDialog(String error) {

        reportNode.setClickable(true);
        DialogUtil.dismissDialog();

        if (error != null) {
            new CustomDialog(this, "上报失败").show();
        } else {
            new CustomDialog(this, error).show();
        }

        SoundPoolUtils.playSound(this, 1, SoundPoolUtils.COMMIT_NODE_FAILURE);
    }


    @Override
    public void eventBusHandler(EventBusBean message) {

        eventTaskId = message.getTaskId();

        if (message.getMsg().equals(Commondata.EVENT_CANCEL_TASK)) {
            if (taskId.equals(eventTaskId))
                finish();

        } else if (message.getMsg().equals(Commondata.EVENT_UPDATE_TASK_NODE)) {
            getNodeInfo();
        }
    }

    private void getNodeInfo() {
        taskInfo = TaskInfoManager.getInstance(this).search(taskId);
        if (taskInfo == null) {
            return;
        }

        // 获取节点列表
        TaskDefBean taskDef = (TaskDefBean) ObjectSaveUtil.readObject(TaskPointActivity.this, Commondata.TASK_DEF + taskInfo.getTaskDefType());
        taskNodes = taskDef.taskNodes;

        stayNode = getNextNode(taskInfo.getStatusCode());
        if (stayNode.equals(Commondata.TASKNODE_RCBD)) {
            String node = getNextNode(stayNode);
            String name = getNodeName(node);
            taskNodeShow.setText(name);
        } else {
            taskNodeShow.setText(getNodeName(stayNode));
        }
    }

}
