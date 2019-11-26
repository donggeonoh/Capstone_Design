package com.donggeon.honmaker.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtil {

    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

}
