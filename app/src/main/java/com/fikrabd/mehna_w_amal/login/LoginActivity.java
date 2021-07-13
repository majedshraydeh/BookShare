package com.fikrabd.mehna_w_amal.login;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.base.BaseActivity;
import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.databinding.ActivityLoginBinding;
import com.fikrabd.mehna_w_amal.home.MainActivity;
import com.fikrabd.mehna_w_amal.models.User;
import com.fikrabd.mehna_w_amal.select_type.SelectActivity;
import com.fikrabd.mehna_w_amal.utilities.Tools;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Objects;


public class LoginActivity extends BaseActivity<LoginPresenter, ActivityLoginBinding> implements LoginInterface.LoginView, View.OnClickListener {

    private static final String TAG = "LoginActivity";

//    private CallbackManager callbackManager;
//    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();


    }


//    private void initToolbar() {
//      setSupportActionBar(viewDataBinding.loginToolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        viewDataBinding.loginToolbar.setNavigationOnClickListener(view -> onBackPressed());
//        setTitle("");
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.lang, menu);
//
//       // final MenuItem item = menu.findItem(R.id.user_profile_eya);
//        return true;
//    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }


    private void init() {


        // callbackManager = CallbackManager.Factory.create();

        viewDataBinding.emailLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.emailLayout));
        viewDataBinding.passwordLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.passwordLayout));
        viewDataBinding.signIn.setOnClickListener(this);
        viewDataBinding.signUp.setOnClickListener(this);

    }

//    private void configureGoogleSignIn() {
//        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
//    }

    @Override
    public void onLoginSuccess(User user) {
        hideProgressDialog();
        SharedPreferencesManager.getInstance().setCreate(false);
        SharedPreferencesManager.getInstance().setLoggedIn(true);
        SharedPreferencesManager.getInstance().setUser(user);

        openActivityWithFinish(MainActivity.class);

    }

    @Override
    public void onLoginFailure(String message) {
        hideProgressDialog();
        SharedPreferencesManager.getInstance().setUserID(Const.NO_USER);
        Tools.showMessage(message);


    }

//    @Override
//    public void facebookLoginSuccess(AccessToken accessToken, User user) {
//
////        String imageUrl = "https://graph.facebook.com/" + accessToken.getUserId() + "/picture?type=large&redirect=true&width=500&height=500";
////        Log.e(TAG, "facebookLoginSuccess: " + user.toString());
////        SharedPreferencesManager.getInstance().setIsSocial(true);
////        showProgressDialog("", getString(R.string.authenticating));
////
////        );
//    }

//    @Override
//    public void facebookLoginFailure() {
//        Tools.showMessage(getString(R.string.something_wrong));
//        SharedPreferencesManager.getInstance().setUserID(Const.NO_USER);
//        LoginManager.getInstance().logOut();
//        SharedPreferencesManager.getInstance().resetFields();
//        GoogleSignInOptions gso = new GoogleSignInOptions.
//                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
//                build();
//
//        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
//        googleSignInClient.signOut();
//
//    }
//
//    @Override
//    public void noInternetConnection() {
//        Tools.showMessage(getString(R.string.no_internet_connection));
//        SharedPreferencesManager.getInstance().setUserID(Const.NO_USER);
//        SharedPreferencesManager.getInstance().resetFields();
//        hideProgressDialog();
//    }
//
//    @Override
//    public void fcmToken(String fcmToken) {
//        SharedPreferencesManager.getInstance().setFCMToken(fcmToken);
//        hideProgressDialog();
//    }

    @Override
    public void onLoginError(String message) {
        hideProgressDialog();
        Tools.showMessage(message);

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == Const.REQUEST_SIGN_IN_GOOGLE) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//            return;
//        }
//
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }


//    private void loginWithFacebook() {
//
//        presenter.continueWithFacebook(LoginActivity.this, callbackManager);
//    }

//    private void loginWithGoogle() {
//
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, Const.REQUEST_SIGN_IN_GOOGLE);
//    }

//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//
//            // Signed in successfully, show authenticated UI.
//            googleSignInSuccessfully(account);
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            googleSignInUnSuccessfully();
//        }
//    }
//
//    private void googleSignInSuccessfully(GoogleSignInAccount googleSignInAccount) {
//
//        Log.e(TAG, "googleSignInSuccessfully: " + googleSignInAccount.getEmail());
//
//        showProgressDialog("", getString(R.string.authenticating));
//
//        String image = "";
//        if (googleSignInAccount.getPhotoUrl() != null)
//            if (!googleSignInAccount.getPhotoUrl().toString().isEmpty()) {
//                image = googleSignInAccount.getPhotoUrl().toString();
//            }
//
//        presenter.onLogin(
//                googleSignInAccount.getEmail(),
//                ""
//        );
//
//      //  SharedPreferencesManager.getInstance().setIsSocial(true);
//    }

//    private void googleSignInUnSuccessfully() {
//        Tools.showMessage(getString(R.string.something_wrong));
//        SharedPreferencesManager.getInstance().setUserID(Const.NO_USER);
//        LoginManager.getInstance().logOut();
//        SharedPreferencesManager.getInstance().resetFields();
//        GoogleSignInOptions gso = new GoogleSignInOptions.
//                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
//                build();
//
//        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
//        googleSignInClient.signOut();
//
//    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.sign_up:
                signUp();
                break;

            case R.id.sign_in:
                signIn();
                break;

        }
    }

    private void signUp() {

        openActivityWithFinish(SelectActivity.class);
    }

    private void signIn() {

        if (validate()) {
            Tools.hideKeyboard(this);
            showProgressDialog("", getString(R.string.authenticating));
            presenter.onLogin(
                    viewDataBinding.email.getText().toString(),
                    viewDataBinding.password.getText().toString());

        } else {
            Tools.showMessage(getString(R.string.valid_info));
        }
    }


    public boolean validate() {

        String inputEmail = viewDataBinding.emailLayout.getEditText().getText().toString();
        String inputPassword = viewDataBinding.passwordLayout.getEditText().getText().toString();


        if (inputEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {
            viewDataBinding.emailLayout.setErrorEnabled(true);
            viewDataBinding.emailLayout.setError(getString(R.string.enter_valid_emails));
            return false;
        } else {
            viewDataBinding.emailLayout.setErrorEnabled(false);
        }


        if (inputPassword.isEmpty()) {
            viewDataBinding.passwordLayout.setErrorEnabled(true);
            viewDataBinding.passwordLayout.setError(getString(R.string.valid_pass));
            return false;
        } else {
            viewDataBinding.passwordLayout.setErrorEnabled(false);
        }


        return true;
    }


    public class RealTimeTextWatcher implements TextWatcher {

        private View view;


        public RealTimeTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void afterTextChanged(Editable editable) {

            switch (view.getId()) {

                case R.id.email_layout:
                case R.id.password_layout:
                    validate();
                    break;

            }
        }
    }

}