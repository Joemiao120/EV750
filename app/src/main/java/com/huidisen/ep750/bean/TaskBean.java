package com.huidisen.ep750.bean;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by miaoyichao on 16/6/10.
 */
public class TaskBean {
    private String result;
    private int status;

    private List<DataBean> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    @DatabaseTable(tableName = "task_data")
    public static class DataBean {
        // 主键 id 自增长
        @DatabaseField(generatedId = true)
        private int _id;

        @DatabaseField(canBeNull = true, foreign = true, columnName = "task_fly_id",foreignAutoRefresh = true)
        private TaskFlyInfoBean taskFlyInfo;

        @DatabaseField(canBeNull = true, foreign = true, columnName = "task_id",foreignAutoRefresh = true)
        private TaskInfoBean taskInfo;

        public TaskFlyInfoBean getTaskFlyInfo() {
            return taskFlyInfo;
        }

        public void setTaskFlyInfo(TaskFlyInfoBean taskFlyInfo) {
            this.taskFlyInfo = taskFlyInfo;
        }

        public TaskInfoBean getTaskInfo() {
            return taskInfo;
        }

        public void setTaskInfo(TaskInfoBean taskInfo) {
            this.taskInfo = taskInfo;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "taskFlyInfo=" + taskFlyInfo +
                    ", taskInfo=" + taskInfo +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TaskBean{" +
                "result='" + result + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }

}
