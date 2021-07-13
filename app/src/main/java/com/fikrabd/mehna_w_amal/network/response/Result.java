package com.fikrabd.mehna_w_amal.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Saif M Jaradat on 2/3/2021.
 */
public class Result {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    public Result() {
    }

    public Result(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
