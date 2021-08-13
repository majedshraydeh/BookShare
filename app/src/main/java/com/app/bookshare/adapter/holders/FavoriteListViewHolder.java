package com.app.bookshare.adapter.holders;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bookshare.R;
import com.app.bookshare.callbacks.PostListener;
import com.app.bookshare.conifig.SharedPreferencesManager;
import com.app.bookshare.databinding.ItemMaterialBinding;
import com.app.bookshare.models.Material;


public class FavoriteListViewHolder extends RecyclerView.ViewHolder  {

    private PostListener postListener;
    private ItemMaterialBinding itemMaterialBinding;

    public FavoriteListViewHolder(@NonNull ItemMaterialBinding itemMaterialBinding, PostListener postListener) {
        super(itemMaterialBinding.getRoot());

        this.postListener = postListener;
        this.itemMaterialBinding = itemMaterialBinding;



        itemMaterialBinding.favorite.setOnClickListener(view ->postListener.makeFavorite(view,getAdapterPosition(),itemMaterialBinding));
        itemMaterialBinding.whats.setOnClickListener(view ->postListener.goToWhatsApp(view,getAdapterPosition()));
        itemMaterialBinding.message.setOnClickListener(view ->postListener.sendEmail(view,getAdapterPosition()));
        itemMaterialBinding.call.setOnClickListener(view ->postListener.makeCall(view,getAdapterPosition()));
    }

    public void bind(Material material) {

        if (material.getListFavorite().contains(SharedPreferencesManager.getInstance().getUser().getUser_id())) {
            itemMaterialBinding.favorite.setImageResource(R.drawable.favorite);
        }else{

            itemMaterialBinding.favorite.setImageResource(R.drawable.un_favorite);
        }
        itemMaterialBinding.setItem(material);
        itemMaterialBinding.executePendingBindings();
    }

}
