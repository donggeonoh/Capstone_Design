package com.donggeon.honmaker.ui.ingredient;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.donggeon.honmaker.R;

public class IngredientAdapter extends ListAdapter<LegacyIngredient, IngredientItemViewHolder> {

    public IngredientAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public IngredientItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientItemViewHolder holder, int position) {
        final LegacyIngredient item = getItem(holder.getAdapterPosition());
        holder.bindTo(item);
    }

    private static DiffUtil.ItemCallback<LegacyIngredient> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<LegacyIngredient>() {
                @Override
                public boolean areItemsTheSame(@NonNull LegacyIngredient oldItem,
                                               @NonNull LegacyIngredient newItem) {
                    return oldItem.getImageResId() == newItem.getImageResId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull LegacyIngredient oldItem,
                                                  @NonNull LegacyIngredient newItem) {
                    return oldItem.equals(newItem);
                }
            };
}
