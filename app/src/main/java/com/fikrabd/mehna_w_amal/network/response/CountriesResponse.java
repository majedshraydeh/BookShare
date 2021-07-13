package com.fikrabd.mehna_w_amal.network.response;


import com.fikrabd.mehna_w_amal.models.DefaultData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountriesResponse extends BaseResponse {

    @SerializedName("data")
    private List<DefaultData> countries;


    public List<DefaultData> getCountries() {
        return countries;
    }

    public void setCountries(List<DefaultData> countries) {
        this.countries = countries;
    }
}
