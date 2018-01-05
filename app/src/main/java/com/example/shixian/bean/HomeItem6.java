package com.example.shixian.bean;

/**
 * Created by admin on 2017/12/30.
 */

public class HomeItem6 {

    private int HomeFoodImageId;

    private String HomeFoodName;

    private String HomeFoodPrice;

    public HomeItem6(int HomeFoodImageId, String HomeFoodName, String HomeFoodPrice) {
        this.HomeFoodImageId = HomeFoodImageId;
        this.HomeFoodName = HomeFoodName;
        this.HomeFoodPrice = HomeFoodPrice;
    }

    public int getHomeFoodImageId() {
        return HomeFoodImageId;
    }

    public String getHomeFoodName() {
        return HomeFoodName;
    }

    public String getHomeFoodPrice() {
        return HomeFoodPrice;
    }

}
