package com.example.farmfresh.admin.model;

public class AllCategoryModel {


    String id;
    Integer imageurl;

    public AllCategoryModel(String id, Integer imageurl) {
        this.id = id;
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getImageurl() {
        return imageurl;
    }

    public void setImageurl(Integer imageurl) {
        this.imageurl = imageurl;
    }

}
