package com.huidisen.ep750.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

import com.huidisen.ep750.R;
import com.huidisen.ep750.adapter.UserNumberAdapter;
import com.huidisen.ep750.bean.TaskDefBean;
import com.huidisen.ep750.bean.TaskInfoBean;
import com.huidisen.ep750.bean.TaskNodeBean;
import com.huidisen.ep750.db.TaskInfoManager;
import com.huidisen.ep750.model.Callback;
import com.huidisen.ep750.model.TaskModelImpl;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.DialogUtil;
import com.huidisen.ep750.utils.ObjectSaveUtil;
import com.huidisen.ep750.utils.SPUtils;
import com.huidisen.ep750.utils.ToastUtils;
import com.huidisen.ep750.view.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by miaoyichao on 16/6/2.
 */
public class InputNumberActivity extends BaseActivity implements View.OnClickListener {
    public static final int NUMBER_COUNT = 3;
    public static final int SJJS_SUCCESS = 0;
    public static final int RCBD_SUCCESS = 1;

    @BindView(R.id.username)
    MultiAutoCompleteTextView userNameEt;
    @BindView(R.id.confirm)
    Button confirm;

    // 任务id
    private String taskId;
    private ArrayList<String> numberList = new ArrayList<String>();
    private String number;
    private String userName;
    private TaskDefBean taskDef;
    private TaskInfoBean taskInfo;
    private String currentNode;
    private List<TaskNodeBean> taskNodes;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SJJS_SUCCESS:
                    taskInfo.setUserName(number);
                    break;
                case RCBD_SUCCESS:
                    taskInfo.setStatusCode(currentNode);
                    TaskInfoManager.getInstance(InputNumberActivity.this).update(taskInfo);

                    enterPointActivity();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.enter_number);
        ButterKnife.bind(this);
        setTitle("输入工号");

        initData();
        initView();
    }

    private void initData() {
        taskId = getIntent().getStringExtra(Commondata.TASK_ID);
        taskInfo = TaskInfoManager.getInstance(this).search(taskId);
        currentNode = taskInfo.getStatusCode();

        taskDef = (TaskDefBean) ObjectSaveUtil.readObject(this, Commondata.TASK_DEF + taskInfo.getTaskDefType());
        taskNodes = taskDef.taskNodes;
    }

    private void initView() {
        confirm.setOnClickListener(this);

        initAutoComplete();
    }

    @Override
    public void onClick(View v) {
        number = userNameEt.getText().toString();

        if (number.equals("")) {
            ToastUtils.showShort(this, "员工号为空");
            return;
        }

        if (number.length() == 4) {
            int ci = number.charAt(0); //获取字符串每个字符ascii
            int change = ci + 16;
            char changeChar = (char) change; //转换

            userName = changeChar + number.substring(1, number.length());
        } else {
            userName = number;
        }

        confirm.setClickable(false);
        DialogUtil.showDialog(this, "正在接受");

        String node = getNextNode(currentNode);

        if (node.equals(Commondata.TASKNODE_SJJS)) {
            reportSjjs(node);
        } else {
            enterPointActivity();
        }
    }

    private void enterPointActivity() {
        Intent intent = new Intent(this, TaskPointActivity.class);
        intent.putExtra(Commondata.TASK_ID, taskId);
        intent.putExtra(Commondata.USER_NAME, userName);
        startActivity(intent);
        finish();
    }

    private void sendMessage(int what) {
        Message message = new Message();
        message.what = what;
        handler.sendMessage(message);
    }

    /**
     * 上报司机接受
     *
     * @param node
     */
    private void reportSjjs(final String node) {
        //上报节点
        TaskModelImpl.getInstance(this).ReportTaskNode(taskId, this.userName, node,
                new Callback.OnReportTaskNodeListener() {
                    @Override
                    public void onSuccess() {
                        // 把员工号和任务id保存到本地，以便上传定位数据时候使用
                        saveArray(number);

                        DialogUtil.dismissDialog();

                        SPUtils.put(InputNumberActivity.this, Commondata.CURRENT_TASK_ID, taskId);
                        SPUtils.put(InputNumberActivity.this, Commondata.CURRENT_JOB_NUMBER, userName);

                        currentNode = node;

                        sendMessage(SJJS_SUCCESS);

                        if (getNextNode(currentNode).equals(Commondata.TASKNODE_RCBD)) {
                            reportRcbd(getNextNode(currentNode));
                        }else {
                            enterPointActivity();
                        }

                    }

                    @Override
                    public void onError(String error) {
                        confirm.setClickable(true);

                        // 清除弹出框
                        DialogUtil.dismissDialog();

                        /**
                         * 上传司机接受失败,即接受任务失败,重新接受
                         */

                        if (error != null) {
                            new CustomDialog(InputNumberActivity.this, error).show();
                        } else {
                            new CustomDialog(InputNumberActivity.this, "接受任务失败").show();
                        }
                    }
                });
    }

    /**
     * 上报人车绑定
     *
     * @param node
     */
    private void reportRcbd(final String node) {
        //上报节点
        TaskModelImpl.getInstance(this).ReportTaskNode(taskId, this.userName, node,
                new Callback.OnReportTaskNodeListener() {
                    @Override
                    public void onSuccess() {
                        currentNode = node;
                        sendMessage(RCBD_SUCCESS);
                    }

                    @Override
                    public void onError(String error) {
                        /**
                         * 失败之后再上报下一个节点的时候默认上报,这里不做处理
                         */
                        enterPointActivity();
                    }
                });
    }


    /**
     * 保存登陆成功的员工号
     */
    public void saveArray(String number) {
        int index = 0;

        if (!numberList.contains(number)) {
            numberList.add(0, number);
        } else {
            index = numberList.indexOf(number);
            numberList.remove(index);
            numberList.add(0, number);
        }


        if (numberList.size() == 4) {
            numberList.remove(NUMBER_COUNT);
        }

        for (int i = 0; i < numberList.size(); i++) {
            SPUtils.put(this, "number_" + i, numberList.get(i));
        }
    }

    private boolean isContain(ArrayList<String> histories, String content) {
        for (int i = 0; i < histories.size(); i++) {
            if (histories.get(i).contains(content)) {
                return true;
            }
        }

        return false;
    }


    /**
     * 获取之前登陆成功的员工号
     */
    private void initAutoComplete() {
        final ArrayList<String> histories = loadArray();

        UserNumberAdapter adapter = new UserNumberAdapter(this, histories);

        userNameEt.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        userNameEt.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.drop_down_background));

        userNameEt.setAdapter(adapter);
        userNameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                MultiAutoCompleteTextView view = (MultiAutoCompleteTextView) v;
                String content = userNameEt.getText().toString();

                if (hasFocus) {
                    view.showDropDown();
                }

                if ((histories.size() == 0) || !isContain(histories, content)) {
                    view.dismissDropDown();
                }


            }
        });

//        userNameEt.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                MultiAutoCompleteTextView view = (MultiAutoCompleteTextView) v;
//
//                // OnKeyListener监听有两个时间,UP和DOWN,所以会执行两次
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    if (view.isPopupShowing()) {
//                        view.dismissDropDown();
//                    } else {
//                        view.showDropDown();
//                    }
//
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    public ArrayList<String> loadArray() {
        numberList.clear();

        for (int i = 0; i < NUMBER_COUNT; i++) {
            String number = (String) SPUtils.get(this, "number_" + i, "");
            if (!(number.equals(""))) {
                numberList.add(number);
            }
        }

        return numberList;
    }


    private String getNextNode(String currentNode) {
        int size = taskNodes.size();

        for (int i = 0; i < size; i++) {
            if (currentNode.equals(taskNodes.get(i).code)) {
                if (i == size - 1) {
                    return Commondata.FINISH;
                }

                return taskNodes.get(i + 1).code;
            }
        }

        return null;
    }


    // userName 输入状态的监听
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        String str = userName.getText().toString().trim();
//        // 防止死循环
//        if (str != null && before == 0 && s.length() != 0) {
////            if (!(s.charAt(0) == '1' || s.charAt(0) == '2')) {
////                userName.setText("");
////            }
//
//
//        }
//
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//
//    }

}
