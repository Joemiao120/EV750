package com.huidisen.ep750.bean;

/**
 * Created by miaoyichao on 16/6/10.
 */
public class InfoShowBean {
    // 进港航班号
    private String incomingFlyNo;
    // 机型
    private String planeType;
    //
    private String realArrival;
    // 机型
    private String taskDefType;
    // 状态
    private String statusCode;
    // 任务类型
    private String taskContent;
    // taskId
    private String taskId;
    // 执行人员工号
    private String jobNumber;

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getIncomingFlyNo() {
        return incomingFlyNo;
    }

    public void setIncomingFlyNo(String incomingFlyNo) {
        this.incomingFlyNo = incomingFlyNo;
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType;
    }

    public String getRealArrival() {
        return realArrival;
    }

    public void setRealArrival(String realArrival) {
        this.realArrival = realArrival;
    }

    public String getTaskDefType() {
        return taskDefType;
    }

    public void setTaskDefType(String taskDefType) {
        this.taskDefType = taskDefType;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    @Override
    public String toString() {
        return "InfoShowBean{" +
                "incomingFlyNo='" + incomingFlyNo + '\'' +
                ", planeType='" + planeType + '\'' +
                ", realArrival='" + realArrival + '\'' +
                ", taskDefType='" + taskDefType + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", taskContent='" + taskContent + '\'' +
                '}';
    }
}
