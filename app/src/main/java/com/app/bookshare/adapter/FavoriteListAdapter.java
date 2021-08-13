package com.app.bookshare.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bookshare.R;
import com.app.bookshare.adapter.holders.FavoriteListViewHolder;
import com.app.bookshare.callbacks.PostListener;
import com.app.bookshare.databinding.ItemMaterialBinding;
import com.app.bookshare.models.Material;
import com.app.bookshare.utilities.ItemAnimation;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListViewHolder> {

    private List<Material> materials;
    private int animation_type = 0;
    private PostListener postListener;

    @NonNull
    @Override
    public FavoriteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMaterialBinding itemMaterialBinding = ItemMaterialBinding.inflate(layoutInflater, parent, false);

        return new FavoriteListViewHolder(itemMaterialBinding, postListener);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavoriteListViewHolder holder, int position) {

        Material material = materials.get(position);

        holder.bind(material);

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_material;
    }

    @Override
    public int getItemCount() {
        if (materials != null) {
            return materials.size();
        }
        return 0;
    }

    public Material getFavorite(int position) {
        return materials.get(position);
    }

    public void setPostListener(PostListener postListener) {
        this.postListener = postListener;
    }

    public void setFavorite(List<Material> materials) {
        this.materials = materials;
        notifyDataSetChanged();
    }


    public void setAnimation_type(int animation_type) {
        this.animation_type = animation_type;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
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
