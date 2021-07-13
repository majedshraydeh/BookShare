package com.fikrabd.mehna_w_amal.register.fragments.company;





import androidx.databinding.ObservableBoolean;

import com.fikrabd.mehna_w_amal.base.BasePresenter;
import com.fikrabd.mehna_w_amal.network.response.CountriesResponse;
import com.fikrabd.mehna_w_amal.network.response.StateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CompanyPresenter extends BasePresenter implements CompanyInterface.CompanyPresenter {

    private CompanyInterface.CompanyView companyView;
    public ObservableBoolean loading;



    public CompanyPresenter() {

    }

    public CompanyPresenter(CompanyInterface.CompanyView companyView) {
        this.companyView = companyView;
        loading = new ObservableBoolean();
        loading.set(true);
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
                            companyView.allCountries(response.body().getCountries());
                            return;
                        }
                    }
                }
                companyView.noCountries();
            }

            @Override
            public void onFailure(Call<CountriesResponse> call, Throwable t) {
                companyView.noCountries();
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
                            companyView.allState(response.body().getState());
                            return;
                        }
                    }
                }
                companyView.noState();
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {
                companyView.noState();
                loading.set(false);
            }
        });
    }
}
