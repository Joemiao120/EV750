package com.huidisen.ep750.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.huidisen.ep750.R;

import java.util.ArrayList;

/**
 * Created by miaoyichao on 16/6/12.
 */
public class UserNumberAdapter extends BaseAdapter implements Filterable {
    private ArrayList<String> userInfos;
    private MyFilter myFilter;
    private Context context;

    private ArrayList<String> mOriginalValues;
    private final Object mLock = new Object();

    public UserNumberAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        this.userInfos = datas;
    }

    @Override
    public int getCount() {
        return userInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return userInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_name_item, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.user_name);
        textView.setText(userInfos.get(position));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter();
        }
        return myFilter;
    }


    class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            // 持有过滤操作完成之后的数据。该数据包括过滤操作之后的数据的值以及数量。 count:数量 values包含过滤操作之后的数据的值
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    // 将list的用户 集合转换给这个原始数据的ArrayList
                    mOriginalValues = userInfos;
                }
            }

            // 不做过滤
//            ArrayList<String> list = mOriginalValues;
//            results.values = list;
//            results.count = list.size();

            // 过滤
            if (prefix == null || prefix.length() == 0) {
                synchronized (mLock) {
                    ArrayList<String> list = mOriginalValues;
                    results.values = list;
                    results.count = list.size();
                }
            } else {
                // 做正式的筛选
                String prefixString = prefix.toString().toLowerCase();

                // 声明一个临时的集合对象 将原始数据赋给这个临时变量
                final ArrayList<String> values = mOriginalValues;

                final int count = values.size();

                // 新的集合对象
                final ArrayList<String> newValues = new ArrayList<String>(
                        count);

                for (int i = 0; i < count; i++) {
                    if (values.get(i).contains(prefix)) {
                        newValues.add(values.get(i));
                    }
                }
                // 然后将这个新的集合数据赋给FilterResults对象
                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            // 重新将与适配器相关联的List重赋值一下
            userInfos = (ArrayList<String>) results.values;

            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}
