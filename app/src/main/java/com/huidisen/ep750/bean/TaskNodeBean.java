package com.huidisen.ep750.bean;

import java.io.Serializable;

/**
 * Created by miaoyichao on 16/6/24.
 */
public class TaskNodeBean implements Serializable {
    private static final long serialVersionUID = 2222L; // 定义序列化ID
    public String name;
    public String code;
    public String auto;
    public String fence;
    public String fenceType;

    @Override
    public String toString() {
        return "TaskNodeBean{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", auto='" + auto + '\'' +
                ", fence='" + fence + '\'' +
                ", fenceType='" + fenceType + '\'' +
                '}';
    }

}
