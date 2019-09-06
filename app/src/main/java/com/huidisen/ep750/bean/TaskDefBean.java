package com.huidisen.ep750.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by miaoyichao on 16/6/24.
 */
public class TaskDefBean implements Serializable {
    private static final long serialVersionUID = 1L; // 定义序列化ID

    public String name;
    public String type;
    public String groupName;
    public String isFlightTask;

    public List<TaskNodeBean> taskNodes;

    @Override
    public String toString() {
        return "TaskDefBean{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", groupName='" + groupName + '\'' +
                ", isFlightTask='" + isFlightTask + '\'' +
                ", taskNodes=" + taskNodes +
                '}';
    }
}
