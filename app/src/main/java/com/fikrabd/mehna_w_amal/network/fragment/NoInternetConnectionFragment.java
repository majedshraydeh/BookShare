package com.fikrabd.mehna_w_amal.network.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.base.BaseFragment;
import com.fikrabd.mehna_w_amal.base.BasePresenter;
import com.fikrabd.mehna_w_amal.databinding.NoInternetConnectionBinding;
import com.fikrabd.mehna_w_amal.utilities.Tools;


public class NoInternetConnectionFragment extends BaseFragment<BasePresenter, NoInternetConnectionBinding> implements View.OnClickListener {

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
    protected BasePresenter createPresenter() {
        return new BasePresenter();
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