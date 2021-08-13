package com.app.bookshare.register;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import com.app.bookshare.R;
import com.app.bookshare.base.BaseActivity;
import com.app.bookshare.databinding.ActivityRegisterBinding;
import com.app.bookshare.login.LoginActivity;
import com.app.bookshare.models.User;
import com.app.bookshare.utilities.Tools;


public class RegisterActivity extends BaseActivity<RegisterPresenter, ActivityRegisterBinding> implements RegisterInterface.RegisterView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }


    private void initView() {

        viewDataBinding.setActivity(this);
        viewDataBinding.nameLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.nameLayout));
        viewDataBinding.phoneLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.phoneLayout));
        viewDataBinding.emailLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.emailLayout));
        viewDataBinding.passwordLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.passwordLayout));

    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }


    public void signUp() {

        if (validate()) {
            Tools.hideKeyboard(this);
            showProgressDialog(getString(R.string.please_wait));
            presenter.createUser(generateUser(),viewDataBinding.password.getText().toString());
        } else {
            Tools.showMessage(getString(R.string.valid_info));
        }
    }

    public void signIn() {
        openActivityWithFinish(LoginActivity.class);
    }

    private User generateUser() {

        User user = new User();

        user.setUser_name(viewDataBinding.name.getText().toString());
        user.setEmail(viewDataBinding.email.getText().toString());
        user.setPhone_number(viewDataBinding.phone.getText().toString());
        return user;
    }

    @Override
    public void onRegisterSuccess(String message) {

        hideProgressDialog();
        Tools.showMessage(message);
        openActivityWithFinish(LoginActivity.class);

    }

    @Override
    public void onRegisterFailure(String message) {

        hideProgressDialog();
        Tools.showMessage(message);

    }


    public boolean validate() {

        String inputEmail = viewDataBinding.emailLayout.getEditText().getText().toString();
        String inputName = viewDataBinding.nameLayout.getEditText().getText().toString();
        String inputMobile = viewDataBinding.phoneLayout.getEditText().getText().toString();
        String inputPassword = viewDataBinding.passwordLayout.getEditText().getText().toString();

        if (inputName.isEmpty() || inputName.length() < 3) {
            viewDataBinding.nameLayout.setErrorEnabled(true);
            viewDataBinding.nameLayout.setError(getString(R.string.valid_name));
            return false;
        } else {
            viewDataBinding.nameLayout.setErrorEnabled(false);
        }



        if (inputMobile.isEmpty() || inputMobile.length() < 10) {
            viewDataBinding.phoneLayout.setErrorEnabled(true);
            viewDataBinding.phoneLayout.setError(getString(R.string.valid_phone));
            return false;
        } else {
            viewDataBinding.phoneLayout.setErrorEnabled(false);
        }

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

                case R.id.name_layout:
                case R.id.phone_layout:
                case R.id.email_layout:
                case R.id.password_layout:
                    validate();
                    break;

            }
        }
    }
}