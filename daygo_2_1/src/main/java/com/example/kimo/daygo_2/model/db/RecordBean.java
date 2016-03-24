package com.example.kimo.daygo_2.model.db;

/**
 * Created by Administrator on 2016/3/19 0019.
 */
public class RecordBean {
    private String title;
    private String location;

    public RecordBean(String title, String location) {
        this.title = title;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
