package com.huidisen.ep750.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.huidisen.ep750.R;
import com.huidisen.ep750.adapter.AllFlightsAdapter;
import com.huidisen.ep750.bean.FlightInfoBean;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lovexiaov on 16/6/21.
 */
public class SearchResultActivity extends BaseActivity {

    public static final String EXTRA_COLUMN_NAME = "columnName";

    @BindView(R.id.rv_all_flight) RecyclerView allFlightView;
    @BindView(R.id.tv_column4) TextView column4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("搜索结果");

        setContentView(R.layout.activity_flightinfo);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String columnName = intent.getStringExtra(EXTRA_COLUMN_NAME);
        column4.setText(columnName.substring(0,2));

        FlightInfoBean searchResult = (FlightInfoBean) intent.getSerializableExtra(SearchFlightActivity.EXTRA_SEARCH_RESULT);
        Logger.d("Received %s", searchResult.toString());

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        allFlightView.setLayoutManager(manager);

        allFlightView.setAdapter(new AllFlightsAdapter(searchResult.data, SearchResultActivity.this, columnName));
    }



}
