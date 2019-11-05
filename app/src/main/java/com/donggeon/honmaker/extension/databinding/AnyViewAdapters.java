package com.donggeon.honmaker.extension.databinding;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

public class AnyViewAdapters {

    @BindingAdapter({"activate"})
    public static void setActivated(@NonNull final View view,
                                    final boolean activate) {
        view.setActivated(activate);
    }
}
