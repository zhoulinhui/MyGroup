package com.my.zhou.group.bean;

/**
 * Created by laobai on 2017/4/26.
 */

public class OrderItem {
    private String goodPrice;
    private String goodNum;
    private String goodName;

    public OrderItem() {
    }

    public OrderItem(String goodName, String goodNum, String goodPrice) {
        this.goodName = goodName;
        this.goodNum = goodNum;
        this.goodPrice = goodPrice;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(String goodNum) {
        this.goodNum = goodNum;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "goodName='" + goodName + '\'' +
                ", goodPrice='" + goodPrice + '\'' +
                ", goodNum='" + goodNum + '\'' +
                '}';
    }
}
