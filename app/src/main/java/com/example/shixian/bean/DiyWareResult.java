package com.example.shixian.bean;

import java.util.List;

/**
 * Created by zzf on 2018/4/4.
 */
public class DiyWareResult {

    private List<DiyWare> data;
    private int totalNum;
    private int pn;
    private int rn;

    public List<DiyWare> getData() {
        return data;
    }

    public void setData(List<DiyWare> data) {
        this.data = data;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getPn() {
        return pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public int getRn() {
        return rn;
    }

    public void setRn(int rn) {
        this.rn = rn;
    }
}
