package com.huidisen.ep750.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.huidisen.ep750.R;
import com.huidisen.ep750.bean.FlightInfoBean;
import com.huidisen.ep750.net.NetService;
import com.huidisen.ep750.net.NetTask;
import com.huidisen.ep750.utils.DialogUtil;
import com.huidisen.ep750.view.CustomDialog;
import com.orhanobut.logger.Logger;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lovexiaov on 16/6/3.
 */
public class SearchFlightActivity extends BaseActivity implements View.OnClickListener, Callback<FlightInfoBean> {

    public static final String EXTRA_SEARCH_RESULT = "search_result";
    private static final String template = "[\"incomingFlyNo\",\"departureFlyNo\",\"planedArrival\",\"planedFly\",\"%s\"]";
    private static final String DEFAULT_CONDITION = "机位";
    @BindView(R.id.spinner_type) BetterSpinner spinner;
    @BindView(R.id.et_search_content) EditText contentView;
    @BindView(R.id.bt_search) Button searchBtn;
    String searchType;


    private boolean isRequesting = false;
    private String[] types;
    private LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_search);
        setTitle("航班搜索");

        ButterKnife.bind(this);
        initData();
        initViews();

    }

    private void initData() {

        fieldMap.put("全文", "allColumn");
        fieldMap.put("进港航班号", "incomingFlyNo");
        fieldMap.put("出港航班号", "departureFlyNo");
        fieldMap.put("机号", "planeNo");
        fieldMap.put("机型", "planeType");
        fieldMap.put("机位", "location");
        fieldMap.put("登机口", "boardingGate");
        fieldMap.put("落地时间", "realArrival");
        fieldMap.put("起飞时间", "realFly");
        fieldMap.put("行李转盘", "baggageClaims");

        types = new String[fieldMap.size()];
        int index = 0;
        for (String key : fieldMap.keySet()) {
            types[index] = key;
            index++;
        }


    }

    private void initViews() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, types);
        spinner.setAdapter(adapter);
        spinner.setText(types[0]);

        searchBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_search && !isRequesting) {
            isRequesting = true;
            String content = contentView.getText().toString().toUpperCase();
            searchType = spinner.getText().toString();

            if (TextUtils.isEmpty(content)) {
                new CustomDialog(this, "请输入查询内容").show();
                return;
            }

            DialogUtil.showDialog(this,"正在搜索");

            NetService netService = NetTask.generateRequest();
            Call<FlightInfoBean> searchRequest = netService
                    .searchFlightByCondition(fieldMap.get(searchType), content, searchCondition(searchType));

            searchRequest.enqueue(this);
        }
    }

    private String searchCondition(String type) {
        String result;
        if (type.equals("全文") || type.equals("进港航班号")) {
            result = String.format(template, fieldMap.get(DEFAULT_CONDITION));
        } else {
            result = String.format(template, fieldMap.get(type));
        }
        Logger.d(result);
        return result;
    }

    @Override
    public void onResponse(Call<FlightInfoBean> call, Response<FlightInfoBean> response) {
        DialogUtil.dismissDialog();

        FlightInfoBean flightInfoBean = response.body();
        if (null != flightInfoBean && hasData(flightInfoBean.data)) {
            Logger.d(flightInfoBean.toString());
            Intent intent = new Intent(SearchFlightActivity.this, SearchResultActivity.class);
            if (searchType.equals("全文") || searchType.equals("进港航班号")) {
                intent.putExtra(SearchResultActivity.EXTRA_COLUMN_NAME, DEFAULT_CONDITION);
            } else {
                intent.putExtra(SearchResultActivity.EXTRA_COLUMN_NAME, searchType);
            }
            intent.putExtra(EXTRA_SEARCH_RESULT, flightInfoBean);
            startActivity(intent);
        } else {
            new CustomDialog(this, "无匹配数据").show();

        }
        isRequesting = false;
    }

    private boolean hasData(FlightInfoBean.DataBean data) {
        return data.farLocation.size() > 0 || data.arriveHere.size() > 0 || data.frontFly.size() > 0 || data.frontNoFly
                .size() > 0 || data.flying.size() > 0;
    }

    @Override
    public void onFailure(Call<FlightInfoBean> call, Throwable t) {
        DialogUtil.dismissDialog();
        Logger.e("Request Failed: %s", t.getLocalizedMessage());
        new CustomDialog(this, "无匹配数据").show();
    }
}
