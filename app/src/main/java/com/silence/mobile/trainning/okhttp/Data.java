package com.silence.mobile.trainning.okhttp;

/**
 * @author violet
 * date :  2018/8/17 02:11
 */
public class Data {
    private String type;
    private int level;
    private Double price;

    public Data(String type, int level, Double price){
        this.type = type;
        this.level = level;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
