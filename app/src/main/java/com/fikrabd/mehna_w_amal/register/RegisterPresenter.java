package com.fikrabd.mehna_w_amal.register;





import androidx.databinding.ObservableBoolean;

import com.fikrabd.mehna_w_amal.base.BasePresenter;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.models.User;
import com.fikrabd.mehna_w_amal.network.response.CountriesResponse;
import com.fikrabd.mehna_w_amal.network.response.StateResponse;
import com.fikrabd.mehna_w_amal.network.response.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterPresenter extends BasePresenter implements RegisterInterface.RegisterPresenter {

    private RegisterInterface.RegisterView registerView;
    public ObservableBoolean loading;



    public RegisterPresenter() {

    }


    public RegisterPresenter(RegisterInterface.RegisterView registerView) {
        this.registerView = registerView;
        loading = new ObservableBoolean();
        loading.set(true);
    }





    @Override
    public void createUser(User user) {

        Call<UserResponse> createAccount =
                apiService.createUser(SharedPreferencesManager.getInstance().getLanguage(),user.getEmail(),user.getUserType(),
                        user.getName(), user.getPhone(), user.getAge(), user.getCountryId(),user.getStateId(),user.getImage(),
                        user.getPassword(),user.getDistrict(),user.getGenderId(),user.getNationalityId(),user.getJob(),user.getExperience(),
                        user.getHaveLicenceId(), user.getHourlySalary(), user.getSalaryPerMonth(), user.getSalaryPerDay(), user.getDesireWork(),
                        user.getResume(),user.getResumeType(), user.getProfessionLicence(), user.getPermanenceTypeId(), user.getLatitude(), user.getLongitude(),
                        user.getCategoryId(),user.getSubCategoryId(),user.getCommercialActivities(),user.getCommercialActivitiesNo(),user.getStaticPhoneNumber(), user.getWebsiteUrl());

        createAccount.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        if (response.body().getResult().getStatus().equals("1")) {
                            registerView.onRegisterSuccess(response.body().getUser());
                        } else {
                            registerView.onRegisterFailure(response.body().getResult().getMessage());
                        }
                    } else {
                        registerView.onRegisterFailure(response.body().getResult().getMessage());
                    }
                } else {
                    registerView.onRegisterFailure(response.body().getResult().getMessage());
                }



            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                registerView.onRegisterError(t.getLocalizedMessage());

            }
        });

    }


    @Override
    public void getAllCountries() {

        Call<CountriesResponse> countriesResponseCall = apiService.getCountries();

        countriesResponseCall.enqueue(new Callback<CountriesResponse>() {
            @Override
            public void onResponse(Call<CountriesResponse> call, Response<CountriesResponse> response) {

                loading.set(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getCountries().size() != 0) {
                            registerView.allCountries(response.body().getCountries());
                            return;
                        }
                    }
                }
                registerView.noCountries();
            }

            @Override
            public void onFailure(Call<CountriesResponse> call, Throwable t) {
                registerView.noCountries();
                loading.set(false);
            }
        });

    }

    @Override
    public void getAllState(String countryId) {

        Call<StateResponse> stateResponseCall = apiService.getState(countryId);

        stateResponseCall.enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {

                loading.set(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getState().size() != 0) {
                            registerView.allState(response.body().getState());
                            return;
                        }
                    }
                }
                registerView.noState();
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {
                registerView.noState();
                loading.set(false);
            }
        });
    }
}
