package com.example.shixian.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by zzf on 2018/3/8.
 */

public class Address implements Serializable,Comparable<Address>{

    private String id;
    private String user_id;
    private String address;
    private String phone;
    private String isdefault;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    @Override
    public int compareTo(@NonNull Address address) {

        if(address.getIsdefault()!=null && this.getIsdefault() !=null)
            return address.getIsdefault().compareTo(this.getIsdefault());

        return -1;
    }
}
