package com.huidisen.ep750.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.huidisen.ep750.R;
import com.huidisen.ep750.adapter.AllFlightsAdapter;
import com.huidisen.ep750.bean.FlightInfoBean;
import com.huidisen.ep750.bean.InstantMessage;
import com.huidisen.ep750.net.NetService;
import com.huidisen.ep750.net.NetTask;
import com.huidisen.ep750.service.WebSocketService;
import com.huidisen.ep750.utils.DialogUtil;
import com.huidisen.ep750.utils.SPUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by lovexiaov on 16/5/29.
 */
public class FlightInfoActivity extends BaseActivity {

    private final ArrayList<String> items = new ArrayList<>();
    Call<FlightInfoBean> flightInfoCall;
    @BindView(R.id.rv_all_flight) RecyclerView allFlightView;
    @BindView(R.id.tv_loading) TextView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flightinfo);
        setTitle("航班信息");
        ButterKnife.bind(this);

        loadingView.setVisibility(View.INVISIBLE);

        String order = (String) SPUtils.get(FlightInfoActivity.this,
                                            SortFlightShowActivity.FLIGHT_SORT_SP,
                                            SortFlightShowActivity.DEFAULT_ORDER
        );

        Collections.addAll(items, order.split(","));

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        DialogUtil.showDialog(FlightInfoActivity.this, "加载航班信息");
        requestData();

    }

    private void requestData() {
        NetService netService = NetTask.generateRequest();
        flightInfoCall = netService
                .requestAllFlights("[\"incomingFlyNo\",\"departureFlyNo\",\"planedArrival\",\"planedFly\",\"location\"]");
        flightInfoCall.enqueue(new FlightInfoBean.CallBack(FlightInfoActivity.this));
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        if (flightInfoCall != null) {
            flightInfoCall.cancel();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                showMenu();
                return true;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * show flight info to the screen
     *
     * @param flightInfoBean
     * @author lovexiaov
     */
    private void showData(FlightInfoBean flightInfoBean) {
        FlightInfoBean.DataBean data = flightInfoBean.data;

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        allFlightView.setLayoutManager(manager);
        allFlightView.setAdapter(new AllFlightsAdapter(data, FlightInfoActivity.this, null));

    }

    private void showMenu() {
        final String[] menuContents = {"排序", "搜索"};
        final int[] menuIcons = {R.drawable.sort2, R.drawable.flight_search};

        final AlertDialog.Builder builder = new AlertDialog.Builder(FlightInfoActivity.this);
        final AlertDialog dialog = builder.create();

        View view = View.inflate(this, R.layout.menu_dialog, null);

        ListView menus = (ListView) view.findViewById(R.id.lv_menu);
        menus.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return menuContents.length;
            }

            @Override
            public Object getItem(int position) {
                return menuContents[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(FlightInfoActivity.this, R.layout.menu_item, null);

                }
                TextView itemText = (TextView) convertView.findViewById(R.id.tv_menu_item);
                ImageView itemIcon = (ImageView) convertView.findViewById(R.id.iv_menu_item);

                itemText.setText(menuContents[position]);
                itemIcon.setBackgroundResource(menuIcons[position]);
                return convertView;
            }
        });

        menus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent sortIntent = new Intent(FlightInfoActivity.this, SortFlightShowActivity.class);
                        startActivity(sortIntent);
                        dialog.dismiss();
                        break;
                    case 1:
                        Intent searchIntent = new Intent(FlightInfoActivity.this, SearchFlightActivity.class);
                        startActivity(searchIntent);
                        dialog.dismiss();
                        break;
                    default:
                        break;

                }
            }
        });

        dialog.setView(view);
        dialog.show();
    }

    @Subscribe
    public void onInstantMessage(InstantMessage msg) {
        if (!WebSocketService.FLY_INFO_UPDATE.equals(msg.type)) {
            return;
        }
        requestData();
    }

    @Subscribe
    public void onRequestError(FlightInfoBean.Error error) {
        Logger.d("Request Error: %s", error.message);
        DialogUtil.dismissDialog();
        loadingView.setVisibility(View.VISIBLE);
//        requestData();
    }

    @Subscribe
    public void onEvent(FlightInfoBean body) {
        DialogUtil.dismissDialog();
        loadingView.setVisibility(View.GONE);
        showData(body);
    }

}
