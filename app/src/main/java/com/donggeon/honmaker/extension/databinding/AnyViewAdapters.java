package com.donggeon.honmaker.extension.databinding;

import android.view.View;

import androidx.databinding.BindingConversion;

/**
 * DataBinding 을 위한 어댑터
 * 여기 있는건 모든 뷰에 적용할 수 있는 어댑터들
 */
public class AnyViewAdapters {

    /**
     * @param isVisible true : 화면에 보이도록 함 (View.VISIBLE)
     *                  false : 뷰를 화면에서 숨김 (View.GONE)
     */
    @BindingConversion
    public static int setLayoutOrientation(final boolean isVisible) {
        return isVisible ? View.VISIBLE : View.GONE;
    }

}
