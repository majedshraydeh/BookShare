package com.fikrabd.mehna_w_amal.adapter.holders;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fikrabd.mehna_w_amal.adapter.CountryAdapter;
import com.fikrabd.mehna_w_amal.callbacks.ItemClickListener;
import com.fikrabd.mehna_w_amal.databinding.ItemCountryBinding;
import com.fikrabd.mehna_w_amal.models.DefaultData;


public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemClickListener itemClickListener;
    private ItemCountryBinding itemCountryBinding;

    public CountryViewHolder(@NonNull ItemCountryBinding itemCountryBinding, ItemClickListener itemClickListener) {
        super(itemCountryBinding.getRoot());

        this.itemClickListener = itemClickListener;
        this.itemCountryBinding = itemCountryBinding;

        itemView.setOnClickListener(this);
    }

    public void bind(DefaultData country) {
        itemCountryBinding.setCountry(country);
        itemCountryBinding.executePendingBindings();
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null)
            if (getAdapterPosition() != -1) {
                itemClickListener.onItemClick(new CountryAdapter(), view, getAdapterPosition());
            }
    }
}
