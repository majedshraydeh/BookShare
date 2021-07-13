package com.fikrabd.mehna_w_amal.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatDelegate;


public class App extends Application {



    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static Resources resources;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        App.context = this;
        resources = getResources();
    }


    public static Context getAppContext() {
        return App.context;
    }
}
