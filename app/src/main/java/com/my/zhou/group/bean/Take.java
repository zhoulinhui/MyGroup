package com.my.zhou.group.bean;

/**
 * 待取货
 * Created by Administrator on 2017/4/20.
 */

public class Take {
    private String user_addr;
    private String add_time;
    private String money;
    private String money_total;
    private String store_addr;
    private String store_name;
    private String eoid;
    private String store_id;
    private String range;
    private String store_phone;
    private String j_du;
    private String w_du;
    private String lat;
    private String lng;

    public String getUser_addr() {
        return user_addr;
    }

    public void setUser_addr(String user_addr) {
        this.user_addr = user_addr;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoney_total() {
        return money_total;
    }

    public void setMoney_total(String money_total) {
        this.money_total = money_total;
    }

    public String getStore_addr() {
        return store_addr;
    }

    public void setStore_addr(String store_addr) {
        this.store_addr = store_addr;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getEoid() {
        return eoid;
    }

    public void setEoid(String eoid) {
        this.eoid = eoid;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getStore_phone() {
        return store_phone;
    }

    public void setStore_phone(String store_phone) {
        this.store_phone = store_phone;
    }

    public String getJ_du() {
        return j_du;
    }

    public void setJ_du(String j_du) {
        this.j_du = j_du;
    }

    public String getW_du() {
        return w_du;
    }

    public void setW_du(String w_du) {
        this.w_du = w_du;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Take() {
    }

    public Take(String user_addr, String add_time, String money, String money_total, String store_addr, String store_name, String eoid, String store_id, String range, String store_phone, String j_du, String w_du, String lat, String lng) {
        this.user_addr = user_addr;
        this.add_time = add_time;
        this.money = money;
        this.money_total = money_total;
        this.store_addr = store_addr;
        this.store_name = store_name;
        this.eoid = eoid;
        this.store_id = store_id;
        this.range = range;
        this.store_phone = store_phone;
        this.j_du = j_du;
        this.w_du = w_du;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Take{" +
                "user_addr='" + user_addr + '\'' +
                ", add_time='" + add_time + '\'' +
                ", money='" + money + '\'' +
                ", money_total='" + money_total + '\'' +
                ", store_addr='" + store_addr + '\'' +
                ", store_name='" + store_name + '\'' +
                ", eoid='" + eoid + '\'' +
                ", store_id='" + store_id + '\'' +
                ", range='" + range + '\'' +
                ", store_phone='" + store_phone + '\'' +
                ", j_du='" + j_du + '\'' +
                ", w_du='" + w_du + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
