package com.huidisen.ep750.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FlightBean implements Serializable {

    /**
     * props : 国内|国内
     * task : 正班|正班
     * incomingFlyNo : SC1332
     * departureFlyNo : CA1325
     * flightLine : 北京-郑州-北京
     * planeType : B738
     * planeNo : B5460
     * preRealFly : 20160510123500
     * incomingProg : 前方起飞
     * estimatedArrival : 20160510123500
     * planedArrival : 20160510123500
     * realArrival : 20160510123500
     * location : 32
     * departureProg : 正在值机
     * boardingGate : 26
     * planedFly : 20160510123500
     * estimatedFly : 20160510123500
     * realFly : 20160510123500
     * incomingExcep : 延误（公司计划）
     * departureExcep : 延误（天气原因）
     * baggageClaims : 20160510123500
     * counter : F18-F19
     * coulisse : S03
     * checkInStart : 20160510123500
     * checkInStop : 20160510123500
     * boardingEnd : 20160510123500
     * urgingBoarding : 20160510123500
     * boardingStation : 20160510123500
     * alternate : 北京
     * executionDate : 20160510123500
     * id : 00006
     * guojiTerminal : T3
     * guoneiTerminal : T2
     */

    @SerializedName("props") public String props;
    @SerializedName("task") public String task;
    @SerializedName("incomingFlyNo") public String incomingFlyNo;
    @SerializedName("departureFlyNo") public String departureFlyNo;
    @SerializedName("flightLine") public String flightLine;
    @SerializedName("planeType") public String planeType;
    @SerializedName("planeNo") public String planeNo;
    @SerializedName("preRealFly") public String preRealFly;
    @SerializedName("incomingProg") public String incomingProg;
    @SerializedName("estimatedArrival") public String estimatedArrival;
    @SerializedName("planedArrival") public String planedArrival;
    @SerializedName("realArrival") public String realArrival;
    @SerializedName("location") public String location;
    @SerializedName("departureProg") public String departureProg;
    @SerializedName("boardingGate") public String boardingGate;
    @SerializedName("planedFly") public String planedFly;
    @SerializedName("estimatedFly") public String estimatedFly;
    @SerializedName("realFly") public String realFly;
    @SerializedName("incomingExcep") public String incomingExcep;
    @SerializedName("departureExcep") public String departureExcep;
    @SerializedName("baggageClaims") public String baggageClaims;
    @SerializedName("counter") public String counter;
    @SerializedName("coulisse") public String coulisse;
    @SerializedName("checkInStart") public String checkInStart;
    @SerializedName("checkInStop") public String checkInStop;
    @SerializedName("boardingEnd") public String boardingEnd;
    @SerializedName("urgingBoarding") public String urgingBoarding;
    @SerializedName("boardingStation") public String boardingStation;
    @SerializedName("alternate") public String alternate;
    @SerializedName("executionDate") public String executionDate;
    @SerializedName("id") public String id;
    @SerializedName("guojiTerminal") public String guojiTerminal;
    @SerializedName("guoneiTerminal") public String guoneiTerminal;

    @Override
    public String toString() {
        return "FlightBean{" +
                "props='" + props + '\'' +
                ", task='" + task + '\'' +
                ", incomingFlyNo='" + incomingFlyNo + '\'' +
                ", departureFlyNo='" + departureFlyNo + '\'' +
                ", flightLine='" + flightLine + '\'' +
                ", planeType='" + planeType + '\'' +
                ", planeNo='" + planeNo + '\'' +
                ", preRealFly='" + preRealFly + '\'' +
                ", incomingProg='" + incomingProg + '\'' +
                ", estimatedArrival='" + estimatedArrival + '\'' +
                ", planedArrival='" + planedArrival + '\'' +
                ", realArrival='" + realArrival + '\'' +
                ", location='" + location + '\'' +
                ", departureProg='" + departureProg + '\'' +
                ", boardingGate='" + boardingGate + '\'' +
                ", planedFly='" + planedFly + '\'' +
                ", estimatedFly='" + estimatedFly + '\'' +
                ", realFly='" + realFly + '\'' +
                ", incomingExcep='" + incomingExcep + '\'' +
                ", departureExcep='" + departureExcep + '\'' +
                ", baggageClaims='" + baggageClaims + '\'' +
                ", counter='" + counter + '\'' +
                ", coulisse='" + coulisse + '\'' +
                ", checkInStart='" + checkInStart + '\'' +
                ", checkInStop='" + checkInStop + '\'' +
                ", boardingEnd='" + boardingEnd + '\'' +
                ", urgingBoarding='" + urgingBoarding + '\'' +
                ", boardingStation='" + boardingStation + '\'' +
                ", alternate='" + alternate + '\'' +
                ", executionDate='" + executionDate + '\'' +
                ", id='" + id + '\'' +
                ", guojiTerminal='" + guojiTerminal + '\'' +
                ", guoneiTerminal='" + guoneiTerminal + '\'' +
                '}';
    }
}