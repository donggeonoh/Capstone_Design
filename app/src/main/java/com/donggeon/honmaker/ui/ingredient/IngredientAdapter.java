package com.donggeon.honmaker.ui.ingredient;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.donggeon.honmaker.R;

public class IngredientAdapter extends ListAdapter<Ingredient, IngredientItemViewHolder> {

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
        final Ingredient item = getItem(holder.getAdapterPosition());
        holder.bindTo(item);
    }

    private static DiffUtil.ItemCallback<Ingredient> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Ingredient>() {
                @Override
                public boolean areItemsTheSame(@NonNull Ingredient oldItem,
                                               @NonNull Ingredient newItem) {
                    return oldItem.getImageResId() == newItem.getImageResId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Ingredient oldItem,
                                                  @NonNull Ingredient newItem) {
                    return oldItem.equals(newItem);
                }
            };
}
