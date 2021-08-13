package com.app.bookshare.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.app.bookshare.R;
import com.app.bookshare.databinding.ItemCategoryBinding;
import com.app.bookshare.models.Category;


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