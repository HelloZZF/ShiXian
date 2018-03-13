package com.example.shixian.bean;

import java.io.Serializable;

/**
 * Created by zzf on 2018/2/25.
 */

public class ShopCart extends Wares implements Serializable{

    private int count;
    private boolean isCheck = true;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
