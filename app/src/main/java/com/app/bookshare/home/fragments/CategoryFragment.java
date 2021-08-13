package com.app.bookshare.home.fragments;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.app.bookshare.R;
import com.app.bookshare.adapter.CategoryAdapter;
import com.app.bookshare.base.BaseFragment;
import com.app.bookshare.callbacks.InternetListener;
import com.app.bookshare.databinding.FragmentCategoryBinding;
import com.app.bookshare.home.MainInterface;
import com.app.bookshare.home.MainPresenter;
import com.app.bookshare.models.Category;
import com.app.bookshare.utilities.Transactions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CategoryFragment extends BaseFragment<MainPresenter, FragmentCategoryBinding> implements InternetListener, MainInterface.CategoryView, OnItemClickListener {


    private CategoryAdapter categoryAdapter;


    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }



    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_category;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInternetListener(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDataBinding.setFragment(this);

        setupCategoryAdapter();

    }



    private void setupCategoryAdapter() {
        categoryAdapter = new CategoryAdapter();
        viewDataBinding.recyclerviewCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        viewDataBinding.recyclerviewCategories.setAdapter(categoryAdapter);
        categoryAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null)
            getActivity().setTitle(getString(R.string.home));
    }
    @Override
    public void internetConnected() {

        presenter.getCategory();

    }

    @Override
    public void internetNotConnected() {
        if (getFragmentManager() != null)
            Transactions.replaceFragmentWithAnimation(NoInternetConnectionFragment.newInstance(), R.id.container, true, getFragmentManager());
    }

    @Override
    public void allCategories(List<Category> categories) {
        categoryAdapter.setList(categories);
    }

    @Override
    public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {


        if (adapter instanceof CategoryAdapter) {
            Category category = categoryAdapter.getItem(position);

            if(getFragmentManager()!=null)
            Transactions.replaceFragment(SubCategoryFragment.newInstance(category.getName(), category.getId()),R.id.container,true,getFragmentManager());
        }


    }
}
