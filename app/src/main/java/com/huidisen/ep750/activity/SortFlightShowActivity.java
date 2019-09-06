package com.huidisen.ep750.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.huidisen.ep750.R;
import com.huidisen.ep750.adapter.SortFlightAdapter;
import com.huidisen.ep750.utils.SPUtils;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lovexiaov on 16/6/11.
 */
public class SortFlightShowActivity extends BaseActivity {
    public static final String FLIGHT_SORT_SP = "flight_sort";

    public static final String DEFAULT_ORDER = "前方起飞,到达本站,前方未起飞,本站已起飞,远机位";

    @BindView(R.id.rv_flight_sort) RecyclerView flightSortView;
    private final ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_show_sort);

        ButterKnife.bind(this);

        setTitle("航班显示排序");

        String flightSort = (String) SPUtils.get(SortFlightShowActivity.this, FLIGHT_SORT_SP, DEFAULT_ORDER);

        if (flightSort == null) flightSort = DEFAULT_ORDER;

        Collections.addAll(items, flightSort.split(","));

        intiView();

    }

    private void intiView() {
        LinearLayoutManager manager = new LinearLayoutManager(SortFlightShowActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        flightSortView.setLayoutManager(manager);
        flightSortView.setAdapter(new SortFlightAdapter(SortFlightShowActivity.this, items));
    }


    @Override
    protected void onPause() {
        super.onPause();
        String spStr = "";
        for (String item : items) {
            spStr += item + ",";
        }

        SPUtils.put(SortFlightShowActivity.this, FLIGHT_SORT_SP, spStr.substring(0, spStr.length() - 1));

    }
}
