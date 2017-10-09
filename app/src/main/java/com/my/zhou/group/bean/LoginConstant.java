package com.my.zhou.group.bean;

/**
 * Created by Administrator on 2017/4/12.
 */

public class LoginConstant {
    private String uid;
    private String device_token;
    private String username;
    private String password;
    private String is_lock;

    public LoginConstant() {
    }

    public LoginConstant(String device_token, String is_lock, String password, String uid, String username) {
        this.device_token = device_token;
        this.is_lock = is_lock;
        this.password = password;
        this.uid = uid;
        this.username = username;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getIs_lock() {
        return is_lock;
    }

    public void setIs_lock(String is_lock) {
        this.is_lock = is_lock;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    //    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
////    public LoginConstant(String uid, String device_token, String username, String password) {
////        this.uid = uid;
////        this.device_token = device_token;
////        this.username = username;
////        this.password = password;
////    }
//
//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
//
//    public String getDevice_token() {
//        return device_token;
//    }
//
//    public void setDevice_token(String device_token) {
//        this.device_token = device_token;
//    }
//    public String getUid() {
//        return id;
//    }
//
//    public void setUid(String id) {
//        this.id = id;
//    }
//
//    public String getToken() {
//        return device_token;
//    }
//
//    public void setToken(String device_token) {
//        this.device_token = device_token;
//    }
}
