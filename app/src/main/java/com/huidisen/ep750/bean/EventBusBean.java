package com.huidisen.ep750.bean;

/**
 * Created by mac on 16/5/11.
 */
public class EventBusBean {
    private String msg;
    private String taskId;

    public EventBusBean(String msg, String taskId){
        this.msg = msg;
        this.taskId = taskId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
