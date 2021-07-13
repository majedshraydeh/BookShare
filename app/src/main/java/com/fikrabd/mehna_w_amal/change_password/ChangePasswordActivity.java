package com.fikrabd.mehna_w_amal.change_password;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;


import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.base.BaseActivity;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.databinding.ActivityChangePasswordBinding;
import com.fikrabd.mehna_w_amal.utilities.Tools;
import com.royrodriguez.transitionbutton.TransitionButton;


public class ChangePasswordActivity extends BaseActivity<ChangePasswordPresenter, ActivityChangePasswordBinding> implements ChangePasswordInterface.ChangePasswordView {


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected ChangePasswordPresenter createPresenter() {
        return new ChangePasswordPresenter(this);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListeners();
        initToolbar();
    }

    @SuppressLint("SetTextI18n")
    private void initToolbar() {
        setSupportActionBar(viewDataBinding.homeToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        viewDataBinding.homeToolbar.setTitleTextAppearance(this, R.style.RobotoTextViewStyle);
        setTitle(getString(R.string.change_password));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initListeners() {

        viewDataBinding.setActivity(this);
        viewDataBinding.currentPasswordLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.currentPasswordLayout));
        viewDataBinding.newPasswordLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.newPasswordLayout));
    }

    @Override
    public void passwordChanged(String message) {
        viewDataBinding.currentPassword.setText("");
        viewDataBinding.newPassword.setText("");
        Tools.showMessage(message);

    }

    @Override
    public void passwordNotChanged(String message) {
        Tools.showMessage(message);
    }

    @Override
    public void errorOccurred(String message) {
    }

    public void onReset(View view) {

        if (validate()) {
            Tools.hideKeyboard(this);
            presenter.changePassword(viewDataBinding.currentPassword.getText().toString(),
                    viewDataBinding.newPassword.getText().toString());
        } else {
            Tools.showMessage(getString(R.string.valid_pass));
        }
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

                case R.id.current_password:
                case R.id.new_password:
                    validate();
                    break;
            }
        }
    }

    public boolean validate() {
        String password = viewDataBinding.currentPassword.getText().toString();
        String secondPassword = viewDataBinding.newPassword.getText().toString();

        if (password.isEmpty() || password.length() < 3 || password.length() > 20) {
            viewDataBinding.currentPasswordLayout.setErrorEnabled(true);
            viewDataBinding.currentPasswordLayout.setError(getString(R.string.valid_pass));
            return false;
        } else {
            viewDataBinding.currentPasswordLayout.setErrorEnabled(false);
        }

        if (secondPassword.isEmpty() || secondPassword.length() < 3 || secondPassword.length() > 20) {
            viewDataBinding.newPasswordLayout.setErrorEnabled(true);
            viewDataBinding.newPasswordLayout.setError(getString(R.string.valid_pass));
            return false;
        } else {
            viewDataBinding.newPasswordLayout.setErrorEnabled(false);
        }
        return true;
    }
}
