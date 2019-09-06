package com.huidisen.ep750.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by miaoyichao on 16/6/24.
 */
public class VehicleInfoBean implements Serializable{

    /**
     * result : success
     * data : {"color":"红色","distance":null,"excuteTaskType":["daketi","cheketi"],"description":"这是一辆客梯车","remark":null,"completedTasksNum":"0","carType":"客梯","productionDate":null,"fuelNum":null,"taskTypeList":[{"name":"搭客梯","type":"daketi"},{"name":"引导","type":"yindao"},{"name":"进港地勤","type":"diqin_in"},{"name":"垃圾","type":"laji"},{"name":"出港登机桥","type":"dengjiqiao_out"},{"name":"空调","type":"kongtiao"},{"name":"摆渡","type":"baidu"},{"name":"电源","type":"dianyuan"},{"name":"撤客梯","type":"cheketi"},{"name":"牵引","type":"qianyin"},{"name":"除冰","type":"chubing"},{"name":"污水","type":"wushui"},{"name":"清水","type":"qingshui"},{"name":"气源","type":"qiyuan"},{"name":"全顺","type":"quanshun"},{"name":"残疾人保障","type":"canjirenbaozhang"},{"name":"进港登机桥","type":"dengjiqiao_in"},{"name":"出港地勤","type":"diqin_out"}],"displacement":"2.0L","id":"864488012013134","flightDep":true,"class":"class com.zlqf.airservice.model.Vehicle","numberOfViolations":null,"regisDate":null,"maintenanceNum":null,"carBrand":"东风","productionDateStr":null,"engineNum":"16666","depName":"机务部","carNumber":"A16666","fuelType":"92#","depId":"1","vehicleStatus":"BUSY"}
     * status : 200
     */

    private String result;
    /**
     * color : 红色
     * distance : null
     * excuteTaskType : ["daketi","cheketi"]
     * description : 这是一辆客梯车
     * remark : null
     * completedTasksNum : 0
     * carType : 客梯
     * productionDate : null
     * fuelNum : null
     * taskTypeList : [{"name":"搭客梯","type":"daketi"},{"name":"引导","type":"yindao"},{"name":"进港地勤","type":"diqin_in"},{"name":"垃圾","type":"laji"},{"name":"出港登机桥","type":"dengjiqiao_out"},{"name":"空调","type":"kongtiao"},{"name":"摆渡","type":"baidu"},{"name":"电源","type":"dianyuan"},{"name":"撤客梯","type":"cheketi"},{"name":"牵引","type":"qianyin"},{"name":"除冰","type":"chubing"},{"name":"污水","type":"wushui"},{"name":"清水","type":"qingshui"},{"name":"气源","type":"qiyuan"},{"name":"全顺","type":"quanshun"},{"name":"残疾人保障","type":"canjirenbaozhang"},{"name":"进港登机桥","type":"dengjiqiao_in"},{"name":"出港地勤","type":"diqin_out"}]
     * displacement : 2.0L
     * id : 864488012013134
     * flightDep : true
     * class : class com.zlqf.airservice.model.Vehicle
     * numberOfViolations : null
     * regisDate : null
     * maintenanceNum : null
     * carBrand : 东风
     * productionDateStr : null
     * engineNum : 16666
     * depName : 机务部
     * carNumber : A16666
     * fuelType : 92#
     * depId : 1
     * vehicleStatus : BUSY
     */

    private DataBean data;
    private int status;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean implements Serializable{
        private String color;
        private String distance;
        private String description;
        private String remark;
        private String completedTasksNum;
        private String carType;
        private String productionDate;
        private String fuelNum;
        private String displacement;
        private String id;
        private boolean flightDep;
        @SerializedName("class")
        private String classX;
        private String numberOfViolations;
        private String regisDate;
        private String maintenanceNum;
        private String carBrand;
        private String productionDateStr;
        private String engineNum;
        private String depName;
        private String carNumber;
        private String fuelType;
        private String depId;
        private String vehicleStatus;
        private List<String> excuteTaskType;

        @Override
        public String toString() {
            return "DataBean{" +
                    "color='" + color + '\'' +
                    ", distance='" + distance + '\'' +
                    ", description='" + description + '\'' +
                    ", remark='" + remark + '\'' +
                    ", completedTasksNum='" + completedTasksNum + '\'' +
                    ", carType='" + carType + '\'' +
                    ", productionDate='" + productionDate + '\'' +
                    ", fuelNum='" + fuelNum + '\'' +
                    ", displacement='" + displacement + '\'' +
                    ", id='" + id + '\'' +
                    ", flightDep=" + flightDep +
                    ", classX='" + classX + '\'' +
                    ", numberOfViolations='" + numberOfViolations + '\'' +
                    ", regisDate='" + regisDate + '\'' +
                    ", maintenanceNum='" + maintenanceNum + '\'' +
                    ", carBrand='" + carBrand + '\'' +
                    ", productionDateStr='" + productionDateStr + '\'' +
                    ", engineNum='" + engineNum + '\'' +
                    ", depName='" + depName + '\'' +
                    ", carNumber='" + carNumber + '\'' +
                    ", fuelType='" + fuelType + '\'' +
                    ", depId='" + depId + '\'' +
                    ", vehicleStatus='" + vehicleStatus + '\'' +
                    ", excuteTaskType=" + excuteTaskType +
                    ", taskTypeList=" + taskTypeList +
                    '}';
        }

        /**
         * name : 搭客梯
         * type : daketi
         */

        private List<TaskTypeListBean> taskTypeList;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }



        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }


        public String getCompletedTasksNum() {
            return completedTasksNum;
        }

        public void setCompletedTasksNum(String completedTasksNum) {
            this.completedTasksNum = completedTasksNum;
        }

        public String getCarType() {
            return carType;
        }

        public void setCarType(String carType) {
            this.carType = carType;
        }


        public String getDisplacement() {
            return displacement;
        }

        public void setDisplacement(String displacement) {
            this.displacement = displacement;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isFlightDep() {
            return flightDep;
        }

        public void setFlightDep(boolean flightDep) {
            this.flightDep = flightDep;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }



        public String getCarBrand() {
            return carBrand;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }


        public String getEngineNum() {
            return engineNum;
        }

        public void setEngineNum(String engineNum) {
            this.engineNum = engineNum;
        }

        public String getDepName() {
            return depName;
        }

        public void setDepName(String depName) {
            this.depName = depName;
        }

        public String getCarNumber() {
            return carNumber;
        }

        public void setCarNumber(String carNumber) {
            this.carNumber = carNumber;
        }

        public String getFuelType() {
            return fuelType;
        }

        public void setFuelType(String fuelType) {
            this.fuelType = fuelType;
        }

        public String getDepId() {
            return depId;
        }

        public void setDepId(String depId) {
            this.depId = depId;
        }

        public String getVehicleStatus() {
            return vehicleStatus;
        }

        public void setVehicleStatus(String vehicleStatus) {
            this.vehicleStatus = vehicleStatus;
        }

        public List<String> getExcuteTaskType() {
            return excuteTaskType;
        }

        public void setExcuteTaskType(List<String> excuteTaskType) {
            this.excuteTaskType = excuteTaskType;
        }

        public List<TaskTypeListBean> getTaskTypeList() {
            return taskTypeList;
        }

        public void setTaskTypeList(List<TaskTypeListBean> taskTypeList) {
            this.taskTypeList = taskTypeList;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getProductionDate() {
            return productionDate;
        }

        public void setProductionDate(String productionDate) {
            this.productionDate = productionDate;
        }

        public String getFuelNum() {
            return fuelNum;
        }

        public void setFuelNum(String fuelNum) {
            this.fuelNum = fuelNum;
        }

        public String getNumberOfViolations() {
            return numberOfViolations;
        }

        public void setNumberOfViolations(String numberOfViolations) {
            this.numberOfViolations = numberOfViolations;
        }

        public String getRegisDate() {
            return regisDate;
        }

        public void setRegisDate(String regisDate) {
            this.regisDate = regisDate;
        }

        public String getMaintenanceNum() {
            return maintenanceNum;
        }

        public void setMaintenanceNum(String maintenanceNum) {
            this.maintenanceNum = maintenanceNum;
        }

        public String getProductionDateStr() {
            return productionDateStr;
        }

        public void setProductionDateStr(String productionDateStr) {
            this.productionDateStr = productionDateStr;
        }

        public static class TaskTypeListBean implements Serializable {
            private String name;
            private String type;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
