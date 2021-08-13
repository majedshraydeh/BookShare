package com.app.bookshares.splash;

import com.app.bookshares.conifig.Const;


public class SplashPresenter  implements SplashInterface.SplashPresenter {


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
                splashView.splashError();
            } finally {
                splashView.splashEnded();
            }
        });
        thread.start();
    }


}
