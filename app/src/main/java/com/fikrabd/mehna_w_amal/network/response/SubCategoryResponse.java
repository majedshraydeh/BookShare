package com.fikrabd.mehna_w_amal.network.response;


import com.fikrabd.mehna_w_amal.models.SubCategory;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Belal Jaradat on 7/7/2021.
 */
public class SubCategoryResponse extends BaseResponse {

    @SerializedName("data")
    private List<SubCategory> subCategories;

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public String toString() {
        return "SubCategoryResponse{" +
                "subCategories=" + subCategories +
                '}';
    }
}
