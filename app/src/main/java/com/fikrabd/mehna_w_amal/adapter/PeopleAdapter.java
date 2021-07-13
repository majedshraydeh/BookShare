package com.fikrabd.mehna_w_amal.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.databinding.ItemPepoleBinding;
import com.fikrabd.mehna_w_amal.models.User;


import org.jetbrains.annotations.NotNull;

public class PeopleAdapter extends BaseQuickAdapter<User, BaseDataBindingHolder<ItemPepoleBinding>> implements LoadMoreModule {

    public PeopleAdapter() {
        super(R.layout.item_pepole);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemPepoleBinding> itemPepoleBindingBaseDataBindingHolder, User user) {
        ItemPepoleBinding itemPepoleBinding = itemPepoleBindingBaseDataBindingHolder.getDataBinding();
        if (itemPepoleBinding != null) {
            itemPepoleBinding.setUser(user);
            itemPepoleBinding.executePendingBindings();
        }
    }
}