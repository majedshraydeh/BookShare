package com.fikrabd.mehna_w_amal.select_type;


import android.os.Bundle;
import android.view.View;

import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.base.BaseActivity;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.databinding.ActivitySelectBinding;
import com.fikrabd.mehna_w_amal.home.MainActivity;
import com.fikrabd.mehna_w_amal.login.LoginActivity;
import com.fikrabd.mehna_w_amal.register.RegisterActivity;

public class SelectActivity extends BaseActivity<SelectPresenter, ActivitySelectBinding> {

    boolean isArtistic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding.setActivity(this);

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_select;
    }

    @Override
    protected SelectPresenter createPresenter() {
        return new SelectPresenter();
    }

    public void isArtistic() {

        viewDataBinding.artistic.setVisibility(View.GONE);
        viewDataBinding.user.setVisibility(View.GONE);
        viewDataBinding.individual.setVisibility(View.VISIBLE);
        viewDataBinding.company.setVisibility(View.VISIBLE);
        viewDataBinding.skip.setVisibility(View.GONE);
        isArtistic = true;

    }

    public void isUser() {

        SharedPreferencesManager.getInstance().setUserType("1");
        openActivity(RegisterActivity.class);

    }

    public void isIndividual() {

        SharedPreferencesManager.getInstance().setUserType("2");
        SharedPreferencesManager.getInstance().setCreate(true);
        openActivity(RegisterActivity.class);
    }

    public void isCompany() {
        SharedPreferencesManager.getInstance().setUserType("3");
        SharedPreferencesManager.getInstance().setCreate(true);
        openActivity(RegisterActivity.class);

    }

    @Override
    public void onBackPressed() {

        if (isArtistic) {
            viewDataBinding.artistic.setVisibility(View.VISIBLE);
            viewDataBinding.user.setVisibility(View.VISIBLE);
            viewDataBinding.individual.setVisibility(View.GONE);
            viewDataBinding.company.setVisibility(View.GONE);
            viewDataBinding.skip.setVisibility(View.GONE);
            isArtistic = false;
        } else {
            super.onBackPressed();
        }

    }
}