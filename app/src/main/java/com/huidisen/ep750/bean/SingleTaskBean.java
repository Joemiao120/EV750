package com.huidisen.ep750.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 16/7/12.
 */

public class SingleTaskBean {

    /**
     * result : success
     * data : {"taskFlyInfo":{"props":"国内|国内","task":"正班|正班","incomingFlyNo":"SC1119","departureFlyNo":"CA1325","flightLine":"北京-郑州-北京","planeType":"B738","planeNo":"B5460","preRealFly":20160510123500,"incomingProg":"到达本站","estimatedArrival":20160510123500,"planedArrival":20160510123500,"realArrival":20160510123500,"location":"32","departureProg":"正在值机","boardingGate":"29","planedFly":20160510123500,"estimatedFly":20160510123500,"realFly":20160510123500,"incomingExcep":"延误（公司计划）","departureExcep":"延误（天气原因）","baggageClaims":"20160510123500","counter":"F18-F19","coulisse":"S03","checkInStart":20160510123500,"checkInStop":20160510123500,"startBoarding":20160510123500,"boardingEnd":20160510123500,"urgingBoarding":20160510123500,"boardingStation":20160510123500,"alternate":"北京","executionDate":20160510123500,"id":"00003_in","type":"INCOMING","explain":"说明;;;","tasks":null,"guojiTerminal":"T3","guoneiTerminal":"T2"},"taskInfo":{"flyNo":"SC1119","pubTime":"1468305205663","cancelUserId":null,"flightType":"IN","userName":"","taskDefType":"daketi","taskFlyId":"00003_in","userId":null,"carNumber":"A16666","flightTask":"true","teamId":"1","depId":"1","vehId":"5","endTime":null,"id":"274","cancelReason":null,"dispatcherId":"1","nodeEndTime":null,"class":"class com.zlqf.airservice.model.Task","statusCode":"djs"}}
     * status : 200
     */

    private String result;
    /**
     * taskFlyInfo : {"props":"国内|国内","task":"正班|正班","incomingFlyNo":"SC1119","departureFlyNo":"CA1325","flightLine":"北京-郑州-北京","planeType":"B738","planeNo":"B5460","preRealFly":20160510123500,"incomingProg":"到达本站","estimatedArrival":20160510123500,"planedArrival":20160510123500,"realArrival":20160510123500,"location":"32","departureProg":"正在值机","boardingGate":"29","planedFly":20160510123500,"estimatedFly":20160510123500,"realFly":20160510123500,"incomingExcep":"延误（公司计划）","departureExcep":"延误（天气原因）","baggageClaims":"20160510123500","counter":"F18-F19","coulisse":"S03","checkInStart":20160510123500,"checkInStop":20160510123500,"startBoarding":20160510123500,"boardingEnd":20160510123500,"urgingBoarding":20160510123500,"boardingStation":20160510123500,"alternate":"北京","executionDate":20160510123500,"id":"00003_in","type":"INCOMING","explain":"说明;;;","tasks":null,"guojiTerminal":"T3","guoneiTerminal":"T2"}
     * taskInfo : {"flyNo":"SC1119","pubTime":"1468305205663","cancelUserId":null,"flightType":"IN","userName":"","taskDefType":"daketi","taskFlyId":"00003_in","userId":null,"carNumber":"A16666","flightTask":"true","teamId":"1","depId":"1","vehId":"5","endTime":null,"id":"274","cancelReason":null,"dispatcherId":"1","nodeEndTime":null,"class":"class com.zlqf.airservice.model.Task","statusCode":"djs"}
     */

    private TaskBean.DataBean data;
    private int status;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public TaskBean.DataBean getData() {
        return data;
    }

    public void setData(TaskBean.DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

//    public static class DataBean {
//        /**
//         * props : 国内|国内
//         * task : 正班|正班
//         * incomingFlyNo : SC1119
//         * departureFlyNo : CA1325
//         * flightLine : 北京-郑州-北京
//         * planeType : B738
//         * planeNo : B5460
//         * preRealFly : 20160510123500
//         * incomingProg : 到达本站
//         * estimatedArrival : 20160510123500
//         * planedArrival : 20160510123500
//         * realArrival : 20160510123500
//         * location : 32
//         * departureProg : 正在值机
//         * boardingGate : 29
//         * planedFly : 20160510123500
//         * estimatedFly : 20160510123500
//         * realFly : 20160510123500
//         * incomingExcep : 延误（公司计划）
//         * departureExcep : 延误（天气原因）
//         * baggageClaims : 20160510123500
//         * counter : F18-F19
//         * coulisse : S03
//         * checkInStart : 20160510123500
//         * checkInStop : 20160510123500
//         * startBoarding : 20160510123500
//         * boardingEnd : 20160510123500
//         * urgingBoarding : 20160510123500
//         * boardingStation : 20160510123500
//         * alternate : 北京
//         * executionDate : 20160510123500
//         * id : 00003_in
//         * type : INCOMING
//         * explain : 说明;;;
//         * tasks : null
//         * guojiTerminal : T3
//         * guoneiTerminal : T2
//         */
//
//        private TaskFlyInfoBean taskFlyInfo;
//        /**
//         * flyNo : SC1119
//         * pubTime : 1468305205663
//         * cancelUserId : null
//         * flightType : IN
//         * userName :
//         * taskDefType : daketi
//         * taskFlyId : 00003_in
//         * userId : null
//         * carNumber : A16666
//         * flightTask : true
//         * teamId : 1
//         * depId : 1
//         * vehId : 5
//         * endTime : null
//         * id : 274
//         * cancelReason : null
//         * dispatcherId : 1
//         * nodeEndTime : null
//         * class : class com.zlqf.airservice.model.Task
//         * statusCode : djs
//         */
//
//        private TaskInfoBean taskInfo;
//
//        public TaskFlyInfoBean getTaskFlyInfo() {
//            return taskFlyInfo;
//        }
//
//        public void setTaskFlyInfo(TaskFlyInfoBean taskFlyInfo) {
//            this.taskFlyInfo = taskFlyInfo;
//        }
//
//        public TaskInfoBean getTaskInfo() {
//            return taskInfo;
//        }
//
//        public void setTaskInfo(TaskInfoBean taskInfo) {
//            this.taskInfo = taskInfo;
//        }
//
//        public static class TaskFlyInfoBean {
//            private String props;
//            private String task;
//            private String incomingFlyNo;
//            private String departureFlyNo;
//            private String flightLine;
//            private String planeType;
//            private String planeNo;
//            private long preRealFly;
//            private String incomingProg;
//            private long estimatedArrival;
//            private long planedArrival;
//            private long realArrival;
//            private String location;
//            private String departureProg;
//            private String boardingGate;
//            private long planedFly;
//            private long estimatedFly;
//            private long realFly;
//            private String incomingExcep;
//            private String departureExcep;
//            private String baggageClaims;
//            private String counter;
//            private String coulisse;
//            private long checkInStart;
//            private long checkInStop;
//            private long startBoarding;
//            private long boardingEnd;
//            private long urgingBoarding;
//            private long boardingStation;
//            private String alternate;
//            private long executionDate;
//            private String id;
//            private String type;
//            private String explain;
//            private Object tasks;
//            private String guojiTerminal;
//            private String guoneiTerminal;
//
//            public String getProps() {
//                return props;
//            }
//
//            public void setProps(String props) {
//                this.props = props;
//            }
//
//            public String getTask() {
//                return task;
//            }
//
//            public void setTask(String task) {
//                this.task = task;
//            }
//
//            public String getIncomingFlyNo() {
//                return incomingFlyNo;
//            }
//
//            public void setIncomingFlyNo(String incomingFlyNo) {
//                this.incomingFlyNo = incomingFlyNo;
//            }
//
//            public String getDepartureFlyNo() {
//                return departureFlyNo;
//            }
//
//            public void setDepartureFlyNo(String departureFlyNo) {
//                this.departureFlyNo = departureFlyNo;
//            }
//
//            public String getFlightLine() {
//                return flightLine;
//            }
//
//            public void setFlightLine(String flightLine) {
//                this.flightLine = flightLine;
//            }
//
//            public String getPlaneType() {
//                return planeType;
//            }
//
//            public void setPlaneType(String planeType) {
//                this.planeType = planeType;
//            }
//
//            public String getPlaneNo() {
//                return planeNo;
//            }
//
//            public void setPlaneNo(String planeNo) {
//                this.planeNo = planeNo;
//            }
//
//            public long getPreRealFly() {
//                return preRealFly;
//            }
//
//            public void setPreRealFly(long preRealFly) {
//                this.preRealFly = preRealFly;
//            }
//
//            public String getIncomingProg() {
//                return incomingProg;
//            }
//
//            public void setIncomingProg(String incomingProg) {
//                this.incomingProg = incomingProg;
//            }
//
//            public long getEstimatedArrival() {
//                return estimatedArrival;
//            }
//
//            public void setEstimatedArrival(long estimatedArrival) {
//                this.estimatedArrival = estimatedArrival;
//            }
//
//            public long getPlanedArrival() {
//                return planedArrival;
//            }
//
//            public void setPlanedArrival(long planedArrival) {
//                this.planedArrival = planedArrival;
//            }
//
//            public long getRealArrival() {
//                return realArrival;
//            }
//
//            public void setRealArrival(long realArrival) {
//                this.realArrival = realArrival;
//            }
//
//            public String getLocation() {
//                return location;
//            }
//
//            public void setLocation(String location) {
//                this.location = location;
//            }
//
//            public String getDepartureProg() {
//                return departureProg;
//            }
//
//            public void setDepartureProg(String departureProg) {
//                this.departureProg = departureProg;
//            }
//
//            public String getBoardingGate() {
//                return boardingGate;
//            }
//
//            public void setBoardingGate(String boardingGate) {
//                this.boardingGate = boardingGate;
//            }
//
//            public long getPlanedFly() {
//                return planedFly;
//            }
//
//            public void setPlanedFly(long planedFly) {
//                this.planedFly = planedFly;
//            }
//
//            public long getEstimatedFly() {
//                return estimatedFly;
//            }
//
//            public void setEstimatedFly(long estimatedFly) {
//                this.estimatedFly = estimatedFly;
//            }
//
//            public long getRealFly() {
//                return realFly;
//            }
//
//            public void setRealFly(long realFly) {
//                this.realFly = realFly;
//            }
//
//            public String getIncomingExcep() {
//                return incomingExcep;
//            }
//
//            public void setIncomingExcep(String incomingExcep) {
//                this.incomingExcep = incomingExcep;
//            }
//
//            public String getDepartureExcep() {
//                return departureExcep;
//            }
//
//            public void setDepartureExcep(String departureExcep) {
//                this.departureExcep = departureExcep;
//            }
//
//            public String getBaggageClaims() {
//                return baggageClaims;
//            }
//
//            public void setBaggageClaims(String baggageClaims) {
//                this.baggageClaims = baggageClaims;
//            }
//
//            public String getCounter() {
//                return counter;
//            }
//
//            public void setCounter(String counter) {
//                this.counter = counter;
//            }
//
//            public String getCoulisse() {
//                return coulisse;
//            }
//
//            public void setCoulisse(String coulisse) {
//                this.coulisse = coulisse;
//            }
//
//            public long getCheckInStart() {
//                return checkInStart;
//            }
//
//            public void setCheckInStart(long checkInStart) {
//                this.checkInStart = checkInStart;
//            }
//
//            public long getCheckInStop() {
//                return checkInStop;
//            }
//
//            public void setCheckInStop(long checkInStop) {
//                this.checkInStop = checkInStop;
//            }
//
//            public long getStartBoarding() {
//                return startBoarding;
//            }
//
//            public void setStartBoarding(long startBoarding) {
//                this.startBoarding = startBoarding;
//            }
//
//            public long getBoardingEnd() {
//                return boardingEnd;
//            }
//
//            public void setBoardingEnd(long boardingEnd) {
//                this.boardingEnd = boardingEnd;
//            }
//
//            public long getUrgingBoarding() {
//                return urgingBoarding;
//            }
//
//            public void setUrgingBoarding(long urgingBoarding) {
//                this.urgingBoarding = urgingBoarding;
//            }
//
//            public long getBoardingStation() {
//                return boardingStation;
//            }
//
//            public void setBoardingStation(long boardingStation) {
//                this.boardingStation = boardingStation;
//            }
//
//            public String getAlternate() {
//                return alternate;
//            }
//
//            public void setAlternate(String alternate) {
//                this.alternate = alternate;
//            }
//
//            public long getExecutionDate() {
//                return executionDate;
//            }
//
//            public void setExecutionDate(long executionDate) {
//                this.executionDate = executionDate;
//            }
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public String getType() {
//                return type;
//            }
//
//            public void setType(String type) {
//                this.type = type;
//            }
//
//            public String getExplain() {
//                return explain;
//            }
//
//            public void setExplain(String explain) {
//                this.explain = explain;
//            }
//
//            public Object getTasks() {
//                return tasks;
//            }
//
//            public void setTasks(Object tasks) {
//                this.tasks = tasks;
//            }
//
//            public String getGuojiTerminal() {
//                return guojiTerminal;
//            }
//
//            public void setGuojiTerminal(String guojiTerminal) {
//                this.guojiTerminal = guojiTerminal;
//            }
//
//            public String getGuoneiTerminal() {
//                return guoneiTerminal;
//            }
//
//            public void setGuoneiTerminal(String guoneiTerminal) {
//                this.guoneiTerminal = guoneiTerminal;
//            }
//        }
//
//        public static class TaskInfoBean {
//            private String flyNo;
//            private String pubTime;
//            private Object cancelUserId;
//            private String flightType;
//            private String userName;
//            private String taskDefType;
//            private String taskFlyId;
//            private Object userId;
//            private String carNumber;
//            private String flightTask;
//            private String teamId;
//            private String depId;
//            private String vehId;
//            private Object endTime;
//            private String id;
//            private Object cancelReason;
//            private String dispatcherId;
//            private Object nodeEndTime;
//            @SerializedName("class")
//            private String classX;
//            private String statusCode;
//
//            public String getFlyNo() {
//                return flyNo;
//            }
//
//            public void setFlyNo(String flyNo) {
//                this.flyNo = flyNo;
//            }
//
//            public String getPubTime() {
//                return pubTime;
//            }
//
//            public void setPubTime(String pubTime) {
//                this.pubTime = pubTime;
//            }
//
//            public Object getCancelUserId() {
//                return cancelUserId;
//            }
//
//            public void setCancelUserId(Object cancelUserId) {
//                this.cancelUserId = cancelUserId;
//            }
//
//            public String getFlightType() {
//                return flightType;
//            }
//
//            public void setFlightType(String flightType) {
//                this.flightType = flightType;
//            }
//
//            public String getUserName() {
//                return userName;
//            }
//
//            public void setUserName(String userName) {
//                this.userName = userName;
//            }
//
//            public String getTaskDefType() {
//                return taskDefType;
//            }
//
//            public void setTaskDefType(String taskDefType) {
//                this.taskDefType = taskDefType;
//            }
//
//            public String getTaskFlyId() {
//                return taskFlyId;
//            }
//
//            public void setTaskFlyId(String taskFlyId) {
//                this.taskFlyId = taskFlyId;
//            }
//
//            public Object getUserId() {
//                return userId;
//            }
//
//            public void setUserId(Object userId) {
//                this.userId = userId;
//            }
//
//            public String getCarNumber() {
//                return carNumber;
//            }
//
//            public void setCarNumber(String carNumber) {
//                this.carNumber = carNumber;
//            }
//
//            public String getFlightTask() {
//                return flightTask;
//            }
//
//            public void setFlightTask(String flightTask) {
//                this.flightTask = flightTask;
//            }
//
//            public String getTeamId() {
//                return teamId;
//            }
//
//            public void setTeamId(String teamId) {
//                this.teamId = teamId;
//            }
//
//            public String getDepId() {
//                return depId;
//            }
//
//            public void setDepId(String depId) {
//                this.depId = depId;
//            }
//
//            public String getVehId() {
//                return vehId;
//            }
//
//            public void setVehId(String vehId) {
//                this.vehId = vehId;
//            }
//
//            public Object getEndTime() {
//                return endTime;
//            }
//
//            public void setEndTime(Object endTime) {
//                this.endTime = endTime;
//            }
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public Object getCancelReason() {
//                return cancelReason;
//            }
//
//            public void setCancelReason(Object cancelReason) {
//                this.cancelReason = cancelReason;
//            }
//
//            public String getDispatcherId() {
//                return dispatcherId;
//            }
//
//            public void setDispatcherId(String dispatcherId) {
//                this.dispatcherId = dispatcherId;
//            }
//
//            public Object getNodeEndTime() {
//                return nodeEndTime;
//            }
//
//            public void setNodeEndTime(Object nodeEndTime) {
//                this.nodeEndTime = nodeEndTime;
//            }
//
//            public String getClassX() {
//                return classX;
//            }
//
//            public void setClassX(String classX) {
//                this.classX = classX;
//            }
//
//            public String getStatusCode() {
//                return statusCode;
//            }
//
//            public void setStatusCode(String statusCode) {
//                this.statusCode = statusCode;
//            }
//        }
//    }
}
