package com.donggeon.honmaker.data;

import com.google.gson.annotations.SerializedName;

public class User {
    
    @SerializedName("UID")
    private String uid;
    
    public User(String uid) {
        this.uid = uid;
    }
    
    public String getUid() {
        return uid;
    }
    
    public void setUid(String uid) {
        this.uid = uid;
    }
}
