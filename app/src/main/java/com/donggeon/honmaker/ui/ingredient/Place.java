package com.donggeon.honmaker.ui.ingredient;

import androidx.annotation.NonNull;

public enum Place {
    ROOM(0, "room"),
    FREEZE(1, "freeze"),
    FRIDGE(2, "fridge");

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
}
