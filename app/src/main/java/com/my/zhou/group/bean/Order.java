package com.my.zhou.group.bean;

import java.util.List;

/**
 * 详情界面
 * Created by Administrator on 2017/4/20.
 */

public class Order {
    private String add_time;
    private String money;
    private String money_total;
    private String store_addr;
    private String store_name;
    private String store_id;
    private String j_du;
    private String w_du;
    private String lat;
    private String lng;
    private String user_addr;
    private String nickname;
    private List<OrderItem> detail;

    public Order() {
    }

    public Order(String add_time, List<OrderItem> detail, String j_du, String lat, String lng, String money, String money_total, String nickname, String store_addr, String store_id, String store_name, String user_addr, String w_du) {
        this.add_time = add_time;
        this.detail = detail;
        this.j_du = j_du;
        this.lat = lat;
        this.lng = lng;
        this.money = money;
        this.money_total = money_total;
        this.nickname = nickname;
        this.store_addr = store_addr;
        this.store_id = store_id;
        this.store_name = store_name;
        this.user_addr = user_addr;
        this.w_du = w_du;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public List<OrderItem> getDetail() {
        return detail;
    }

    public void setDetail(List<OrderItem> detail) {
        this.detail = detail;
    }

    public String getJ_du() {
        return j_du;
    }

    public void setJ_du(String j_du) {
        this.j_du = j_du;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStore_addr() {
        return store_addr;
    }

    public void setStore_addr(String store_addr) {
        this.store_addr = store_addr;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getUser_addr() {
        return user_addr;
    }

    public void setUser_addr(String user_addr) {
        this.user_addr = user_addr;
    }

    public String getW_du() {
        return w_du;
    }

    public void setW_du(String w_du) {
        this.w_du = w_du;
    }

    @Override
    public String toString() {
        return "Order{" +
                "add_time='" + add_time + '\'' +
                ", money='" + money + '\'' +
                ", money_total='" + money_total + '\'' +
                ", store_addr='" + store_addr + '\'' +
                ", store_name='" + store_name + '\'' +
                ", store_id='" + store_id + '\'' +
                ", j_du='" + j_du + '\'' +
                ", w_du='" + w_du + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", user_addr='" + user_addr + '\'' +
                ", nickname='" + nickname + '\'' +
                ", detail=" + detail +
                '}';
    }
}
