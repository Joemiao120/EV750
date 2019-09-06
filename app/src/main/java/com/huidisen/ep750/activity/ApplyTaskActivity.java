package com.huidisen.ep750.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.huidisen.ep750.R;
import com.huidisen.ep750.bean.VehicleInfoBean;
import com.huidisen.ep750.model.Callback;
import com.huidisen.ep750.model.TaskModelImpl;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.ObjectSaveUtil;

import java.util.List;


/**
 * Created by miaoyichao on 16/6/26.
 */
public class ApplyTaskActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, Callback.OnApplyTaskListener {
    private List<String> taskTypes;
    private Spinner taskTaskSpinner;
    private Button applyTask;
    private ArrayAdapter<String> arr_adapter;
    private String selectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_task);

        setTitle("申请任务");

        initData();
        initView();
    }

    private void initView() {
        taskTaskSpinner = (Spinner) findViewById(R.id.task_type_spinner);
        applyTask = (Button) findViewById(R.id.apply_task);
        applyTask.setOnClickListener(this);

        //适配器
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, taskTypes);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        taskTaskSpinner.setAdapter(arr_adapter);
        taskTaskSpinner.setOnItemSelectedListener(this);
    }

    private void initData() {
        VehicleInfoBean.DataBean vehicleInfo = (VehicleInfoBean.DataBean) ObjectSaveUtil.readObject(this, Commondata.CAR_INFO);
        Log.e("ApplyTaskActivity", vehicleInfo.toString());
        taskTypes = vehicleInfo.getExcuteTaskType();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedType = taskTypes.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        //申请任务
        TaskModelImpl.getInstance(this).ApplyNoFlightTask(selectedType, this);
    }

    @Override
    public void onSuccess() {
        startActivity(new Intent(this, TaskActivity.class));
    }

    @Override
    public void onError(String error) {
        // 根据error信息显示弹出框
    }
}
