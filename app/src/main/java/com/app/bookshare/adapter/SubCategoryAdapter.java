package com.app.bookshare.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.app.bookshare.R;
import com.app.bookshare.adapter.holders.SubCategoryViewHolder;
import com.app.bookshare.callbacks.ItemClickListener;
import com.app.bookshare.databinding.ItemSubCategoryBinding;
import com.app.bookshare.models.SubCategory;
import com.app.bookshare.utilities.ItemAnimation;

import java.util.List;


public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryViewHolder> {

    private List<SubCategory> subCategories;
    private int animation_type = 0;
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSubCategoryBinding itemSubCategoryBinding = ItemSubCategoryBinding.inflate(layoutInflater, parent, false);

        return new SubCategoryViewHolder(itemSubCategoryBinding, itemClickListener);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {

        SubCategory subCategory = subCategories.get(position);

        holder.bind(subCategory);

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_sub_category;
    }

    @Override
    public int getItemCount() {
        if (subCategories != null) {
            return subCategories.size();
        }
        return 0;
    }

    public SubCategory getSubCategory(int position) {
        return subCategories.get(position);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setSubCategory(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
        notifyDataSetChanged();
    }


    public void setAnimation_type(int animation_type) {
        this.animation_type = animation_type;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        super.onAttachedToRecyclerView(recyclerView);
    }

    private int lastPosition = -1;
    private boolean on_attach = true;

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
            lastPosition = position;
        }
    }
}
