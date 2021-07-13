package com.fikrabd.mehna_w_amal.steppers;

import androidx.databinding.ObservableBoolean;


import com.fikrabd.mehna_w_amal.base.BasePresenter;
import com.fikrabd.mehna_w_amal.models.Stepper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StepperPresenter extends BasePresenter implements StepperInterface.StepperPresenter {


    private StepperInterface.StepperView stepperView;
    public ObservableBoolean loading;


    public StepperPresenter(StepperInterface.StepperView stepperView) {
        this.stepperView = stepperView;
        loading = new ObservableBoolean();
    }

    @Override
    public void getAllSteppers() {

        loading.set(true);

        Call<List<Stepper>> callSteppers = apiService.getAllStepper();

        callSteppers.enqueue(new Callback<List<Stepper>>() {
            @Override
            public void onResponse(@NotNull Call<List<Stepper>> call, @NotNull Response<List<Stepper>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        stepperView.allSteppers(response.body());
                    } else {
                        stepperView.noSteppers();
                    }
                } else {
                    stepperView.noSteppers();
                }
                loading.set(false);
            }

            @Override
            public void onFailure(@NotNull Call<List<Stepper>> call, @NotNull Throwable t) {
                loading.set(false);
                stepperView.noSteppers();
            }
        });
    }
}
