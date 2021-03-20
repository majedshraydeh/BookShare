package com.fikrabd.mvp.network;


import com.fikrabd.mvp.conifig.Const;
import com.fikrabd.mvp.network.response.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Saif M Jaradat on 2/3/2021.
 */
public interface ApiService {

    @FormUrlEncoded
    @POST(Const.LOGIN)
    Call<LoginResponse> login(@Field("email") String email,
                              @Field("password") String password,
                              @Field("login_type") String loginType,
                              @Field("fcm_token ") String token,
                              @Field("full_name") String fullName,
                              @Field("social_id") String socialId,
                              @Field("lang") String language);
}
