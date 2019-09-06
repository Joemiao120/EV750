package com.huidisen.ep750.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huidisen.ep750.R;
import com.huidisen.ep750.bean.InfoDetailBean;

import java.util.List;

/**
 * Created by miaoyichao on 16/6/13.
 */
public class TaskInfoDetailAdapter extends BaseAdapter {
    private Context context;
    private List<InfoDetailBean> infoDetails;


    public TaskInfoDetailAdapter(Context context, List<InfoDetailBean> infoDetails) {
        this.context = context;
        this.infoDetails = infoDetails;
    }

    @Override
    public int getCount() {

        return infoDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return infoDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (convertView == null){
            hodler = new ViewHodler();

            convertView = LayoutInflater.from(context).inflate(R.layout.info_detail_item,null);
            hodler.itemName = (TextView) convertView.findViewById(R.id.item_name);
            hodler.itemValue = (TextView) convertView.findViewById(R.id.item_value);
            convertView.setTag(hodler);
        }else {
            hodler = (ViewHodler) convertView.getTag();
        }

        hodler.itemName.setText(infoDetails.get(position).getItemName());
        hodler.itemValue.setText(infoDetails.get(position).getItemValue());
        return convertView;
    }

    static class ViewHodler{
        TextView itemName;
        TextView itemValue;
    }
}
