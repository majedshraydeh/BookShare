package com.fikrabd.mehna_w_amal.home;

import androidx.databinding.ObservableBoolean;

import com.fikrabd.mehna_w_amal.base.BasePresenter;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.models.Category;
import com.fikrabd.mehna_w_amal.models.User;
import com.fikrabd.mehna_w_amal.network.response.BaseResponse;
import com.fikrabd.mehna_w_amal.network.response.CategoryResponse;
import com.fikrabd.mehna_w_amal.network.response.PeopleResponse;
import com.fikrabd.mehna_w_amal.network.response.RateResponse;
import com.fikrabd.mehna_w_amal.network.response.SubCategoryResponse;
import com.fikrabd.mehna_w_amal.network.response.UserResponse;
import com.fikrabd.mehna_w_amal.register.RegisterInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainPresenter extends BasePresenter implements MainInterface.MainPresenterView {


    public ObservableBoolean loading;
    private MainInterface.CategoryView categoryView;
    private MainInterface.SubCategoryView subCategoryView;
    private MainInterface.PeopleView peopleView;
    private MainInterface.UserDetailsView userDetails;
    private MainInterface.UpdateView updateView;



    public MainPresenter() {

    }

    public MainPresenter(MainInterface.CategoryView categoryView) {
        this.categoryView = categoryView;
        loading = new ObservableBoolean();
        loading.set(true);
    }

    public MainPresenter(MainInterface.SubCategoryView subCategoryView) {
        this.subCategoryView = subCategoryView;
        loading = new ObservableBoolean();
        loading.set(true);

    }

    public MainPresenter(MainInterface.PeopleView peopleView) {
        this.peopleView = peopleView;
        loading = new ObservableBoolean();
        loading.set(true);
    }

    public MainPresenter(MainInterface.UserDetailsView userDetails) {
        this.userDetails = userDetails;
        loading = new ObservableBoolean();
        loading.set(true);
    }



    public MainPresenter(MainInterface.UpdateView updateView) {
        this.updateView = updateView;

    }





    @Override
    public void getCategory() {

        Call<CategoryResponse> listCall = apiService.getHomeCategories();

        listCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                loading.set(false);

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                            categoryView.allCategories(response.body().getCategories());
                            return;

                    }
                }
                categoryView.noCategories();
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                categoryView.noCategories();
                loading.set(false);
            }
        });
    }

    @Override
    public void getSubCategory(String categoryId) {



        Call<SubCategoryResponse> subCategory = apiService.getSubCategories(categoryId);

        subCategory.enqueue(new Callback<SubCategoryResponse>() {
            @Override
            public void onResponse(Call<SubCategoryResponse> call, Response<SubCategoryResponse> response) {

                loading.set(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        subCategoryView.allSubCategory(response.body().getSubCategories());
                        return;

                    }
                }
                subCategoryView.noSubCategory();
            }

            @Override
            public void onFailure(Call<SubCategoryResponse> call, Throwable t) {
                subCategoryView.noSubCategory();
                loading.set(false);

            }
        });

    }

    @Override
    public void getPeople(String subCategoryId) {


        Call<PeopleResponse> listCall = apiService.getPeople(subCategoryId);

        listCall.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {

                loading.set(false);

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        if (response.body().getUsers().size()!=0)
                        {
                            peopleView.allPeople(response.body().getUsers());
                            return;
                        }



                    }
                }
                peopleView.noPeople();
            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {
                peopleView.noPeople();
                loading.set(false);
            }
        });

    }

    @Override
    public void createUser(User user) {
        Call<UserResponse> createAccount =
                apiService.createUser(SharedPreferencesManager.getInstance().getLanguage(), user.getEmail(),user.getUserType(),
                        user.getName(), user.getPhone(), user.getAge(), user.getCountryId(),user.getStateId(),user.getImage(),
                        user.getPassword(),user.getDistrict(),user.getGenderId(),user.getNationalityId(),user.getJob(),user.getExperience(),
                        user.getHaveLicenceId(), user.getHourlySalary(), user.getSalaryPerMonth(), user.getSalaryPerDay(), user.getDesireWork(),
                        user.getResume(),user.getResumeType(), user.getProfessionLicence(), user.getPermanenceTypeId(), user.getLatitude(), user.getLongitude(),
                        user.getCategoryId(),user.getSubCategoryId(),user.getCommercialActivities(),user.getCommercialActivitiesNo(),user.getStatic_phone(), user.getWebsite());

        createAccount.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        if (response.body().getResult().getStatus().equals("1")) {
                            subCategoryView.onRegisterSuccess(response.body().getUser());
                        } else {
                            subCategoryView.onRegisterFailure(response.body().getResult().getMessage());
                        }
                    } else {
                        subCategoryView.onRegisterFailure(response.body().getResult().getMessage());
                    }
                } else {
                    subCategoryView.onRegisterFailure(response.body().getResult().getMessage());
                }



            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                subCategoryView.onRegisterError(t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void userDetails(String userId) {


        Call<UserResponse> listCall = apiService.getUserDetails(userId);

        listCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                loading.set(false);

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        if (response.body().getResult().getStatus().equalsIgnoreCase("1"))
                        {
                            userDetails.allUserDetails(response.body().getUser());
                            return;
                        }



                    }
                }
                userDetails.noUserDetails();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                userDetails.noUserDetails();
                loading.set(false);
            }
        });

    }


    @Override
    public void rating(String userToRate, String rateValue) {

        Call<RateResponse> baseResponseCall = apiService.setRate(SharedPreferencesManager.getInstance().getUser().getId(),
                userToRate, rateValue);

        baseResponseCall.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        userDetails.rated(response.body().getResult().getMessage(), response.body().getRating());
                    }
                }
            }

            @Override
            public void onFailure(Call<RateResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public void changeStatus(String userId, String status) {


        Call<UserResponse> changeStatus = apiService.changeStatus(userId,status);

        changeStatus.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        updateView.onStatusChanged(response.body().getResult().getMessage());
                        return;

                    }
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {


            }
        });

    }

    @Override
    public void updateUser(User user) {

        Call<UserResponse> createAccount =
                apiService.updateUserProfile(user.getId(),SharedPreferencesManager.getInstance().getLanguage(), user.getEmail(),user.getUserType(),
                        user.getName(), user.getPhone(), user.getAge(), user.getCountryId(),user.getStateId(),user.getImage(),
                        user.getPassword(),user.getDistrict(),user.getGenderId(),user.getNationalityId(),user.getJob(),user.getExperience(),
                        user.getHaveLicenceId(), user.getHourlySalary(), user.getSalaryPerMonth(), user.getSalaryPerDay(), user.getDesireWork(),
                        user.getResume(),user.getResumeType(), user.getProfessionLicence(), user.getPermanenceTypeId(), user.getLatitude(), user.getLongitude(),
                        user.getCategoryId(),user.getSubCategoryId(),user.getCommercialActivities(),user.getCommercialActivitiesNo(),user.getStatic_phone(), user.getWebsite());

        createAccount.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        if (response.body().getResult().getStatus().equals("1")) {
                            updateView.onUpdateSuccess(response.body().getUser(),response.body().getResult().getMessage());
                        } else {
                            updateView.onUpdateFailure(response.body().getResult().getMessage());
                        }
                    } else {
                        updateView.onUpdateFailure(response.body().getResult().getMessage());
                    }
                } else {
                    updateView.onUpdateFailure(response.body().getResult().getMessage());
                }



            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                updateView.onUpdateError(t.getLocalizedMessage());

            }
        });
    }
}
