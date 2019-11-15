package com.donggeon.honmaker.ui.ingredient;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.donggeon.honmaker.databinding.ItemIngredientBinding;

public class IngredientItemViewHolder extends RecyclerView.ViewHolder {

    private ItemIngredientBinding binding;

    IngredientItemViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    void bindTo(@NonNull final LegacyIngredient item) {
        binding.setItem(item);
    }
}