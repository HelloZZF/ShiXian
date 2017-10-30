package com.example.shixian.classify;

/**
 * Created by admin on 2017/7/25.
 */

public class Goods {

    private String price;

    private String thought;

    private String name;

    private String Introduction;

    private int FoodImageId;


    public Goods(String price, String thought, String name, int foodImageId, String Introduction) {
        this.price = price;
        this.thought = thought;
        this.name = name;
        this.FoodImageId = foodImageId;
        this.Introduction = Introduction;
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

    public int getFoodImageId() {
        return FoodImageId;
    }

    public String getIntroduction() {
        return Introduction;
    }
}
