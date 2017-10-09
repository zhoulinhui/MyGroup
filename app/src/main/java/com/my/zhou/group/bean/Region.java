package com.my.zhou.group.bean;

/**
 * Created by Administrator on 2017/4/14.
 */

public class Region {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Region(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Region() {
    }
}
