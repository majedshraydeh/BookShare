package com.app.bookshares.login;


import com.app.bookshares.R;
import com.app.bookshares.app.App;
import com.google.firebase.auth.FirebaseAuth;


public class LoginPresenter  implements LoginInterface.LoginPresenter {

    private LoginInterface.LoginView loginView;
    private FirebaseAuth firebaseAuth;


    public LoginPresenter(LoginInterface.LoginView loginView) {
        this.loginView = loginView;
        firebaseAuth = FirebaseAuth.getInstance();

    }


    private static final String TAG = "LoginPresenter";


    @Override
    public void onLogin(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email.trim(), password.trim())
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        loginView.onLoginSuccess(task.getResult().getUser().getUid());

                    } else {
                        loginView.onLoginFailure(App.getAppContext().getString(R.string.authentication_failed));

                    }

                })
                .addOnFailureListener(e -> {
                    loginView.onLoginFailure(e.getLocalizedMessage());
                });
    }

}
