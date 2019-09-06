package com.huidisen.ep750.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.huidisen.ep750.R;
import com.huidisen.ep750.adapter.UserNumberAdapter;
import com.huidisen.ep750.bean.CarCheckItem;
import com.huidisen.ep750.model.Callback;
import com.huidisen.ep750.model.WorkerModelImpl;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.DialogUtil;
import com.huidisen.ep750.utils.SPUtils;
import com.huidisen.ep750.utils.SoundPoolUtils;
import com.huidisen.ep750.utils.ToastUtils;
import com.huidisen.ep750.view.CustomDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by miaoyichao on 16/6/25.
 */
public class CheckCarActivity extends BaseActivity implements View.OnClickListener, Callback.OnUploadInfoListener {
    public static final int NUMBER_COUNT = 3;

    private List<CarCheckItem> list;
    private MyAdapter adapter;
    private ListView lv;
    private MultiAutoCompleteTextView jobNumber;
    private Button upload;
    private Map map = new HashMap();
    private RadioGroup[] radioGroups;
    private RadioButton[] radioNormals;
    private RadioButton[] radioErrors;
    private ArrayList<String> numberList = new ArrayList<String>();
    private String number;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check_car);
        setTitle("车辆检查");

        addData();
        initView();
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.check_list);
        lv.setFocusableInTouchMode(true);

        jobNumber = (MultiAutoCompleteTextView) findViewById(R.id.et_job_number);
        upload = (Button) findViewById(R.id.upload);

        jobNumber.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.drop_down_background));
        initAutoComplete();

        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        number = jobNumber.getText().toString();
        if (TextUtils.isEmpty(number)) {
            ToastUtils.showShort(this, "请输入员工号");
            return;
        }

        if (number.length() == 4) {
            int ci = number.charAt(0); //获取字符串每个字符ascii
            int change = ci + 16;
            char changeChar = (char) change; //转换

            userName = changeChar + number.substring(1, number.length());
        }else {
            userName = number;
        }

        upload.setClickable(false);
        DialogUtil.showDialog(this, "正在上报");

        // 参数map
        map.clear();
        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i).getKey(), list.get(i).i + "");
        }

        Log.e("CheckCarActivity", map.toString());

        WorkerModelImpl.getInstance(this).CarCheck(userName, Commondata.DEVICE_CODE, map, this);

    }

    @Override
    public void onSuccess() {
        // 上报成功
        DialogUtil.dismissDialog();
        ToastUtils.showShort(this, "上传成功");

        // 保存员工号
        saveArray(number);

        SoundPoolUtils.playSound(this, 1, SoundPoolUtils.CHECK_CAR_SUCCESS);
        finish();
    }

    @Override
    public void onError(String error) {
        upload.setClickable(true);

        DialogUtil.dismissDialog();

        if (error != null) {
            new CustomDialog(this, error).show();
        } else {
            new CustomDialog(this, "上报失败").show();
        }

        SoundPoolUtils.playSound(this, 1, SoundPoolUtils.CHECK_CAR_FAILURE);
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {

            ViewHolder holder = null;
            if (view == null) {
                view = View.inflate(getApplicationContext(), R.layout.check_car_item, null);
                holder = new ViewHolder();

                holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
                holder.rg_select = (RadioGroup) view.findViewById(R.id.rg_select);
                holder.rb_normal = (RadioButton) view.findViewById(R.id.rb_normal);
                holder.rb_error = (RadioButton) view.findViewById(R.id.rb_error);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            radioGroups[position] = holder.rg_select;
            radioNormals[position] = holder.rb_normal;
            radioErrors[position] = holder.rb_error;

            holder.rg_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                    Log.e("CheckCarActivity", "position" + position);
                    list.get(position).i = radioGroup.getChildAt(0).getId() == checkedId ? 0 : 1;
                }
            });


            holder.tv_title.setText(list.get(position).getValue()[0]);
            holder.rb_normal.setText(list.get(position).getValue()[1]);
            holder.rb_error.setText(list.get(position).getValue()[2]);
            holder.rg_select.check(holder.rg_select.getChildAt(list.get(position).i).getId());

            return view;
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        int position = lv.getSelectedItemPosition();

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (!radioNormals[position].isChecked())
                    radioNormals[position].setChecked(true);
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (!radioErrors[position].isChecked())
                    radioErrors[position].setChecked(true);
                return true;
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//                if (jobNumber.isFocused()){
//
//                }
//                return true;
            default:
                break;

        }
        return super.onKeyDown(keyCode, event);
    }

    public static class ViewHolder {
        TextView tv_title;
        RadioGroup rg_select;
        RadioButton rb_normal, rb_error;
    }


    /**
     * 添加数据
     */
    private void addData() {
        list = new ArrayList<>();
        list.add(new CarCheckItem("carInOut", new String[]{"出车/入库", "出车", "入库"}, 0));
        list.add(new CarCheckItem("fuelOil", new String[]{"燃油", "正常", "异常"}, 0));
        list.add(new CarCheckItem("engineOil", new String[]{"机油", "正常", "异常"}, 0));
        list.add(new CarCheckItem("water", new String[]{"水", "正常", "异常"}, 0));
        list.add(new CarCheckItem("electricity", new String[]{"电", "正常", "异常"}, 0));
        list.add(new CarCheckItem("tirePressure", new String[]{"轮胎气压", "正常", "异常"}, 0));
        list.add(new CarCheckItem("hydraulicOil", new String[]{"液压油", "正常", "异常"}, 0));
        list.add(new CarCheckItem("lighting", new String[]{"灯光", "正常", "异常"}, 0));
        list.add(new CarCheckItem("brake", new String[]{"刹车", "正常", "异常"}, 0));
        list.add(new CarCheckItem("isnormal", new String[]{"有无异常", "正常", "异常"}, 0));
        list.add(new CarCheckItem("generator", new String[]{"发电机组", "正常", "异常"}, 0));
        list.add(new CarCheckItem("airSupply", new String[]{"供气系统", "正常", "异常"}, 0));
        list.add(new CarCheckItem("ketiSystem", new String[]{"客梯液压系统", "正常", "异常"}, 0));

        radioGroups = new RadioGroup[list.size()];
        radioNormals = new RadioButton[list.size()];
        radioErrors = new RadioButton[list.size()];

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
            SPUtils.put(this, "jobNumber_" + i, numberList.get(i));
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

        jobNumber.setAdapter(adapter);
        jobNumber.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        jobNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                MultiAutoCompleteTextView view = (MultiAutoCompleteTextView) v;
                String content = jobNumber.getText().toString();
                if (hasFocus && histories.size() > 0) {
                    view.showDropDown();
                }

                if ((histories.size() == 0) || !isContain(histories, content)) {
                    view.dismissDropDown();
                }
            }
        });

    }

    public ArrayList<String> loadArray() {
        numberList.clear();

        for (int i = 0; i < NUMBER_COUNT; i++) {
            String number = (String) SPUtils.get(this, "jobNumber_" + i, "");
            if (!(number.equals(""))) {
                numberList.add(number);
            }
        }

        return numberList;
    }
}
