package com.fikrabd.mvp.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.fikrabd.mvp.R;
import com.fikrabd.mvp.base.BaseActivity;
import com.fikrabd.mvp.databinding.ActivityLoginBinding;
import com.fikrabd.mvp.databinding.ActivityMainBinding;

public class LoginActivity extends BaseActivity<LoginPresenter , ActivityLoginBinding> implements LoginInterface.LoginView {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding.setActivity(this);
    }

    public void onLogin(View view)
    {
        presenter.onLogin(viewDataBinding.email.getText().toString() , viewDataBinding.password.getText().toString());
    }

    @Override
    public void loginSuccess(String message) {
        Toast.makeText(this , message , Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginFailure() {
        Toast.makeText(this , "Failure" , Toast.LENGTH_LONG).show();
    }
}