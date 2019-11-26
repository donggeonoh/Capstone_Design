package com.donggeon.honmaker.ui.food;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.donggeon.honmaker.R;
import com.donggeon.honmaker.data.Food;
import com.donggeon.honmaker.ui.listener.ItemClickListener;
import com.donggeon.honmaker.ui.listener.RatingClickListener;

public class FoodAdapter extends ListAdapter<Food, FoodItemViewHolder> {

    @Nullable
    private ItemClickListener<Food> itemClickListener;
    private RatingClickListener<Food> ratingClickListener;

    FoodAdapter(@Nullable ItemClickListener<Food> itemClickListener, RatingClickListener<Food> ratingClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
        this.ratingClickListener = ratingClickListener;
    }
    
    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false));
    }
    
    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {
        final Food item = getItem(holder.getAdapterPosition());
        holder.bindTo(item);
        
        holder.itemView.setOnClickListener(__ -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(item);
            }
        });
        
        holder.getBinding().btRating.setOnClickListener(__ -> {
            if (ratingClickListener != null) {
                ratingClickListener.onRatingButtonClick(item);
            }
        });
    }

    private static DiffUtil.ItemCallback<Food> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Food>() {
                @Override
                public boolean areItemsTheSame(@NonNull Food oldItem,
                                               @NonNull Food newItem) {
                    return oldItem.getFoodName() == newItem.getFoodName();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Food oldItem,
                                                  @NonNull Food newItem) {
                    return oldItem.equals(newItem);
                }
            };
}