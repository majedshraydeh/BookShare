package com.fikrabd.mehna_w_amal.splash;

import android.util.Log;

import com.fikrabd.mehna_w_amal.base.BasePresenter;
import com.fikrabd.mehna_w_amal.conifig.Const;


/**
 * Created by Belal Jaradat on 6/27/2021.
 */
public class SplashPresenter extends BasePresenter implements SplashInterface.SplashPresenter {

    private static final String TAG = "SplashPresenter";

    private SplashInterface.SplashView splashView;
    private Thread thread;

    public SplashPresenter(SplashInterface.SplashView splashView) {
        this.splashView = splashView;
    }

    @Override
    public void splashThread() {

        thread = new Thread(() -> {
            try {
                Thread.sleep(Const.SPLASH_THREAD);
            } catch (Exception ex) {
                Log.e(TAG, "run: " + ex.getMessage());
                splashView.splashError();
            } finally {
                splashView.splashEnded();
            }
        });
        thread.start();
    }


}
