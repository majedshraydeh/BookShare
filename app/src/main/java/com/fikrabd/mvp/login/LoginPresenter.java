package com.fikrabd.mvp.login;

import android.util.Log;

import com.fikrabd.mvp.network.ApiClient;
import com.fikrabd.mvp.network.ApiService;
import com.fikrabd.mvp.network.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Saif M Jaradat on 3/19/2021.
 */
public class LoginPresenter implements LoginInterface.LoginPresenterView {

    private static final String TAG = "LoginPresenter";
    private LoginInterface.LoginView loginView;
    private ApiService apiService;

    public LoginPresenter() {

    }

    public LoginPresenter(LoginInterface.LoginView loginView) {
        this.loginView = loginView;
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    @Override
    public void onLogin(String email, String password) {

        Call<LoginResponse> loginResponseCall = apiService.login
                (email,password ,"0" , "" , "" , "" , "en");

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful())
                {
                    if(response.body() != null)
                    {
                        if(response.body().getResult().getStatus().equals("1"))
                        {
                            loginView.loginSuccess(response.body().getResult().getMessage());
                            return;
                        }
                    }
                }
                loginView.loginFailure();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginView.loginFailure();
            }
        });

    }
}
