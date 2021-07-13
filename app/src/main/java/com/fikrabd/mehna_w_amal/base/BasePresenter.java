package com.fikrabd.mehna_w_amal.base;


import com.fikrabd.mehna_w_amal.network.ApiClient;
import com.fikrabd.mehna_w_amal.network.ApiService;

/**
 * Created by Belal Jaradat on 6/27/2021.
 */
public class BasePresenter {

    public ApiService apiService;

    public BasePresenter() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }
}
