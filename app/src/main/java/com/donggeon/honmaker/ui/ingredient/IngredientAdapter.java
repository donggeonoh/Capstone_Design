package com.donggeon.honmaker.ui.ingredient;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.donggeon.honmaker.R;
import com.donggeon.honmaker.data.Ingredient;
import com.donggeon.honmaker.ui.listener.ItemClickListener;

public class IngredientAdapter extends ListAdapter<Ingredient, IngredientItemViewHolder> {
    
    private ItemClickListener<Ingredient> itemClickListener;
    
    IngredientAdapter() {
        super(DIFF_CALLBACK);
    }
    
    public IngredientAdapter(ItemClickListener<Ingredient> itemClickListener) {
        super(DIFF_CALLBACK);
        
        this.itemClickListener = itemClickListener;
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
        
        holder.itemView.setOnClickListener(__ -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(item);
            }
        });
    }
    
    private static DiffUtil.ItemCallback<Ingredient> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Ingredient>() {
                @Override
                public boolean areItemsTheSame(@NonNull Ingredient oldItem,
                                               @NonNull Ingredient newItem) {
                    return oldItem == newItem;
                }
                
                @Override
                public boolean areContentsTheSame(@NonNull Ingredient oldItem,
                                                  @NonNull Ingredient newItem) {
                    return oldItem.equals(newItem);
                }
            };
}
