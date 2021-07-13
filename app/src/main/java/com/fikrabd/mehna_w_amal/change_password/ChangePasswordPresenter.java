package com.fikrabd.mehna_w_amal.change_password;

import android.util.Log;


import com.fikrabd.mehna_w_amal.base.BasePresenter;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.network.response.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ChangePasswordPresenter extends BasePresenter implements ChangePasswordInterface.ChangePasswordPresenter {

    private static final String TAG = "ChangePasswordPresenter";

    private ChangePasswordInterface.ChangePasswordView changePasswordView;

    public ChangePasswordPresenter(ChangePasswordInterface.ChangePasswordView changePasswordView) {
        this.changePasswordView = changePasswordView;
    }

    @Override
    public void changePassword(String currentPassword, String newPassword) {
        Call<BaseResponse> changePassword =
                apiService.changePassword(SharedPreferencesManager.getInstance().getUser().getId(),
                        currentPassword,
                        newPassword);

        changePassword.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.e(TAG, "onResponse: " + response.body());
                        if (response.body().getResult().getStatus().equals("1")) {
                            changePasswordView.passwordChanged(response.body().getResult().getMessage());
                        } else {
                            changePasswordView.passwordNotChanged(response.body().getResult().getMessage());
                        }
                    } else {
                        changePasswordView.errorOccurred(response.body().getResult().getMessage());
                    }
                } else {
                    changePasswordView.errorOccurred(response.body().getResult().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                changePasswordView.errorOccurred(t.getLocalizedMessage());
            }
        });
    }
}