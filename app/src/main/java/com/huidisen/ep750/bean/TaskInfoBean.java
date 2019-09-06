package com.huidisen.ep750.bean;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by mac on 16/6/21.
 */

@DatabaseTable(tableName = "task_info")
public class TaskInfoBean {
    // 主键 id 自增长
    @DatabaseField(generatedId = true)
    private int _id;
    @DatabaseField(columnName = "flyNo")
    private String flyNo;
    @DatabaseField(columnName = "pubTime")
    private String pubTime;

    @DatabaseField(columnName = "flightType")
    private String flightType;
    @DatabaseField(columnName = "userName")
    private String userName;

    @DatabaseField(columnName = "taskDefType")
    private String taskDefType;
    @DatabaseField(columnName = "taskFlyId")
    private String taskFlyId;

    @DatabaseField(columnName = "userId")
    private String userId;
    @DatabaseField(columnName = "carNumber")
    private String carNumber;

    @DatabaseField(columnName = "flightTask")
    private String flightTask;
    @DatabaseField(columnName = "endTime")
    private String endTime;


    @DatabaseField(columnName = "id")
    private String id;
    @DatabaseField(columnName = "teamId")
    private String teamId;

    @DatabaseField(columnName = "depId")
    private String depId;
    @DatabaseField(columnName = "vehicleId")
    private String vehId;

    @DatabaseField(columnName = "classX")
    @SerializedName("class")
    private String classX;


    @DatabaseField(columnName = "nodeEndTime")
    private String nodeEndTime;

    @DatabaseField(columnName = "statusCode")
    private String statusCode;

    @DatabaseField(columnName = "dispatcherId")
    private String dispatcherId;

    @DatabaseField(columnName = "jobNumber")
    private String jobNumber;
    @DatabaseField(columnName = "cancelUserId")
    private String cancelUserId;
    @DatabaseField(columnName = "cancelReason")
    private String cancelReason;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getFlyNo() {
        return flyNo;
    }

    public void setFlyNo(String flyNo) {
        this.flyNo = flyNo;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTaskDefType() {
        return taskDefType;
    }

    public void setTaskDefType(String taskDefType) {
        this.taskDefType = taskDefType;
    }

    public String getTaskFlyId() {
        return taskFlyId;
    }

    public void setTaskFlyId(String taskFlyId) {
        this.taskFlyId = taskFlyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getFlightTask() {
        return flightTask;
    }

    public void setFlightTask(String flightTask) {
        this.flightTask = flightTask;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getVehId() {
        return vehId;
    }

    public void setVehId(String vehId) {
        this.vehId = vehId;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getNodeEndTime() {
        return nodeEndTime;
    }

    public void setNodeEndTime(String nodeEndTime) {
        this.nodeEndTime = nodeEndTime;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDispatcherId() {
        return dispatcherId;
    }

    public void setDispatcherId(String dispatcherId) {
        this.dispatcherId = dispatcherId;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getCancelUserId() {
        return cancelUserId;
    }

    public void setCancelUserId(String cancelUserId) {
        this.cancelUserId = cancelUserId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }


    @Override
    public String toString() {
        return "TaskInfoBean{" +
                "_id=" + _id +
                ", flyNo='" + flyNo + '\'' +
                ", pubTime='" + pubTime + '\'' +
                ", flightType='" + flightType + '\'' +
                ", userName='" + userName + '\'' +
                ", taskDefType='" + taskDefType + '\'' +
                ", taskFlyId='" + taskFlyId + '\'' +
                ", userId='" + userId + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", flightTask='" + flightTask + '\'' +
                ", endTime=" + endTime +
                ", id='" + id + '\'' +
                ", teamId='" + teamId + '\'' +
                ", depId='" + depId + '\'' +
                ", vehId='" + vehId + '\'' +
                ", classX='" + classX + '\'' +
                ", nodeEndTime=" + nodeEndTime +
                ", statusCode='" + statusCode + '\'' +
                ", dispatcherId='" + dispatcherId + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", cancelUserId='" + cancelUserId + '\'' +
                ", cancelReason='" + cancelReason + '\'' +
                '}';
    }
}
