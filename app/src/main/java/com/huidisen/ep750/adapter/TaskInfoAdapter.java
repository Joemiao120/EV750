package com.huidisen.ep750.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huidisen.ep750.R;
import com.huidisen.ep750.bean.InfoShowBean;
import com.huidisen.ep750.bean.TaskBean;
import com.huidisen.ep750.bean.InfoShowBean;

import java.util.List;

/**
 * Created by miaoyichao on 16/5/30.
 */
public class TaskInfoAdapter extends BaseAdapter {
    private List<InfoShowBean> taskDatas;
    private LayoutInflater mLayoutInflater;

    public TaskInfoAdapter(Context context) {
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setTaskDatas(List<InfoShowBean> list) {
        this.taskDatas = list;

    }

    @Override
    public int getCount() {
        if (taskDatas != null)
            return taskDatas.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        if (taskDatas != null)
            return taskDatas.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        InfoShowBean taskInfo = taskDatas.get(position);

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.data_list_item, null);

            holder = new ViewHolder();
            holder.arriveName = (TextView) convertView.findViewById(R.id.arrive_name);
            holder.planeType = (TextView) convertView.findViewById(R.id.plane_type);
            holder.landTime = (TextView) convertView.findViewById(R.id.land_time);
            holder.taskType = (TextView) convertView.findViewById(R.id.task_type);
            holder.taskStatus = (TextView) convertView.findViewById(R.id.task_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.arriveName.setText(taskInfo.getIncomingFlyNo());
        holder.planeType.setText(taskInfo.getPlaneType());
        holder.landTime.setText(taskInfo.getRealArrival());
        holder.taskType.setText(taskInfo.getTaskDefType());
        holder.taskStatus.setText(taskInfo.getStatusCode());

        return convertView;
    }

    static class ViewHolder {
        TextView arriveName;
        TextView planeType;
        TextView landTime;
        TextView taskType;
        TextView taskStatus;
    }

}
