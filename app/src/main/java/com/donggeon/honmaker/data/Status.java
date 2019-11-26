package com.donggeon.honmaker.data;

import com.google.gson.annotations.SerializedName;

public class Status {
    
    @SerializedName("code")
    private int code;
    
    public Status(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
}
