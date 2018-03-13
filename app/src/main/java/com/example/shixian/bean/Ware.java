package com.example.shixian.bean;

/**
 * Created by zzf on 2018/1/28.
 */

public class Ware {

    private String price;

    private String thought;

    private String name;

    private String introduction;

    private String imageurl;


    public Ware(String price, String thought, String name, String foodImageId, String Introduction) {
        this.price = price;
        this.thought = thought;
        this.name = name;
        this.imageurl = foodImageId;
        this.introduction = Introduction;
    }

    public String getPrice() {
        return price;
    }

    public String getThought() {
        return thought;
    }

    public String getName() {
        return name;
    }

    public String getFoodImageId() {
        return imageurl;
    }

    public String getIntroduction() {
        return introduction;
    }
}
