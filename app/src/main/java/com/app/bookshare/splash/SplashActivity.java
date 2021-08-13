package com.app.bookshare.splash;

import android.os.Bundle;

import com.app.bookshare.R;
import com.app.bookshare.base.BaseActivity;
import com.app.bookshare.conifig.SharedPreferencesManager;
import com.app.bookshare.databinding.ActivitySplashBinding;
import com.app.bookshare.home.MainActivity;
import com.app.bookshare.login.LoginActivity;
import com.app.bookshare.utilities.Tools;


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