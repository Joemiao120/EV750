package com.huidisen.ep750.bean;

import java.util.List;

/**
 * Created by mac on 16/7/21.
 */

public class TaskInfoNameBean {
    private String taskName;
    private String statusName;
    private List<TaskNodeBean> taskNodes;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<TaskNodeBean> getTaskNodes() {
        return taskNodes;
    }

    public void setTaskNodes(List<TaskNodeBean> taskNodes) {
        this.taskNodes = taskNodes;
    }
}
