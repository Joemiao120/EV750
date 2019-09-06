package com.huidisen.ep750.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.huidisen.ep750.R;
import com.huidisen.ep750.bean.EventBusBean;
import com.huidisen.ep750.bean.InfoDetailBean;
import com.huidisen.ep750.bean.TaskBean;
import com.huidisen.ep750.bean.TaskFlyInfoBean;
import com.huidisen.ep750.bean.TaskInfoBean;
import com.huidisen.ep750.bean.TaskInfoNameBean;
import com.huidisen.ep750.bean.TaskNodeBean;
import com.huidisen.ep750.db.TaskManager;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.DateUtils;
import com.huidisen.ep750.utils.SPUtils;
import com.huidisen.ep750.utils.TaskNameUtils;
import com.huidisen.ep750.view.CustomDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by miaoyichao on 16/5/31.
 */
public class TaskInfoDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final String FLAG = "flag";

    private String taskId;
    private TaskBean.DataBean taskData;
    private TaskFlyInfoBean taskFlyInfo;
    private TaskInfoBean taskInfo;
    private List<InfoDetailBean> infoDetails = new ArrayList<InfoDetailBean>();
    private Button acceptTask;
    private Button emptyButton;
    private ListView infoList;
    private TaskInfoDetailAdapter adapter;
    private boolean flag;
    private List<TaskNodeBean> taskNodes;

    //用于显示每列5个Item项。
    int VIEW_COUNT = 4;

    //用于显示页号的索引
    int index = 0;

    private String[] itemNoFlightNames = {

            "任务内容",
            "任务状态",
            "执行车辆车牌号",
            "执行人",
            "发布时间",
    };

    private String[] itemFlightNames = {
            "任务类型",
            "任务说明",
            "发布时间",
            "任务状态",
            "执行车辆车牌号",
            "执行人",
            "属性",
            "任务",
            "进港航班号",
            "出港航班号",
            "航线",
            "机型",
            "机号",
            "前站实飞",
            "进港状态",
            "预到(预计到达时间)",
            "计到(计划到达时间)",
            "实到(实际到达时间)",
            "机位",
            "出港状态",
            "登机口",
            "计飞(计划起飞时间)",
            "预飞(预计起飞时间)",
            "本站实飞(本站实际起飞时间)",
            "进港异常",
            "出港异常",
            "转盘",
            "柜台",
            "滑槽",
            "国内航站楼",
            "国际航站楼",
            "开始值机",
            "开始登机",
            "值机截止",
            "登机截止",
            "催促登机",
            "过站登机",
            "备降站",
            "执行日期",

    };
    private String[] itemValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_info_detail);
        setTitle("任务详情");
    }

    @Override
    protected void onResume() {
        super.onResume();

        initData();
        initView();
    }

    private void initData() {
        taskId = getIntent().getStringExtra(Commondata.TASK_DATA_ID);
        flag = getIntent().getBooleanExtra(FLAG, false);

        if ((boolean) SPUtils.get(this, Commondata.IS_FLIGHT, false)) {
            initFlightData();
        } else {
            initNoFlightData();
        }
    }

    private void initView() {
        infoList = (ListView) findViewById(R.id.detail_list);
        adapter = new TaskInfoDetailAdapter();
        infoList.setAdapter(adapter);

        acceptTask = (Button) findViewById(R.id.accept_task);
        emptyButton = (Button) findViewById(R.id.empty_button);

        acceptTask.setOnClickListener(this);
        if (flag) {
            acceptTask.setVisibility(View.VISIBLE);
            acceptTask.setFocusable(true);
            acceptTask.setFocusableInTouchMode(true);
            acceptTask.requestFocus();

            emptyButton.setVisibility(View.GONE);
        } else {
            emptyButton.setVisibility(View.VISIBLE);
            emptyButton.setFocusable(true);
            emptyButton.setFocusableInTouchMode(true);
            emptyButton.requestFocus();

            acceptTask.setVisibility(View.GONE);
        }

        checkButton();
    }

    @Override
    public void eventBusHandler(EventBusBean message) {

        eventTaskId = message.getTaskId();

        if (message.getMsg().equals(Commondata.EVENT_CANCEL_TASK)) {
            if (taskId.equals(eventTaskId))
                finish();

        }
    }


    private void initNoFlightData() {
        taskData = TaskManager.getInstance(this).queryByTaskId(taskId);
        taskInfo = taskData.getTaskInfo();

        TaskInfoNameBean taskInfoName = TaskNameUtils.getTaskInfoName(this,
                taskInfo.getTaskDefType(), taskInfo.getFlightType(), taskInfo.getStatusCode());

        taskNodes = taskInfoName.getTaskNodes();

        itemValues = new String[itemNoFlightNames.length];

        itemValues[0] = taskInfoName.getTaskName();
        itemValues[1] = taskInfoName.getStatusName();
        itemValues[2] = taskInfo.getCarNumber();
        itemValues[3] = taskInfo.getUserName();
        itemValues[4] = DateUtils.getTimeByTimeMillis(taskInfo.getPubTime());


        for (int i = 0; i < itemNoFlightNames.length; i++) {
            InfoDetailBean info = new InfoDetailBean();
            info.setItemName(itemNoFlightNames[i]);
            info.setItemValue(itemValues[i]);
            infoDetails.add(info);
        }
    }

    private void initFlightData() {
        taskId = getIntent().getStringExtra(Commondata.TASK_DATA_ID);
        taskData = TaskManager.getInstance(this).queryByTaskId(taskId);
        taskInfo = taskData.getTaskInfo();
        taskFlyInfo = taskData.getTaskFlyInfo();
        Log.e("TaskInfoDetailActivity", taskInfo.toString());

        TaskInfoNameBean taskInfoName = TaskNameUtils.getTaskInfoName(this,
                taskInfo.getTaskDefType(), taskInfo.getFlightType(), taskInfo.getStatusCode());

        taskNodes = taskInfoName.getTaskNodes();

        itemValues = new String[itemFlightNames.length];
        itemValues[0] = taskInfoName.getTaskName();

        itemValues[1] = taskFlyInfo.getExplain();
        itemValues[2] = DateUtils.getTimeByTimeMillis(taskInfo.getPubTime());

        itemValues[3] = taskInfoName.getStatusName();

        itemValues[4] = taskInfo.getCarNumber();
        itemValues[5] = taskInfo.getUserName();
        itemValues[6] = taskFlyInfo.getProps();
        itemValues[7] = taskFlyInfo.getTask();
        itemValues[8] = taskFlyInfo.getIncomingFlyNo();
        itemValues[9] = taskFlyInfo.getDepartureFlyNo();


        itemValues[10] = taskFlyInfo.getFlightLine();
        itemValues[11] = taskFlyInfo.getPlaneType();
        itemValues[12] = taskFlyInfo.getPlaneNo();
        itemValues[13] = DateUtils.convertTime(taskFlyInfo.getRealFly());
        itemValues[14] = taskFlyInfo.getIncomingProg();
        itemValues[15] = DateUtils.convertTime(taskFlyInfo.getEstimatedArrival());
        itemValues[16] = DateUtils.convertTime(taskFlyInfo.getPlanedArrival());
        itemValues[17] = DateUtils.convertTime(taskFlyInfo.getRealArrival());
        itemValues[18] = String.valueOf(taskFlyInfo.getLocation());
        itemValues[19] = taskFlyInfo.getDepartureProg();

        itemValues[20] = taskFlyInfo.getBoardingGate();
        itemValues[21] = DateUtils.convertTime(taskFlyInfo.getPlanedFly());
        itemValues[22] = DateUtils.convertTime(taskFlyInfo.getEstimatedFly());
        itemValues[23] = DateUtils.convertTime(taskFlyInfo.getRealFly());
        itemValues[24] = taskFlyInfo.getIncomingExcep();
        itemValues[25] = taskFlyInfo.getDepartureExcep();
        itemValues[26] = taskFlyInfo.getBaggageClaims();
        itemValues[27] = taskFlyInfo.getCounter();
        itemValues[28] = taskFlyInfo.getCoulisse();
        itemValues[29] = taskFlyInfo.getGuoneiTerminal();

        itemValues[30] = taskFlyInfo.getGuojiTerminal();
        itemValues[31] = DateUtils.convertTime(taskFlyInfo.getCheckInStart());
        itemValues[32] = DateUtils.convertTime(taskFlyInfo.getStartBoarding());
        itemValues[33] = DateUtils.convertTime(taskFlyInfo.getCheckInStop());
        itemValues[34] = DateUtils.convertTime(taskFlyInfo.getBoardingEnd());
        itemValues[35] = DateUtils.convertTime(taskFlyInfo.getUrgingBoarding());
        itemValues[36] = DateUtils.convertTime(taskFlyInfo.getBoardingStation());
        itemValues[37] = taskFlyInfo.getAlternate();
        itemValues[38] = DateUtils.formatDate(taskFlyInfo.getExecutionDate());


        for (int i = 0; i < itemFlightNames.length; i++) {
            InfoDetailBean info = new InfoDetailBean();
            info.setItemName(itemFlightNames[i]);
            info.setItemValue(itemValues[i]);
            infoDetails.add(info);
        }
    }

    @Override
    public void onClick(View v) {
        String userName = taskInfo.getJobNumber();
        String currentTaskID = (String) SPUtils.get(this, Commondata.CURRENT_TASK_ID, "");
        // 在任务页面选择的position
        int position = (int) SPUtils.get(this, Commondata.CURRENT_TASK_POSITION, -1);

        // 当前员工号和当前执行任务的id
        if (userName == null && currentTaskID.equals("")) {
            doNormalAction(position);

        } else {
            TaskBean.DataBean taskData = TaskManager.getInstance(this).queryByTaskId(currentTaskID);

            if (taskData == null){
                doNormalAction(position);
                return;
            }

            TaskInfoBean taskInfo = taskData.getTaskInfo();
            String taskDef = taskInfo.getTaskDefType();
            String status = taskInfo.getStatusCode();

            if (currentTaskID.equals(taskId)) {
                Intent intent = new Intent(this, TaskPointActivity.class);
                intent.putExtra(Commondata.TASK_ID, taskId);
                intent.putExtra(Commondata.USER_NAME, userName);
                startActivity(intent);

                finish();
            } else if ((!(taskDef.equals("cheketi"))) && isAcceptNewTask(status)) {
                showSelectDialog();
            } else {
                new CustomDialog(this, "您当前有未完成\n的任务,请先完成当前任务").show();
            }
        }

    }

    private void doNormalAction(int position){
        if (position > 0) {
            showSelectDialog();
        } else {
            enterInputNumberActivity();
        }
    }

    private boolean isAcceptNewTask(String status) {
        String next = getNextNode("rcbd");
        boolean isFlight = (boolean) SPUtils.get(this, Commondata.IS_FLIGHT, false);


        if (isFlight) {
            if (status.equals("sjjs") || status.equals("rcbd") || status.equals(next)) {
                return true;
            }
        } else {
            if (status.equals("sjjs") || status.equals("rcbd")) {
                return true;
            }
        }


        return false;
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


    /**
     * 重启另一个任务的dialog
     */
    private void showSelectDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注意");
        builder.setMessage("您有任务未完成，点击确定会\n放弃该任务并开始新的任务");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterInputNumberActivity();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                        TaskInfoDetailActivity.this.finish();
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();

        /**
         * show方法必须放到getButton之前，不然会返回null
         */
        dialog.show();

        // 设置默认聚焦到取消上
        Button button = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        button.setFocusable(true);
        button.setFocusableInTouchMode(true);
        button.requestFocus();

    }

    private void enterInputNumberActivity() {
        Intent intent = new Intent(this, InputNumberActivity.class);
        intent.putExtra(Commondata.TASK_ID, taskId);
        startActivity(intent);

        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                // 下一页
                if (down) {
                    index++;
                    //刷新ListView里面的数值。
                    adapter.notifyDataSetChanged();
                    //检查Button是否可用。
                    checkButton();
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                // 上一页
                if (up) {
                    index--;
                    adapter.notifyDataSetChanged();
                    checkButton();
                }
                return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean up = true;
    private boolean down = true;

    public void checkButton() {
        //索引值小于等于0，表示不能向前翻页了，以经到了第一页了。         //将向前翻页的按钮设为不可用。
        if (index <= 0) {
            up = false;
            down = true;
        }
        //值的长度减去前几页的长度，剩下的就是这一页的长度，如果这一页的长度比View_Count小，表示这是最后的一页了，后面在没有了。
        //将向后翻页的按钮设为不可用。
        else if (infoDetails.size() - index * VIEW_COUNT <= VIEW_COUNT) {
            down = false;
            up = true;
        } else {
            up = true;
            down = true;
        }

    }

    public class TaskInfoDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            //return data.length
            //ori表示到目前为止的前几页的总共的个数。
            int ori = VIEW_COUNT * index;

            //值的总个数-前几页的个数就是这一页要显示的个数，如果比默认的值小，说明这是最后一页，只需显示这么多就可以了
            if (infoDetails.size() - ori < VIEW_COUNT) {
                return infoDetails.size() - ori;
            }
            //如果比默认的值还要大，说明一页显示不完，还要用换一页显示，这一页用默认的值显示满就可以了。
            else {
                return VIEW_COUNT;
            }
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView itemName;
            TextView itemValue;

            convertView = LayoutInflater.from(TaskInfoDetailActivity.this).inflate(R.layout.info_detail_item, null);
            itemName = (TextView) convertView.findViewById(R.id.item_name);
            itemValue = (TextView) convertView.findViewById(R.id.item_value);

            itemName.setText(infoDetails.get(position + index * VIEW_COUNT).getItemName());
            itemValue.setText(infoDetails.get(position + index * VIEW_COUNT).getItemValue());
            return convertView;
        }
    }


}
