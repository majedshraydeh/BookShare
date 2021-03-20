package com.fikrabd.mvp.login;

/**
 * Created by Saif M Jaradat on 3/19/2021.
 */
public interface LoginInterface {

    interface LoginView
    {
        void loginSuccess(String message);

        void loginFailure();
    }

    interface LoginPresenterView
    {
        void onLogin(String email , String password);
    }
}
