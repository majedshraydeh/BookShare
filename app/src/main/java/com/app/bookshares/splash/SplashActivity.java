package com.app.bookshares.splash;

import android.os.Bundle;

import com.app.bookshares.R;
import com.app.bookshares.base.BaseActivity;
import com.app.bookshares.conifig.SharedPreferencesManager;
import com.app.bookshares.databinding.ActivitySplashBinding;
import com.app.bookshares.home.MainActivity;
import com.app.bookshares.login.LoginActivity;
import com.app.bookshares.utilities.Tools;


public class SplashActivity extends BaseActivity<SplashPresenter, ActivitySplashBinding> implements SplashInterface.SplashView {

    private static final String TAG = "SplashActivity";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.splashThread();
    }



    @Override
    public void splashEnded() {

         if(SharedPreferencesManager.getInstance().isLoggedIn())
            openActivityWithFinish(MainActivity.class);
        else
            openActivityWithFinish(LoginActivity.class);
    }

    @Override
    public void splashError() {
       Tools.showMessage(getString(R.string.error_splash_screen));
    }

}