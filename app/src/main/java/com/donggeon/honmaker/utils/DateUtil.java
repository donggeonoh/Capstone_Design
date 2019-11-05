package com.donggeon.honmaker.utils;

import android.text.format.DateFormat;

import androidx.annotation.NonNull;

import java.util.Date;

public class DateUtil {

    @NonNull
    public static String getCurrentDateTime() {
        return DateFormat.format("yyyy-MM-dd hh:mm:ss a", new Date())
                .toString();
    }
}
