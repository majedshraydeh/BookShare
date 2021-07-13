package com.fikrabd.mehna_w_amal.network.response;

import com.fikrabd.mehna_w_amal.models.User;
import com.google.gson.annotations.SerializedName;

public class UserResponse extends BaseResponse {

    @SerializedName("data")
    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "user=" + user +
                '}';
    }
}
