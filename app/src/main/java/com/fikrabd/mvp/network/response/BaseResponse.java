package com.fikrabd.mvp.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Saif M Jaradat on 2/3/2021.
 */
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
