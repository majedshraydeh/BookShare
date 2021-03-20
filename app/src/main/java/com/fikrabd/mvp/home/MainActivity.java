package com.fikrabd.mvp.home;

import android.os.Bundle;

import com.fikrabd.mvp.R;
import com.fikrabd.mvp.base.BaseActivity;
import com.fikrabd.mvp.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<MainPresenter ,ActivityMainBinding> implements MainInterface.MainView {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}