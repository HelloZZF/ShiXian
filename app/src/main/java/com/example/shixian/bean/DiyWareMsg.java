package com.example.shixian.bean;

/**
 * Created by zzf on 2018/4/4.
 */
public class DiyWareMsg {

    private String resultcode;
    private String reason;
    private DiyWareResult result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public DiyWareResult getResult() {
        return result;
    }

    public void setResult(DiyWareResult result) {
        this.result = result;
    }
}
