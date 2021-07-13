package com.fikrabd.mehna_w_amal.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.adapter.holders.SubCategoryViewHolder;
import com.fikrabd.mehna_w_amal.callbacks.ItemClickListener;

import com.fikrabd.mehna_w_amal.databinding.ItemSubCategoryBinding;
import com.fikrabd.mehna_w_amal.models.SubCategory;
import com.fikrabd.mehna_w_amal.utilities.ItemAnimation;
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
        return R.layout.layout_bottom;
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
