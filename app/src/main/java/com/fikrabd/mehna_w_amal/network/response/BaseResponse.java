package com.fikrabd.mehna_w_amal.network.response;

import com.google.gson.annotations.SerializedName;


public class BaseResponse {

    @SerializedName("result")
    private Result result;

    public BaseResponse() {
    }

    public BaseResponse(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "result=" + result +
                '}';
    }
}
