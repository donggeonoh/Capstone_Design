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

public class ViewGroupAdapters {

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
