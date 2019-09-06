package com.huidisen.ep750.bean;

import java.util.Date;

/**
 * Created by Eason Lu on 2014/4/20.
 */
public class GISData {

    private double latitude;
    private double longitude;
    private double alt;
    private long timeStamp;
    private String vehicleId;
    private int taskId;
    private String jobNumber;

    private float speed;
    private float accuracy;
    private int starCount;
    private int gpsStatus;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public int getGpsStatus() {
        return gpsStatus;
    }

    public void setGpsStatus(int gpsStatus) {
        this.gpsStatus = gpsStatus;
    }

    @Override
    public String toString() {
        return "GISData{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", alt=" + alt +
                ", timeStamp=" + timeStamp +
                ", vehicleId='" + vehicleId + '\'' +
                ", taskId=" + taskId +
                ", jobNumber='" + jobNumber + '\'' +
                ", speed=" + speed +
                ", accuracy=" + accuracy +
                ", starCount=" + starCount +
                ", gpsStatus=" + gpsStatus +
                '}';
    }
}
