package com.fikrabd.mehna_w_amal.home.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.adapter.CategoryAdapter;
import com.fikrabd.mehna_w_amal.base.BaseFragment;
import com.fikrabd.mehna_w_amal.callbacks.InternetListener;
import com.fikrabd.mehna_w_amal.databinding.FragmentCategoryBinding;
import com.fikrabd.mehna_w_amal.databinding.FragmentContractsBinding;
import com.fikrabd.mehna_w_amal.home.MainInterface;
import com.fikrabd.mehna_w_amal.home.MainPresenter;
import com.fikrabd.mehna_w_amal.models.Category;
import com.fikrabd.mehna_w_amal.network.fragment.NoInternetConnectionFragment;
import com.fikrabd.mehna_w_amal.utilities.Transactions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;


public class ContractsFragment extends BaseFragment<MainPresenter, FragmentContractsBinding> implements InternetListener {




    public static ContractsFragment newInstance() {
        return new ContractsFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_contracts;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
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
        initToolbar();


    }


    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(viewDataBinding.loginToolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        viewDataBinding.loginToolbar.setNavigationOnClickListener(view -> onBackPresseds());
        getActivity().setTitle("");
    }



    private void onBackPresseds() {

        getFragmentManager().popBackStack();

    }




    @Override
    public void internetConnected() {


    }

    @Override
    public void internetNotConnected() {
        if (getFragmentManager() != null)
            Transactions.replaceFragmentWithAnimation(NoInternetConnectionFragment.newInstance(), R.id.container, true, getFragmentManager());
    }

}
