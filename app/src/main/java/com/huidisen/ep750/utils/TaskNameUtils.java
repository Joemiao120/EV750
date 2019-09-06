package com.huidisen.ep750.utils;

import android.content.Context;

import com.huidisen.ep750.bean.TaskDefBean;
import com.huidisen.ep750.bean.TaskInfoNameBean;
import com.huidisen.ep750.bean.TaskNodeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 16/7/21.
 */

public class TaskNameUtils {

    public static TaskInfoNameBean getTaskInfoName(Context context, String def, String flightType, String node) {
        TaskInfoNameBean taskInfo = new TaskInfoNameBean();

        TaskDefBean taskDefBean = (TaskDefBean) ObjectSaveUtil.readObject(context,
                Commondata.TASK_DEF + def);

        if (taskDefBean == null)
            return null;

        // taskName
        String flight = "";
        String statusName = null;
        String taskName = taskDefBean.name;
        List<TaskNodeBean> taskNodes = taskDefBean.taskNodes;

        if (flightType.equals("IN")) {
            flight = "进港";
        } else if (flightType.equals("OUT")) {
            flight = "出港";
        }

        if (def.equals("baidu") || def.equals("quanshun") || def.equals("yindao") || def.equals("xingliqianyin")) {
            taskName = flight + taskName;
        }

        // statusName
        for (int i = 0; i < taskNodes.size(); i++) {
            String name = taskNodes.get(i).code;
            if (taskNodes.get(i).code.equals(node))
                statusName = taskNodes.get(i).name;
        }

        taskInfo.setStatusName(statusName);
        taskInfo.setTaskName(taskName);
        taskInfo.setTaskNodes(taskDefBean.taskNodes);

        return taskInfo;
    }
}
