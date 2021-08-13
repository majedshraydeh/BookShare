package com.app.bookshare.splash;


public interface SplashInterface {
    interface SplashView {
        void splashEnded();

        void splashError();
    }

    interface SplashPresenter {
        void splashThread();


    }
}
