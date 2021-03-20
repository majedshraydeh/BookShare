package com.fikrabd.mvp.base;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Created by Saif M Jaradat on 3/17/2021.
 */
public abstract class BaseActivity<P, T extends ViewDataBinding> extends AppCompatActivity {
    protected T viewDataBinding;
    protected P presenter;

    protected abstract int getLayoutId();

    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        building();
    }

    private void building() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        presenter = createPresenter();
    }
}
