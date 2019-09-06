package com.huidisen.ep750.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huidisen.ep750.R;
import com.huidisen.ep750.activity.FlightInfoDetailActivity;
import com.huidisen.ep750.activity.SortFlightShowActivity;
import com.huidisen.ep750.bean.FlightBean;
import com.huidisen.ep750.bean.FlightInfoBean;
import com.huidisen.ep750.utils.SPUtils;
import com.orhanobut.logger.Logger;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.huidisen.ep750.utils.DateUtils.convertTime;

/**
 * Created by lovexiaov on 16/6/9.
 */
public class AllFlightsAdapter extends SectionedRecyclerViewAdapter<AllFlightsAdapter.HeaderViewHolder, AllFlightsAdapter.ViewHolder, AllFlightsAdapter.FooterViewHolder> {

    // 前方起飞,到达本站,本站起飞,已经起飞,远机位航班
    public static final String FRONT_NO_FLY_BEAN = "前方未起飞";
    public static final String FRONT_FLY_BEAN = "前方起飞";
    public static final String FLYING_BEAN = "本站已起飞";
    public static final String ARRIVE_HERE_BEAN = "到达本站";
    public static final String FAR_LOCATION_BEAN = "远机位";
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<List<FlightBean>> beans = new ArrayList<>();
    private ArrayList<String> types = new ArrayList<>();

    private HashMap<String, List<FlightBean>> dataMap = new HashMap<>();

    private Context context;
    private String columnName;
    private int mSelectedItem;
    private RecyclerView mRecyclerView;

    private long timestamp = SystemClock.uptimeMillis();

    public AllFlightsAdapter(FlightInfoBean.DataBean data, Context context, String columnName) {

        this.context = context;
        this.columnName = columnName;

        getValidData(data.arriveHere, ARRIVE_HERE_BEAN);
        getValidData(data.frontFly, FRONT_FLY_BEAN);
        getValidData(data.frontNoFly, FRONT_NO_FLY_BEAN);
        getValidData(data.flying, FLYING_BEAN);
        getValidData(data.farLocation, FAR_LOCATION_BEAN);

        String flightSort = (String) SPUtils
                .get(context, SortFlightShowActivity.FLIGHT_SORT_SP, SortFlightShowActivity.DEFAULT_ORDER);

        if (TextUtils.isEmpty(flightSort)) {
            flightSort = SortFlightShowActivity.DEFAULT_ORDER;
        }
        Collections.addAll(items, flightSort.split(","));
        Logger.t("Sort Order").i(items.toString());

        fillBeans();

    }


    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (isConfirmButton(event)) {
                        if ((event.getFlags() & KeyEvent.FLAG_LONG_PRESS) == KeyEvent.FLAG_LONG_PRESS) {
                            mRecyclerView.findViewHolderForAdapterPosition(mSelectedItem).itemView.performLongClick();
                        } else {
                            event.startTracking();
                        }
                        return true;
                    } else {
                        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                            return tryMoveSelection(1);
                        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                            return tryMoveSelection(-1);
                        }
                    }
                } else if (event.getAction() == KeyEvent.ACTION_UP && isConfirmButton(event) && ((event
                        .getFlags() & KeyEvent.FLAG_LONG_PRESS) != KeyEvent.FLAG_LONG_PRESS)) {
                    mRecyclerView.findViewHolderForAdapterPosition(mSelectedItem).itemView.performClick();
                    return true;
                }

                return false;
            }
        });

    }


    @Override
    protected int getSectionCount() {
        return beans.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        return beans.get(section).size();
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.title_flight_type, null);
        return new HeaderViewHolder(view);
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_flight, parent, false);

        return new ViewHolder(view);
    }

    @Override
    protected FooterViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindSectionHeaderViewHolder(HeaderViewHolder holder, int section) {
        holder.render(types.get(section));

    }

    @Override
    protected void onBindSectionFooterViewHolder(FooterViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, final int section, final int position) {
        int count = 0;
        for (int i = 0; i < section; i++) {
            count += beans.get(i).size();
        }

        count += position;
//        Logger.d("section: %s, position: %s, count: %s, selected: %s", section, position, count, mSelectedItem);

        if (count == mSelectedItem - (section + 1)) {
            holder.render(beans.get(section).get(position), 0xcc888888);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent flightDetailIntent = new Intent(context, FlightInfoDetailActivity.class);
                    flightDetailIntent.putExtra(FlightInfoDetailActivity.DATA_ID, beans.get(section).get(position).id);

                    context.startActivity(flightDetailIntent);

                }
            });
        } else {
            holder.render(beans.get(section).get(position), 0x00000000);
        }
    }


    private void getValidData(List<FlightBean> flights, String tag) {
        if (flights.size() > 0) {
            dataMap.put(tag, flights);
        }
    }

    private void fillBeans() {
        for (String item : items) {
            for (int i = 0; i < dataMap.size(); i++) {
                if (dataMap.containsKey(item)) {
                    beans.add(dataMap.get(item));
                    types.add(item);
                    break;
                }
            }
        }
    }

    private boolean isConfirmButton(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_BUTTON_A:
                return true;
            default:
                return false;
        }
    }

    private boolean tryMoveSelection(int direction) {
        if (SystemClock.uptimeMillis() - timestamp <= 200) {
            return true;
        }
        timestamp = SystemClock.uptimeMillis();

        int nextSelectItem = mSelectedItem + direction;

        // If still within valid bounds, move the selection, notify to redraw, and scroll
        if (nextSelectItem >= 0 && nextSelectItem < getItemCount()) {
            notifyItemChanged(mSelectedItem);
            mSelectedItem = nextSelectItem;
            notifyItemChanged(mSelectedItem);
            mRecyclerView.smoothScrollToPosition(mSelectedItem);
            return true;
        }

        return false;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_flight_type_name) TextView type;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void render(String title) {
            type.setText(title);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_flight_container) LinearLayout container;
        @BindView(R.id.tv_incoming_number) TextView incomingNumber;
        @BindView(R.id.tv_departure_number) TextView departureNumber;
        @BindView(R.id.tv_flight_planed_arrival) TextView planedArrival;
        @BindView(R.id.tv_flight_planed_fly) TextView planedFly;
        @BindView(R.id.tv_flight_dynamic_column) TextView dynamicColumn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void render(FlightBean flightBean, int color) {
            incomingNumber.setText(flightBean.incomingFlyNo);
            departureNumber.setText(flightBean.departureFlyNo);
            planedArrival.setText(convertTime(flightBean.planedArrival));
            planedFly.setText(convertTime(flightBean.planedFly));

            if (columnName != null) {
                switch (columnName) {
                    case "出港航班号":
                        dynamicColumn.setText(flightBean.departureFlyNo);
                        break;
                    case "机号":
                        dynamicColumn.setText(flightBean.planeNo);
                        break;
                    case "机型":
                        dynamicColumn.setText(flightBean.planeType);
                        break;
                    case "机位":
                        dynamicColumn.setText(flightBean.location);
                        break;
                    case "登机口":
                        dynamicColumn.setText(flightBean.boardingGate);
                        break;
                    case "落地时间":
                        dynamicColumn.setText(convertTime(flightBean.realArrival));
                        break;
                    case "起飞时间":
                        dynamicColumn.setText(convertTime(flightBean.realFly));
                        break;
                    case "行李转盘":
                        dynamicColumn.setText(flightBean.baggageClaims);
                        break;

                    default:
                        break;
                }
            } else {
                dynamicColumn.setText(flightBean.location);
            }
            if (color == 0) {
                container.setBackgroundColor(color);
            } else {
                container.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_selected_flight_item));
            }
        }
    }


    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }

    }
}
