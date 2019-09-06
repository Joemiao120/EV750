package com.huidisen.ep750.bean;

public class CarCheckItem {
    public String key;
    public String [] value;
    public int i;
    public CarCheckItem(String key, String[] value, int i){
        this.key = key;
        this.value = value;
        this.i= i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getKey() {
        return key;
    }

    public String[] getValue() {
        return value;
    }

    public int getI() {
        return i;
    }
}
