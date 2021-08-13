package com.app.bookshares.login;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import com.app.bookshares.R;
import com.app.bookshares.base.BaseActivity;
import com.app.bookshares.conifig.Const;
import com.app.bookshares.conifig.SharedPreferencesManager;
import com.app.bookshares.databinding.ActivityLoginBinding;
import com.app.bookshares.home.MainActivity;
import com.app.bookshares.register.RegisterActivity;
import com.app.bookshares.utilities.Tools;


public class LoginActivity extends BaseActivity<LoginPresenter, ActivityLoginBinding> implements LoginInterface.LoginView {

    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();



    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }



    private void init() {

        viewDataBinding.setActivity(this);
        viewDataBinding.emailLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.emailLayout));
        viewDataBinding.passwordLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.passwordLayout));

        viewDataBinding.stayLogin.setOnCheckedChangeListener((compoundButton, b) -> {
            SharedPreferencesManager.getInstance().setLoggedIn(b);
        });
    }


    @Override
    public void onLoginSuccess(String userId) {
        SharedPreferencesManager.getInstance().setUserID(userId);
        hideProgressDialog();
        openActivityWithFinish(MainActivity.class);

    }

    @Override
    public void onLoginFailure(String message) {
        hideProgressDialog();
        SharedPreferencesManager.getInstance().setUserID(Const.NO_USER);
        Tools.showMessage(message);


    }


    public void signUp() {

        openActivityWithFinish(RegisterActivity.class);
    }

    public void signIn() {

        if (validate()) {
            Tools.hideKeyboard(this);
            showProgressDialog(getString(R.string.please_wait));
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

        if (inputPassword.isEmpty() || inputPassword.length() < 6 || inputPassword.length() > 20) {
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