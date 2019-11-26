package com.donggeon.honmaker.extension.databinding;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DataBinding 을 위한 어댑터
 * 여기 있는건 ViewGroup 에 적용할 수 있는 어댑터들
 * <p>
 * LinearLayout, RelativeLayout, ConstraintLayout 같은 레이아웃이나
 * RecyclerView, ViewPager 등 다른 뷰를 담고있는 뷰 그룹 들이 있음
 */
public class ViewGroupAdapters {

    /**
     * 리사이클러뷰에 뷰여줄 item 들을 어댑터에 추가
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter({"items"})
    public static <T, VH extends RecyclerView.ViewHolder> void setItems(
            @NonNull final RecyclerView recyclerView,
            @Nullable final List<T> items) {
        final ListAdapter<T, VH> adapter = (ListAdapter<T, VH>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.submitList(items == null ? null : new ArrayList<>(items));
        }
    }

    /**
     * 리사이클러뷰의 orientation 변경
     *
     * @param orientation LinearLayoutManager.VERTICAL or HORIZONTAL
     */
    @BindingAdapter({"android:orientation"})
    public static void setLayoutOrientation(@NonNull final RecyclerView recyclerView,
                                            final int orientation) {
        ((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager()))
                .setOrientation(orientation);
    }

    @BindingAdapter({"android:onClick"})
    public static void setOnClickListener(@NonNull final Group group,
                                          @NonNull final View.OnClickListener listener) {
        for (int id : group.getReferencedIds()) {
            group.getRootView().findViewById(id).setOnClickListener(listener);
        }
    }
}
