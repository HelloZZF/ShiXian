package com.example.shixian.bean;

import java.util.List;

/**
 * Created by admin on 2017/12/30.
 */

public class Home {

    private int Item1_Type = 1;
    private int Item2_Type = 2;
    private int Item3_Type = 3;
    private int Item4_Type = 4;
    private int Item5_Type = 5;
    private int Item6_Type = 6;

    private List<HomeItem3> Item3_datas;
    private List<HomeItem6> Item5_datas;

    public int getItem1_Type() {
        return Item1_Type;
    }

    public void setItem1_Type(int item1_Type) {
        Item1_Type = item1_Type;
    }

    public int getItem2_Type() {
        return Item2_Type;
    }

    public void setItem2_Type(int item2_Type) {
        Item2_Type = item2_Type;
    }

    public int getItem3_Type() {
        return Item3_Type;
    }

    public void setItem3_Type(int item3_Type) {
        Item3_Type = item3_Type;
    }

    public int getItem4_Type() {
        return Item4_Type;
    }

    public void setItem4_Type(int item4_Type) {
        Item4_Type = item4_Type;
    }

    public int getItem5_Type() {
        return Item5_Type;
    }

    public void setItem5_Type(int item5_Type) {
        Item5_Type = item5_Type;
    }

    public int getItem6_Type() {
        return Item6_Type;
    }

    public void setItem6_Type(int item6_Type) {
        Item6_Type = item6_Type;
    }

    public List<HomeItem3> getItem3_datas() {
        return Item3_datas;
    }

    public void setItem3_datas(List<HomeItem3> item3_datas) {
        Item3_datas = item3_datas;
    }

    public List<HomeItem6> getItem5_datas() {
        return Item5_datas;
    }

    public void setITEM5_datas(List<HomeItem6> ITEM5_datas) {
        this.Item5_datas = ITEM5_datas;
    }
}
