package com.huidisen.ep750.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;

import com.huidisen.ep750.R;
import com.huidisen.ep750.bean.FlightInfoDetailBean;
import com.huidisen.ep750.bean.TaskFlyInfoBean;
import com.huidisen.ep750.net.NetService;
import com.huidisen.ep750.net.NetTask;
import com.huidisen.ep750.utils.DateUtils;
import com.huidisen.ep750.utils.DialogUtil;
import com.orhanobut.logger.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.huidisen.ep750.utils.DateUtils.convertTime;

/**
 * Created by lovexiaov on 16/6/3.
 */
public class FlightInfoDetailActivity extends BaseActivity implements Callback<FlightInfoDetailBean> {
    public static final String DATA_ID = "dataId";

    @BindView(R.id.rv_flight_info_detail) RecyclerView detailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);
        setTitle("航班详细信息");

        ButterKnife.bind(this);

        String dataId = getIntent().getStringExtra(DATA_ID);
        Logger.d(dataId);

        DialogUtil.showDialog(this, "正在获取航班详情");
        NetService netService = NetTask.generateRequest();
        Call<FlightInfoDetailBean> flightDetailCall = netService.requestFlightDetail(dataId);
        Logger.d("url is: %s", flightDetailCall.request().url().toString());
        flightDetailCall.enqueue(this);

    }

    @Override
    public void onResponse(Call<FlightInfoDetailBean> call, Response<FlightInfoDetailBean> response) {
        Logger.d(response.body().toString());

        DialogUtil.dismissDialog();

        FlightInfoDetailBean detailBean = response.body();
        TaskFlyInfoBean data = detailBean.data;
        List<InfoKV> infos = new ArrayList<>();

//        属性：国内|国内，地区|国际，地区|混合，国内，
        infos.add(new InfoKV("属性", data.getProps()));
//        任务：正|正，正班，公务，备降、熟练、播种……
        infos.add(new InfoKV("任务", data.getTask()));
//        进港航班号：FM9295+进港共享航班：CX5740
        infos.add(new InfoKV("进港航班号", data.getIncomingFlyNo()));
//        出港航班号：FM9296+出港共享航班：CX5741
        infos.add(new InfoKV("出港航班号", data.getDepartureFlyNo()));
//        航线：杭州-郑州-杭州
        infos.add(new InfoKV("航线", data.getFlightLine()));
//        机型：B738
        infos.add(new InfoKV("机型", data.getPlaneType()));
//        机号：B5460
        infos.add(new InfoKV("机号", data.getPlaneNo()));
//        前站实飞：1202
        infos.add(new InfoKV("前站实飞", convertTime(data.getPreRealFly())));
//        进港状态：到达，null（单出），前方起飞
        infos.add(new InfoKV("进港状态", data.getIncomingProg()));
//        预到（预计到达时间）：1300
        infos.add(new InfoKV("预到", convertTime(data.getEstimatedArrival())));
//        计到（计划到达时间）：1300
        infos.add(new InfoKV("计到", convertTime(data.getPlanedArrival())));
//        实到（实际到达时间）：1334
        infos.add(new InfoKV("实到", convertTime(data.getRealArrival())));
//        机位：237
        infos.add(new InfoKV("机位", data.getLocation()));
//        出港状态：催促登机，正在值机，值机截止，null
        infos.add(new InfoKV("出港状态", data.getDepartureProg()));
//        登机口：237
        infos.add(new InfoKV("登机口", data.getBoardingGate()));
//        计飞（计划起飞时间）：1345
        infos.add(new InfoKV("计飞", convertTime(data.getPlanedFly())));
//        预飞（预计起飞时间）：
        infos.add(new InfoKV("预飞", convertTime(data.getEstimatedFly())));
//        本站实飞（本站实际起飞时间）：
        infos.add(new InfoKV("本站实飞", convertTime(data.getRealFly())));
//        进港异常：延误（异常原因：公司计划，流量控制）
        infos.add(new InfoKV("进港异常", data.getIncomingExcep()));
//        出港异常：延误（异常原因：流量控制，对方机场天气）
        infos.add(new InfoKV("出港异常", data.getDepartureExcep()));
//        转盘：3
        infos.add(new InfoKV("转盘", data.getBaggageClaims()));
//        柜台：F18-F19
        infos.add(new InfoKV("柜台", data.getCounter()));
//        滑槽：S03
        infos.add(new InfoKV("滑槽", data.getCoulisse()));
//        国内航站楼：T2
        infos.add(new InfoKV("国内航站楼", data.getGuoneiTerminal()));
//        国际航站楼：T2
        infos.add(new InfoKV("国际航站楼", data.getGuojiTerminal()));
//        开始值机：1400
        infos.add(new InfoKV("开始值机", convertTime(data.getCheckInStart())));
//        值机截止：1440
        infos.add(new InfoKV("值机截止", convertTime(data.getCheckInStop())));
//        开始登机
        infos.add(new InfoKV("开始登机", convertTime(data.getStartBoarding())));
//        登机截止：
        infos.add(new InfoKV("登机截止", convertTime(data.getBoardingEnd())));
//        催促登机：1500
        infos.add(new InfoKV("催促登机", convertTime(data.getUrgingBoarding())));
//        过站登机：1500
        infos.add(new InfoKV("过站登机", convertTime(data.getBoardingStation())));
//        备降站：
        infos.add(new InfoKV("备降站", data.getAlternate()));
//        执行日期：2016-6-15
        infos.add(new InfoKV("执行日期", DateUtils.formatDate(data.getExecutionDate())));

        detailView.setLayoutManager(new LinearLayoutManager(FlightInfoDetailActivity.this,
                                                            LinearLayoutManager.VERTICAL,
                                                            false
        ));

        detailView.setAdapter(new CommonAdapter<InfoKV>(FlightInfoDetailActivity.this,
                                                        R.layout.item_flight_detail,
                                                        infos
        ) {
            @Override
            public void convert(ViewHolder holder, InfoKV infoKV, int position) {
                holder.setText(R.id.tv_flight_detail_key, infoKV.key);
                holder.setText(R.id.tv_flight_detail_value, infoKV.value);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            detailView.smoothScrollBy(0, 150);
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            detailView.smoothScrollBy(0, -150);
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFailure(Call<FlightInfoDetailBean> call, Throwable t) {
        // TODO handle error
        Logger.e("Error: %s", t.getLocalizedMessage());
    }

    private class InfoKV {
        String key;
        String value;

        public InfoKV(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
