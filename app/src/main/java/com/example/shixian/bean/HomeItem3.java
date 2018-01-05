package com.example.shixian.bean;

/**
 * Created by admin on 2017/12/30.
 */

public class HomeItem3 {

    private String userName;

    private String foodName;

    private int UserImageId;

    private int FoodImageId;

    public HomeItem3(String userName, String foodName, int UserImageId, int FoodImageId) {
        this.userName = userName;
        this.foodName = foodName;
        this.UserImageId = UserImageId;
        this.FoodImageId = FoodImageId;
    }

    public int getFoodImageId() {
        return FoodImageId;
    }

    public int getUserImageId() {
        return UserImageId;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getUserName() {
        return userName;
    }

}
