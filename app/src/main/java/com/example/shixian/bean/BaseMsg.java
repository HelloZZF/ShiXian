package com.example.shixian.bean;

import java.util.List;

/**
 * Created by admin on 2018/1/15.
 */

public class BaseMsg<T> {

    public static final int RESULTCODE_SUCCESS = 200;
    public static final int RESULTCODE_ERROR = 400;

    protected int resultcode;
    protected String reason;
    protected List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
