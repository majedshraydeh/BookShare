package com.fikrabd.mehna_w_amal.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.adapter.holders.CountryViewHolder;
import com.fikrabd.mehna_w_amal.callbacks.ItemClickListener;
import com.fikrabd.mehna_w_amal.databinding.ItemCountryBinding;
import com.fikrabd.mehna_w_amal.models.DefaultData;
import com.fikrabd.mehna_w_amal.utilities.ItemAnimation;

import java.util.List;


public class CountryAdapter extends RecyclerView.Adapter<CountryViewHolder> {

    private List<DefaultData> countries;
    private int animation_type = 0;
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemCountryBinding itemCountryBinding = ItemCountryBinding.inflate(layoutInflater, parent, false);

        return new CountryViewHolder(itemCountryBinding, itemClickListener);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {

        DefaultData country = countries.get(position);

        holder.bind(country);

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_bottom;
    }

    @Override
    public int getItemCount() {
        if (countries != null) {
            return countries.size();
        }
        return 0;
    }

    public DefaultData getCountries(int position) {
        return countries.get(position);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setCountries(List<DefaultData> countries) {
        this.countries = countries;
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
