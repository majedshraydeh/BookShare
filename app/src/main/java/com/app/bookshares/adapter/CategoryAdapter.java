package com.app.bookshares.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.app.bookshares.R;
import com.app.bookshares.databinding.ItemCategoryBinding;
import com.app.bookshares.models.Category;


import org.jetbrains.annotations.NotNull;

public class CategoryAdapter extends BaseQuickAdapter<Category, BaseDataBindingHolder<ItemCategoryBinding>> implements LoadMoreModule {



    public CategoryAdapter() {
        super(R.layout.item_category);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemCategoryBinding> itemCategoryBindingBaseDataBindingHolder, Category category) {
        ItemCategoryBinding itemCategoryBinding = itemCategoryBindingBaseDataBindingHolder.getDataBinding();
        if (itemCategoryBinding != null) {
            itemCategoryBinding.setCategory(category);
            itemCategoryBinding.setLoading(itemCategoryBinding.loading);
            itemCategoryBinding.executePendingBindings();
        }
    }
}