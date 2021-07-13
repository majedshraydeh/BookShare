package com.fikrabd.mehna_w_amal.splash;

/**
 * Created by Saif M Jaradat on 2/3/2021.
 */
public interface SplashInterface {
    interface SplashView {
        void splashEnded();

        void splashError();
    }

    interface SplashPresenter {
        void splashThread();


    }
}
