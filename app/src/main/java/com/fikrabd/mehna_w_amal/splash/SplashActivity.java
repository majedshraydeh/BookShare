package com.fikrabd.mehna_w_amal.splash;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;


import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.base.BaseActivity;
import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.databinding.ActivitySplashBinding;
import com.fikrabd.mehna_w_amal.home.MainActivity;
import com.fikrabd.mehna_w_amal.login.LoginActivity;
import com.fikrabd.mehna_w_amal.select_type.SelectActivity;
import com.fikrabd.mehna_w_amal.steppers.SteppersActivity;
import com.fikrabd.mehna_w_amal.utilities.Tools;

import java.util.Locale;

/**
 * Created by Belal Jaradat on 6/27/2021.
 */
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

        Tools.printHashKey(this);
        setupLanguage();
        getTag();
    }

//    private void setupLanguage() {
//        if (getIntent().hasExtra(Const.LANGUAGE_KEY)) {
//            String language = getIntent().getStringExtra(Const.LANGUAGE_KEY);
//
////            Locale locale = new Locale(language);
////            Locale.setDefault(locale);
////            Resources resources = getResources();
////            Configuration config = resources.getConfiguration();
////            config.setLocale(locale);
////            resources.updateConfiguration(config, resources.getDisplayMetrics());
//
//            Configuration config = getBaseContext().getResources().getConfiguration();
//            assert language != null;
//            Locale locale = new Locale(language);
//            Locale.setDefault(locale);
//            Configuration conf = new Configuration(config);
//            conf.locale = locale;
//            getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
//        }
//    }

    private void setupLanguage() {
        if (getIntent().hasExtra(Const.LANGUAGE_KEY)) {
            String language = getIntent().getStringExtra(Const.LANGUAGE_KEY);

            SharedPreferencesManager.getInstance().setLanguage(language);


            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Resources resources = getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());

//            Configuration config = getBaseContext().getResources().getConfiguration();
//            assert language != null;
//            Locale locale = new Locale(language);
//            Locale.setDefault(locale);
//            Configuration conf = new Configuration(config);
//            conf.locale = locale;
//            getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        }
    }


    private void getTag() {
        Intent intent = getIntent();

        if (intent.hasExtra(Const.LANGUAGE_KEY)) {
            String language = getIntent().getStringExtra(Const.LANGUAGE_KEY);

            SharedPreferencesManager.getInstance().setLanguage(language);

            Configuration config = getBaseContext().getResources().getConfiguration();
            assert language != null;
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration conf = new Configuration(config);
            conf.locale = locale;
            getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        }
        presenter.splashThread();
    }


    @Override
    public void splashEnded() {
        if (SharedPreferencesManager.getInstance().isFirstTime())
            openActivityWithFinish(SteppersActivity.class);
        else if(SharedPreferencesManager.getInstance().isLoggedIn())
            openActivityWithFinish(MainActivity.class);
        else
            openActivityWithFinish(LoginActivity.class);
    }

    @Override
    public void splashError() {
        Tools.showMessage(getString(R.string.error_splash_screen));
    }

}