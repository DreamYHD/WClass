package com.example.administrator.wclass.data.bean;

/**
 * Created by Administrator on 2018/4/29.
 */

public class ClassBean {
    private String class_image_url;
    private String class_number;
    private String class_name;
    private String class_id_number;
    private User user;

    public String getClass_image_url() {
        return class_image_url;
    }

    public void setClass_image_url(String class_image_url) {
        this.class_image_url = class_image_url;
    }

    public String getClass_number() {
        return class_number;
    }

    public void setClass_number(String class_number) {
        this.class_number = class_number;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_id_number() {
        return class_id_number;
    }

    public void setClass_id_number(String class_id_number) {
        this.class_id_number = class_id_number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
