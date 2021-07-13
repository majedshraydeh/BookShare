package com.fikrabd.mehna_w_amal.adapter.holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fikrabd.mehna_w_amal.adapter.NationalityAdapter;
import com.fikrabd.mehna_w_amal.adapter.StateAdapter;
import com.fikrabd.mehna_w_amal.callbacks.ItemClickListener;
import com.fikrabd.mehna_w_amal.databinding.ItemStateBinding;
import com.fikrabd.mehna_w_amal.models.DefaultData;


public class NationalityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemClickListener itemClickListener;
    private ItemStateBinding itemStateBinding;

    public NationalityViewHolder(@NonNull ItemStateBinding itemStateBinding, ItemClickListener itemClickListener) {
        super(itemStateBinding.getRoot());

        this.itemClickListener = itemClickListener;
        this.itemStateBinding = itemStateBinding;

        itemView.setOnClickListener(this);
    }

    public void bind(DefaultData state) {
        itemStateBinding.setState(state);
        itemStateBinding.executePendingBindings();
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null)
            if (getAdapterPosition() != -1) {
                itemClickListener.onItemClick(new NationalityAdapter(), view, getAdapterPosition());
            }
    }
}
