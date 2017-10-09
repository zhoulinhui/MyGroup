package com.my.zhou.group.bean;

/**
 * Created by laobai on 2017/4/27.
 */

public class Login {
    private String e_id;
    private String e_mobile;
    private String user_name;
    private String is_lock;

    public Login() {
    }

    public Login(String e_id, String e_mobile, String is_lock, String user_name) {
        this.e_id = e_id;
        this.e_mobile = e_mobile;
        this.is_lock = is_lock;
        this.user_name = user_name;
    }

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    public String getE_mobile() {
        return e_mobile;
    }

    public void setE_mobile(String e_mobile) {
        this.e_mobile = e_mobile;
    }

    public String getIs_lock() {
        return is_lock;
    }

    public void setIs_lock(String is_lock) {
        this.is_lock = is_lock;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
