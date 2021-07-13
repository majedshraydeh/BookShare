package com.fikrabd.mehna_w_amal.network.response;


import com.fikrabd.mehna_w_amal.models.DefaultData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateResponse extends BaseResponse {

    @SerializedName("data")
    private List<DefaultData> state;


    public List<DefaultData> getState() {
        return state;
    }

    public void setState(List<DefaultData> state) {
        this.state = state;
    }
}
