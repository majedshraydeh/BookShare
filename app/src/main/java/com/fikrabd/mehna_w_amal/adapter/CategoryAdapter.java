package com.fikrabd.mehna_w_amal.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.databinding.ItemCategoryBinding;
import com.fikrabd.mehna_w_amal.models.Category;


import org.jetbrains.annotations.NotNull;

public class CategoryAdapter extends BaseQuickAdapter<Category, BaseDataBindingHolder<ItemCategoryBinding>> implements LoadMoreModule {

    public int selectedPosition = 0;

    public CategoryAdapter() {
        super(R.layout.item_category);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemCategoryBinding> itemCategoryBindingBaseDataBindingHolder, Category category) {
        ItemCategoryBinding itemCategoryBinding = itemCategoryBindingBaseDataBindingHolder.getDataBinding();
        if (itemCategoryBinding != null) {
            itemCategoryBinding.setCategory(category);
            itemCategoryBinding.setLoading(itemCategoryBinding.loading);
            itemCategoryBinding.setPosition(getItemPosition(category));
            itemCategoryBinding.setSelectedPosition(selectedPosition);
            itemCategoryBinding.executePendingBindings();
        }
    }
}