package com.fikrabd.mehna_w_amal.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.callbacks.JobListener;
import com.fikrabd.mehna_w_amal.databinding.ItemJobBinding;
import com.fikrabd.mehna_w_amal.databinding.ItemPepoleBinding;
import com.fikrabd.mehna_w_amal.models.SubCategory;
import com.fikrabd.mehna_w_amal.models.User;

import org.jetbrains.annotations.NotNull;

public class JobAdapter extends BaseQuickAdapter<SubCategory, BaseDataBindingHolder<ItemJobBinding>> implements LoadMoreModule {

    public JobAdapter() {
        super(R.layout.item_job);
    }

    private JobListener jobListener;

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemJobBinding> itemJobBindingBaseDataBindingHolder, SubCategory subCategory) {
        ItemJobBinding itemJobBinding = itemJobBindingBaseDataBindingHolder.getDataBinding();
        if (itemJobBinding != null) {
            itemJobBinding.setSubCategory(subCategory);
//            itemJobBinding.setLoading(itemJobBinding.loading);
            itemJobBinding.executePendingBindings();

            itemJobBinding.add.setOnClickListener(view ->jobListener.onAddJob(view,getItemPosition(subCategory),itemJobBinding));
            itemJobBinding.remove.setOnClickListener(view ->jobListener.onRemoveJob(view,getItemPosition(subCategory),itemJobBinding));
        }
    }

    public void setJobListener(JobListener jobListener){

        this.jobListener=jobListener;
    }
}