package com.example.shixian.homepage;

/**
 * Created by admin on 2017/8/28.
 */

public class item1 {

    private String userName;

    private String foodName;

    private int UserImageId;

    private int FoodImageId;

    public item1(String userName, String foodName, int UserImageId, int FoodImageId) {
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
