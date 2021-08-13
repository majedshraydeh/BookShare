package com.app.bookshare.home.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.bookshare.R;
import com.app.bookshare.base.BaseFragment;
import com.app.bookshare.databinding.NoInternetConnectionBinding;
import com.app.bookshare.home.MainPresenter;
import com.app.bookshare.utilities.Tools;


public class NoInternetConnectionFragment extends BaseFragment<MainPresenter, NoInternetConnectionBinding> implements View.OnClickListener {

    private static final String TAG = "NoInternetConnectionFragment";


    public static NoInternetConnectionFragment newInstance() {

        return new NoInternetConnectionFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.no_internet_connection;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }


    private void init() {
        viewDataBinding.reloadPage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (Tools.isNetworkAvailable(getContext())) {
            if (getActivity() != null)
                getActivity().onBackPressed();
        } else {
            Tools.showMessage(getString(R.string.no_internet_connection));
        }
    }
}