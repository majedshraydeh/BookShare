package com.fikrabd.mehna_w_amal.network.response;

import com.fikrabd.mehna_w_amal.models.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeopleResponse extends BaseResponse {

    @SerializedName("data")
    private List<User> users;


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "PeopleResponse{" +
                "users=" + users +
                '}';
    }
}
