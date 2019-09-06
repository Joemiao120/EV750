package com.huidisen.ep750.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huidisen.ep750.R;

import java.util.ArrayList;

/**
 * Created by vektor on 31/05/16.
 */
public class SortFlightAdapter extends InputTrackingRecyclerViewAdapter<SortFlightAdapter.ViewHolder> {

    private final static int SELECTOR_COLOR = 0xFFBDBDBD;
    private ArrayList<String> data;
    private Context context;

    public SortFlightAdapter(Context context, ArrayList<String> items) {
        super(context);
        data = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flight_show_sort, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        boolean isSelected = position == getSelectedItem();

        if (isSelected) {
            holder.itemView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_selected_flight_item));
        }else {
            holder.itemView.setBackgroundDrawable(new ColorDrawable(0X00000000));

        }
        holder.itemView.setSelected(isSelected);

        holder.textView.setText(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != 0) {
                    String tmp = data.get(position);
                    data.set(position, data.get(position - 1));
                    data.set(position - 1, tmp);
                    notifyDataSetChanged();
                }
                notifyItemChanged(getSelectedItem());
                setSelectedItem(getRecyclerView().getChildLayoutPosition(v));
                notifyItemChanged(getSelectedItem());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_flight_sort_item);
        }
    }
}
