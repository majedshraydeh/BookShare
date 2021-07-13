package com.fikrabd.mehna_w_amal.network;


import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.models.Category;
import com.fikrabd.mehna_w_amal.models.DefaultData;
import com.fikrabd.mehna_w_amal.models.Stepper;
import com.fikrabd.mehna_w_amal.network.response.BaseResponse;
import com.fikrabd.mehna_w_amal.network.response.CategoryResponse;
import com.fikrabd.mehna_w_amal.network.response.PeopleResponse;
import com.fikrabd.mehna_w_amal.network.response.RateResponse;
import com.fikrabd.mehna_w_amal.network.response.StateResponse;
import com.fikrabd.mehna_w_amal.network.response.CountriesResponse;
import com.fikrabd.mehna_w_amal.network.response.SubCategoryResponse;
import com.fikrabd.mehna_w_amal.network.response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiService {

    @GET(Const.STEPPERS)
    Call<List<Stepper>> getAllStepper();

    @POST(Const.GET_CATEGORY)
    Call<CategoryResponse> getHomeCategories();

    @POST(Const.SUB_CATEGORY_BY_CATEGORY_ID + "{category_id}")
    Call<SubCategoryResponse> getSubCategories(@Path("category_id") String categoryId);

    @POST(Const.GET_ALL_COUNTRIES)
    Call<CountriesResponse> getCountries();


    @POST(Const.GET_ALL_STATE + "{country_id}")
    Call<StateResponse> getState(@Path("country_id") String countryId);

    @FormUrlEncoded
    @POST(Const.CREATE_ACCOUNT)
    Call<UserResponse> createUser(
            @Field("lang") String lang,
            @Field("email") String email,
            @Field("user_type") String userType,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("age") String age,
            @Field("country_id") String countryId,
            @Field("state_id") String stateId,
            @Field("image") String image,
            @Field("password") String password,
            @Field("district") String district,
            @Field("gender") String gender,
            @Field("nationality_id") String nationalityId,
            @Field("job") String job,
            @Field("experience") String experience,
            @Field("have_licence") String have_licence,
            @Field("hourly_salary") String hourly_salary,
            @Field("monthly_salary") String monthly_salary,
            @Field("dayly_salary") String dayly_salary,
            @Field("desire_work") String desireWork,
            @Field("resume") String resume,
            @Field("resume_type") String resumeType,
            @Field("profession_licence") String professionLicence,
            @Field("permanence_type") String permanenceType,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("category") String category,
            @Field("sub_category") String subCategory,
            @Field("commercial_activities") String commercialActivities,
            @Field("commercial_registration_no") String commercialRegistrationNo,
            @Field("static_phone") String staticPhone,
            @Field("website") String website);


    @FormUrlEncoded
    @POST(Const.LOGIN)
    Call<UserResponse> login(@Field("email") String email,
                             @Field("password") String password);


    @POST(Const.USER_BY_SUB_CATEGORY + "{sub_id}")
    Call<PeopleResponse> getPeople(@Path("sub_id") String subId);

    @POST(Const.COMPLETE_PAY + "{uid}")
    Call<UserResponse> payment(@Path("uid") String userId);

    @POST(Const.ONLINE_OFFLINE + "{uid}/{status}")
    Call<UserResponse> changeStatus(@Path("uid") String userId,
                                    @Path("status") String status);

    @POST(Const.SET_RATING + "{current_user_id}/{user_to_rate}/{rate_number}")
    Call<RateResponse> setRate(@Path("current_user_id") String currentUser,
                               @Path("user_to_rate") String userToRate,
                               @Path("rate_number") String rateNumber);

    @FormUrlEncoded
    @POST(Const.CHANGE_PASSWORD)
    Call<BaseResponse> changePassword(@Field("id") String id,
                                      @Field("current_password") String currentPassword,
                                      @Field("new_password") String newPassword);


    @POST(Const.USER_DETAILS + "{user_id}")
    Call<UserResponse> getUserDetails(@Path("user_id") String userId);


    @FormUrlEncoded
    @POST(Const.UPDATE_PROFILE + "{user_id}")
    Call<UserResponse> updateUserProfile(
            @Path("user_id") String userId,
            @Field("lang") String lang,
            @Field("email") String email,
            @Field("user_type") String userType,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("age") String age,
            @Field("country_id") String countryId,
            @Field("state_id") String stateId,
            @Field("image") String image,
            @Field("password") String password,
            @Field("district") String district,
            @Field("gender") String gender,
            @Field("nationality_id") String nationalityId,
            @Field("job") String job,
            @Field("experience") String experience,
            @Field("have_licence") String have_licence,
            @Field("hourly_salary") String hourly_salary,
            @Field("monthly_salary") String monthly_salary,
            @Field("dayly_salary") String dayly_salary,
            @Field("desire_work") String desireWork,
            @Field("resume") String resume,
            @Field("resume_type") String resumeType,
            @Field("profession_licence") String professionLicence,
            @Field("permanence_type") String permanenceType,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("category") String category,
            @Field("sub_category") String subCategory,
            @Field("commercial_activities") String commercialActivities,
            @Field("commercial_registration_no") String commercialRegistrationNo,
            @Field("static_phone") String staticPhone,
            @Field("website") String website);


}
