package com.fikrabd.mehna_w_amal.home.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.adapter.CategoryAdapter;
import com.fikrabd.mehna_w_amal.base.BaseFragment;
import com.fikrabd.mehna_w_amal.callbacks.InternetListener;
import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.databinding.FragmentCategoryBinding;
import com.fikrabd.mehna_w_amal.home.MainInterface;
import com.fikrabd.mehna_w_amal.home.MainPresenter;
import com.fikrabd.mehna_w_amal.models.Category;
import com.fikrabd.mehna_w_amal.models.User;
import com.fikrabd.mehna_w_amal.network.fragment.NoInternetConnectionFragment;
import com.fikrabd.mehna_w_amal.utilities.Transactions;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CategoryFragment extends BaseFragment<MainPresenter, FragmentCategoryBinding> implements InternetListener, MainInterface.CategoryView, OnItemClickListener {


    private CategoryAdapter categoryAdapter;
    private User user=new User();

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

//
//    public static CategoryFragment newInstance(User user) {
//
//        CategoryFragment categoryFragment = new CategoryFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(Const.USER, user);
//        categoryFragment.setArguments(bundle);
//
//        return categoryFragment;
//    }


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
        categoryAdapter.selectedPosition = -1;
        viewDataBinding.recyclerviewCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
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

        user=SharedPreferencesManager.getInstance().getUser();
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
    public void noCategories() {

    }

    @Override
    public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {


        if (adapter instanceof CategoryAdapter) {
            Category category = categoryAdapter.getItem(position);

            if (SharedPreferencesManager.getInstance().getUser().getUserType().equals("1")) {
                Transactions.replaceFragmentWithAnimation(CategorySelectionFragment.newInstance(category.getId(), category.getTitle()), R.id.container, true, getActivity().getSupportFragmentManager());

            } else  {

                user.setCategoryId(category.getId());
                SharedPreferencesManager.getInstance().setUser(user);
                Transactions.replaceFragmentWithAnimation(AddJopFragment.newInstance(category.getId(), category.getTitle()), R.id.container, true, getActivity().getSupportFragmentManager());

            }


        }


    }
}
