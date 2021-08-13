package com.app.bookshare.adapter.holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bookshare.adapter.SubCategoryAdapter;
import com.app.bookshare.callbacks.ItemClickListener;
import com.app.bookshare.databinding.ItemSubCategoryBinding;
import com.app.bookshare.models.SubCategory;


public class SubCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemClickListener itemClickListener;
    private ItemSubCategoryBinding itemSubCategoryBinding;

    public SubCategoryViewHolder(@NonNull ItemSubCategoryBinding itemSubCategoryBinding, ItemClickListener itemClickListener) {
        super(itemSubCategoryBinding.getRoot());

        this.itemClickListener = itemClickListener;
        this.itemSubCategoryBinding = itemSubCategoryBinding;

        itemView.setOnClickListener(this);
    }

    public void bind(SubCategory subCategory) {
        itemSubCategoryBinding.setSubCategory(subCategory);
        itemSubCategoryBinding.executePendingBindings();
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null)
            if (getAdapterPosition() != -1) {
                itemClickListener.onItemClick(new SubCategoryAdapter(), view, getAdapterPosition());
            }
    }
}
