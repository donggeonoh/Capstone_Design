package com.donggeon.honmaker.extension.databinding;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;

public class AnyViewAdapters {

    @BindingAdapter({"activate"})
    public static void setActivated(@NonNull final View view,
                                    final boolean activate) {
        view.setActivated(activate);
    }

    @BindingConversion
    public static int setLayoutOrientation(final boolean isVisible) {
        return isVisible ? View.VISIBLE : View.GONE;
    }

}
