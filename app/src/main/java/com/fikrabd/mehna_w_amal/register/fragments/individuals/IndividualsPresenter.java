package com.fikrabd.mehna_w_amal.register.fragments.individuals;





import androidx.databinding.ObservableBoolean;

import com.fikrabd.mehna_w_amal.base.BasePresenter;
import com.fikrabd.mehna_w_amal.network.response.CountriesResponse;
import com.fikrabd.mehna_w_amal.network.response.StateResponse;
import com.fikrabd.mehna_w_amal.register.fragments.company.CompanyInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IndividualsPresenter extends BasePresenter implements IndividualsInterface.IndividualsPresenter {

    private IndividualsInterface.IndividualsView individualsView;
    public ObservableBoolean loading;



    public IndividualsPresenter() {

    }


    public IndividualsPresenter(IndividualsInterface.IndividualsView individualsView) {
        this.individualsView = individualsView;
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
                            individualsView.allCountries(response.body().getCountries());
                            return;
                        }
                    }
                }
                individualsView.noCountries();
            }

            @Override
            public void onFailure(Call<CountriesResponse> call, Throwable t) {
                individualsView.noCountries();
                loading.set(false);
            }
        });

    }

    @Override
    public void getAllNationality() {
        Call<CountriesResponse> countriesResponseCall = apiService.getCountries();

        countriesResponseCall.enqueue(new Callback<CountriesResponse>() {
            @Override
            public void onResponse(Call<CountriesResponse> call, Response<CountriesResponse> response) {

                loading.set(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getCountries().size() != 0) {
                            individualsView.allNationality(response.body().getCountries());
                            return;
                        }
                    }
                }
                individualsView.noNationality();
            }

            @Override
            public void onFailure(Call<CountriesResponse> call, Throwable t) {
                individualsView.noNationality();
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
                            individualsView.allState(response.body().getState());
                            return;
                        }
                    }
                }
                individualsView.noState();
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {
                individualsView.noState();
                loading.set(false);
            }
        });
    }
}
