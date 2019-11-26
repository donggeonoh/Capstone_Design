package com.donggeon.honmaker.ui.ingredient;

import androidx.annotation.NonNull;

public enum Place {
    상온(0, "상온"),
    냉동(1, "냉동"),
    냉장(2, "냉장");

    private final int index;
    @NonNull
    private final String place;

    Place(int index, @NonNull String place) {
        this.index = index;
        this.place = place;
    }

    public int getIndex() {
        return index;
    }

    @NonNull
    public String getPlace() {
        return place;
    }
    
    public static Place get(String location){
        Place[] values = Place.values();
        for (Place value : values) {
            if(value.getPlace().equals(location)){
                return value;
            }
        }
        return 상온;
    }
}
