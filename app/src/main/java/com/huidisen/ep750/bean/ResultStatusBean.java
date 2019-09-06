package com.huidisen.ep750.bean;

/**
 * Created by miaoyichao on 16/6/15.
 */
public class ResultStatusBean {

    private String result;
    private int status;
    private boolean data;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

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

    @Override
    public String toString() {
        return "ResultStatusBean{" +
                "result='" + result + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}
