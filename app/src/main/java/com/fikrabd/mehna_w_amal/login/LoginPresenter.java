package com.fikrabd.mehna_w_amal.login;



import com.fikrabd.mehna_w_amal.base.BasePresenter;
import com.fikrabd.mehna_w_amal.network.response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenter extends BasePresenter implements LoginInterface.LoginPresenter {

    private LoginInterface.LoginView loginView;

    public LoginPresenter(LoginInterface.LoginView loginView) {
        this.loginView = loginView;
    }

    private static final String TAG = "LoginPresenter";

    @Override
    public void onLogin(String email, String password) {

        Call<UserResponse> login=apiService.login(email, password);

        login.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.isSuccessful())
                {
                    if(response.body()!=null)
                    {
                        if(response.body().getResult().getStatus().equalsIgnoreCase("1"))
                        {
                            loginView.onLoginSuccess(response.body().getUser());
                        }else{
                            loginView.onLoginFailure(response.body().getResult().getMessage());
                        }
                    }else{
                        loginView.onLoginFailure(response.body().getResult().getMessage());
                    }
                }else{
                    loginView.onLoginFailure(response.body().getResult().getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

                loginView.onLoginError(t.getLocalizedMessage());
            }
        });

    }
//
//    @Override
//    public void continueWithFacebook(Activity activity, CallbackManager callbackManager) {
//
//        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email"));
//        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.e(TAG, "onSuccess: " + loginResult.getAccessToken());
//                getUserData(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                loginView.facebookLoginFailure();
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.e(TAG, "onError: " + error.getMessage());
//                loginView.facebookLoginFailure();
//            }
//        });
//    }


//    private void getUserData(AccessToken accessToken) {
//
//        GraphRequest request = GraphRequest.newMeRequest(
//                accessToken,
//                (object, response) -> {
//                    Log.e(TAG, "onCompleted: " + object.toString());
//
//                    User user = new User();
//                    try {
////                        user.setFullName(response.getJSONObject().getString("first_name") + " " +
////                                response.getJSONObject().getString("last_name"));
//
//                        if (response.getJSONObject().has("email")) {
//                            String email = response.getJSONObject().getString("email");
//                            if (email.isEmpty())
//                                user.setEmail(accessToken.getUserId() + Const.BASE_EMAIL_ENDPOINT);
//                            else
//                                user.setEmail(email);
//                        } else {
//                            user.setEmail(accessToken.getUserId() + Const.BASE_EMAIL_ENDPOINT);
//
//                        }
//                        loginView.facebookLoginSuccess(accessToken, user);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//        );
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,first_name,last_name,email");
//        request.setParameters(parameters);
//        request.executeAsync();
//    }
//
//    @Override
//    public void getFCMToken() {
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(task -> {
//                    if (!task.isSuccessful()) {
//                        Log.w(TAG, "getInstanceId failed", task.getException());
//                        return;
//                    }
//                    // Get new Instance ID token
//                    String token = task.getResult().getToken();
//                    loginView.fcmToken(token);
//                });
//    }
}
