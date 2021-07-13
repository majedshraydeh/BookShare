package com.fikrabd.mehna_w_amal.network.response;


import com.fikrabd.mehna_w_amal.models.DefaultData;
import com.fikrabd.mehna_w_amal.models.Rating;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RateResponse extends BaseResponse {

    @SerializedName("rating")
    private Rating rating;

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "RateResponse{" +
                "rating=" + rating +
                '}';
    }
}
