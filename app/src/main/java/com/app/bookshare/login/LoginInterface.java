package com.app.bookshare.login;



public interface LoginInterface {
    interface LoginView {
        void onLoginSuccess(String userId);

        void onLoginFailure(String message);

    }

    interface LoginPresenter {
        void onLogin(String email, String password);

    }
}
