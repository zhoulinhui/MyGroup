package com.my.zhou.group.bean;

/**
 * Created by Administrator on 2017/4/17.
 */

public class Person {
    private String e_head_photo;
    private String user_name;
    private String e_mobile;

    public String getE_head_photo() {
        return e_head_photo;
    }

    public void setE_head_photo(String e_head_photo) {
        this.e_head_photo = e_head_photo;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getE_mobile() {
        return e_mobile;
    }

    public void setE_mobile(String e_mobile) {
        this.e_mobile = e_mobile;
    }

    public Person(String e_head_photo, String user_name, String e_mobile) {
        this.e_head_photo = e_head_photo;
        this.user_name = user_name;
        this.e_mobile = e_mobile;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "e_head_photo='" + e_head_photo + '\'' +
                ", user_name='" + user_name + '\'' +
                ", e_mobile='" + e_mobile + '\'' +
                '}';
    }
}
