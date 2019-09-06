package com.huidisen.ep750.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huidisen.ep750.R;
import com.huidisen.ep750.bean.InfoShowBean;

import java.util.List;

/**
 * Created by miaoyichao on 16/5/30.
 */
public class NonflightTaskInfoAdapter extends BaseAdapter {
    private List<InfoShowBean> taskInfoBeans;
    private LayoutInflater mLayoutInflater;

    public NonflightTaskInfoAdapter(Context context) {
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setTaskDatas(List<InfoShowBean> list) {
        this.taskInfoBeans = list;

    }

    @Override
    public int getCount() {

        if (taskInfoBeans != null) {
            return taskInfoBeans.size();
        }

        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (taskInfoBeans != null) {
            return taskInfoBeans.get(position);
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        InfoShowBean taskInfoBean = taskInfoBeans.get(position);

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.nonflight_data_list_item, null);

            holder = new ViewHolder();
            holder.taskName = (TextView) convertView.findViewById(R.id.task_name);
            holder.taskStatus = (TextView) convertView.findViewById(R.id.task_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.taskName.setText(taskInfoBean.getTaskDefType());
        holder.taskStatus.setText(taskInfoBean.getStatusCode());

        return convertView;
    }

    static class ViewHolder {
        TextView taskName;
        TextView taskStatus;
    }

}
