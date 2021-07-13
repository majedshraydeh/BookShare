package com.fikrabd.mehna_w_amal.login;


import com.fikrabd.mehna_w_amal.models.User;



public interface LoginInterface {
    interface LoginView {
        void onLoginSuccess(User user);

        void onLoginFailure(String message);

//        void facebookLoginSuccess(AccessToken accessToken, User user);
//
//        void facebookLoginFailure();
//
//        void noInternetConnection();
//
//        void fcmToken(String fcmToken);

        void onLoginError(String message);
    }

    interface LoginPresenter {
        void onLogin(String email, String password);

//        void continueWithFacebook(Activity activity, CallbackManager callbackManager);
//
//        void getFCMToken();
    }
}
