package com.app.bookshare.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bookshare.R;
import com.app.bookshare.adapter.holders.MaterialListViewHolder;
import com.app.bookshare.callbacks.PostListener;
import com.app.bookshare.databinding.ItemMaterialBinding;
import com.app.bookshare.models.Material;
import com.app.bookshare.utilities.ItemAnimation;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MaterialListAdapter extends RecyclerView.Adapter<MaterialListViewHolder> implements Filterable {

    private List<Material> materials;
    private List<Material> filterMaterials;
    private int animation_type = 0;
    private PostListener postListener;

    @NonNull
    @Override
    public MaterialListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMaterialBinding itemMaterialBinding = ItemMaterialBinding.inflate(layoutInflater, parent, false);

        return new MaterialListViewHolder(itemMaterialBinding, postListener);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MaterialListViewHolder holder, int position) {

        Material material = filterMaterials.get(position);

        holder.bind(material);

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_material;
    }

    @Override
    public int getItemCount() {
        if (filterMaterials != null) {
            return filterMaterials.size();
        }
        return 0;
    }

    public Material getMaterial(int position) {
        return filterMaterials.get(position);
    }

    public void setPostListener(PostListener postListener) {
        this.postListener = postListener;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
        filterMaterials=materials;
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


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterMaterials = materials;
                } else {
                    List<Material> filteredList = new ArrayList<>();
                        for (Material row : materials) {
                                if (row.getSubject().toLowerCase().contains(charString.toLowerCase()))
                                    filteredList.add(row);
                        }
                            filterMaterials = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterMaterials;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterMaterials = (ArrayList<Material>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
