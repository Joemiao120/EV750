package com.huidisen.ep750.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by miaoyichao on 16/6/10.
 */
@DatabaseTable(tableName = "flight_info")
public class TaskFlyInfoBean {
    // 主键 id 自增长
    @DatabaseField(generatedId = true)
    private int _id;
    @DatabaseField(columnName = "props")
    private String props;//
    @DatabaseField(columnName = "task")
    private String task;//

    @DatabaseField(columnName = "incomingFlyNo")
    private String incomingFlyNo;//
    @DatabaseField(columnName = "departureFlyNo")
    private String departureFlyNo;//

    @DatabaseField(columnName = "flightLine")
    private String flightLine;
    @DatabaseField(columnName = "planeType")
    private String planeType;//

    @DatabaseField(columnName = "planeNo")
    private String planeNo;//
    @DatabaseField(columnName = "preRealFly")
    private long preRealFly;

    @DatabaseField(columnName = "incomingProg")
    private String incomingProg;
    @DatabaseField(columnName = "estimatedArrival")
    private long estimatedArrival;

    @DatabaseField(columnName = "planedArrival")
    private long planedArrival;
    @DatabaseField(columnName = "realArrival")
    private long realArrival;

    @DatabaseField(columnName = "location")
    private String location;
    @DatabaseField(columnName = "departureProg")
    private String departureProg;

    @DatabaseField(columnName = "boardingGate")
    private String boardingGate;
    @DatabaseField(columnName = "planedFly")
    private long planedFly;

    @DatabaseField(columnName = "estimatedFly")
    private long estimatedFly;
    @DatabaseField(columnName = "realFly")
    private long realFly;

    @DatabaseField(columnName = "incomingExcep")
    private String incomingExcep;
    @DatabaseField(columnName = "departureExcep")
    private String departureExcep;

    @DatabaseField(columnName = "baggageClaims")
    private String baggageClaims;
    @DatabaseField(columnName = "counter")
    private String counter;//

    @DatabaseField(columnName = "coulisse")
    private String coulisse;
    @DatabaseField(columnName = "checkInStart")
    private long checkInStart;

    @DatabaseField(columnName = "checkInStop")
    private long checkInStop;
    @DatabaseField(columnName = "boardingEnd")
    private long boardingEnd;

    @DatabaseField(columnName = "urgingBoarding")
    private long urgingBoarding;
    @DatabaseField(columnName = "boardingStation")
    private long boardingStation;

    @DatabaseField(columnName = "alternate")
    private String alternate;
    @DatabaseField(columnName = "executionDate")
    private long executionDate;

    @DatabaseField(columnName = "id")
    private String id;
    @DatabaseField(columnName = "type")
    private String type;

    @DatabaseField(columnName = "explain")
    private String explain;
    @DatabaseField(columnName = "tasks")
    private String tasks;

    @DatabaseField(columnName = "guoneiTerminal")
    private String guoneiTerminal;
    @DatabaseField(columnName = "guojiTerminal")
    private String guojiTerminal;

    @DatabaseField(columnName = "startBoarding")
    private long startBoarding;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getIncomingFlyNo() {
        return incomingFlyNo;
    }

    public void setIncomingFlyNo(String incomingFlyNo) {
        this.incomingFlyNo = incomingFlyNo;
    }

    public String getDepartureFlyNo() {
        return departureFlyNo;
    }

    public void setDepartureFlyNo(String departureFlyNo) {
        this.departureFlyNo = departureFlyNo;
    }

    public String getFlightLine() {
        return flightLine;
    }

    public void setFlightLine(String flightLine) {
        this.flightLine = flightLine;
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType;
    }

    public String getPlaneNo() {
        return planeNo;
    }

    public void setPlaneNo(String planeNo) {
        this.planeNo = planeNo;
    }

    public long getPreRealFly() {
        return preRealFly;
    }

    public void setPreRealFly(long preRealFly) {
        this.preRealFly = preRealFly;
    }

    public String getIncomingProg() {
        return incomingProg;
    }

    public void setIncomingProg(String incomingProg) {
        this.incomingProg = incomingProg;
    }

    public long getEstimatedArrival() {
        return estimatedArrival;
    }

    public void setEstimatedArrival(long estimatedArrival) {
        this.estimatedArrival = estimatedArrival;
    }

    public long getPlanedArrival() {
        return planedArrival;
    }

    public void setPlanedArrival(long planedArrival) {
        this.planedArrival = planedArrival;
    }

    public long getRealArrival() {
        return realArrival;
    }

    public void setRealArrival(long realArrival) {
        this.realArrival = realArrival;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepartureProg() {
        return departureProg;
    }

    public void setDepartureProg(String departureProg) {
        this.departureProg = departureProg;
    }

    public String getBoardingGate() {
        return boardingGate;
    }

    public void setBoardingGate(String boardingGate) {
        this.boardingGate = boardingGate;
    }

    public long getPlanedFly() {
        return planedFly;
    }

    public void setPlanedFly(long planedFly) {
        this.planedFly = planedFly;
    }

    public long getEstimatedFly() {
        return estimatedFly;
    }

    public void setEstimatedFly(long estimatedFly) {
        this.estimatedFly = estimatedFly;
    }

    public long getRealFly() {
        return realFly;
    }

    public void setRealFly(long realFly) {
        this.realFly = realFly;
    }

    public String getIncomingExcep() {
        return incomingExcep;
    }

    public void setIncomingExcep(String incomingExcep) {
        this.incomingExcep = incomingExcep;
    }

    public String getDepartureExcep() {
        return departureExcep;
    }

    public void setDepartureExcep(String departureExcep) {
        this.departureExcep = departureExcep;
    }

    public String getBaggageClaims() {
        return baggageClaims;
    }

    public void setBaggageClaims(String baggageClaims) {
        this.baggageClaims = baggageClaims;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getCoulisse() {
        return coulisse;
    }

    public void setCoulisse(String coulisse) {
        this.coulisse = coulisse;
    }

    public long getCheckInStart() {
        return checkInStart;
    }

    public void setCheckInStart(long checkInStart) {
        this.checkInStart = checkInStart;
    }

    public long getCheckInStop() {
        return checkInStop;
    }

    public void setCheckInStop(long checkInStop) {
        this.checkInStop = checkInStop;
    }

    public long getBoardingEnd() {
        return boardingEnd;
    }

    public void setBoardingEnd(long boardingEnd) {
        this.boardingEnd = boardingEnd;
    }

    public long getUrgingBoarding() {
        return urgingBoarding;
    }

    public void setUrgingBoarding(long urgingBoarding) {
        this.urgingBoarding = urgingBoarding;
    }

    public long getBoardingStation() {
        return boardingStation;
    }

    public void setBoardingStation(long boardingStation) {
        this.boardingStation = boardingStation;
    }

    public String getAlternate() {
        return alternate;
    }

    public void setAlternate(String alternate) {
        this.alternate = alternate;
    }

    public long getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(long executionDate) {
        this.executionDate = executionDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public String getGuoneiTerminal() {
        return guoneiTerminal;
    }

    public void setGuoneiTerminal(String guoneiTerminal) {
        this.guoneiTerminal = guoneiTerminal;
    }

    public String getGuojiTerminal() {
        return guojiTerminal;
    }

    public void setGuojiTerminal(String guojiTerminal) {
        this.guojiTerminal = guojiTerminal;
    }

    public long getStartBoarding() {
        return startBoarding;
    }

    public void setStartBoarding(long startBoarding) {
        this.startBoarding = startBoarding;
    }

    @Override
    public String toString() {
        return "TaskFlyInfoBean{" +
                "_id=" + _id +
                ", props='" + props + '\'' +
                ", task='" + task + '\'' +
                ", incomingFlyNo='" + incomingFlyNo + '\'' +
                ", departureFlyNo='" + departureFlyNo + '\'' +
                ", flightLine='" + flightLine + '\'' +
                ", planeType='" + planeType + '\'' +
                ", planeNo='" + planeNo + '\'' +
                ", preRealFly=" + preRealFly +
                ", incomingProg='" + incomingProg + '\'' +
                ", estimatedArrival=" + estimatedArrival +
                ", planedArrival=" + planedArrival +
                ", realArrival=" + realArrival +
                ", location=" + location +
                ", departureProg='" + departureProg + '\'' +
                ", boardingGate='" + boardingGate + '\'' +
                ", planedFly=" + planedFly +
                ", estimatedFly=" + estimatedFly +
                ", realFly=" + realFly +
                ", incomingExcep='" + incomingExcep + '\'' +
                ", departureExcep='" + departureExcep + '\'' +
                ", baggageClaims='" + baggageClaims + '\'' +
                ", counter='" + counter + '\'' +
                ", coulisse='" + coulisse + '\'' +
                ", checkInStart=" + checkInStart +
                ", checkInStop=" + checkInStop +
                ", boardingEnd=" + boardingEnd +
                ", urgingBoarding=" + urgingBoarding +
                ", boardingStation=" + boardingStation +
                ", alternate='" + alternate + '\'' +
                ", executionDate=" + executionDate +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", explain='" + explain + '\'' +
                ", tasks='" + tasks + '\'' +
                ", guoneiTerminal='" + guoneiTerminal + '\'' +
                ", guojiTerminal='" + guojiTerminal + '\'' +
                ", startBoarding=" + startBoarding +
                '}';
    }
}
