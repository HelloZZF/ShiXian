package com.example.shixian.homepage;

/**
 * Created by admin on 2017/8/28.
 */

public class item5 {

    private int HomeFoodImageId;

    private String HomeFoodName;

    private String HomeFoodPrice;

    public item5 (int HomeFoodImageId, String HomeFoodName, String HomeFoodPrice) {
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
