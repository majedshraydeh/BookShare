package com.fikrabd.mehna_w_amal.network.response;


import com.fikrabd.mehna_w_amal.models.Category;
import com.fikrabd.mehna_w_amal.models.DefaultData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse extends BaseResponse {

    @SerializedName("data")
    private List<Category> categories;


    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
