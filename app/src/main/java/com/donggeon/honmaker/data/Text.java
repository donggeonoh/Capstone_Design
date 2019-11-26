package com.donggeon.honmaker.data;

import com.google.gson.annotations.SerializedName;

public class Text {
    
    @SerializedName("UID")
    private String uid;
    
    @SerializedName("text")
    private String text;
    
    public Text(String uid, String text) {
        this.uid = uid;
        this.text = text;
    }
    
    public String getUid() {
        return uid;
    }
    
    public void setUid(String uid) {
        this.uid = uid;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
}
