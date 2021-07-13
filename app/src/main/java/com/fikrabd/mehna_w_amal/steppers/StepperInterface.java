package com.fikrabd.mehna_w_amal.steppers;



import com.fikrabd.mehna_w_amal.models.Stepper;

import java.util.List;

/**
 * Created by Saif M Jaradat on 2/7/2021.
 */
public interface StepperInterface {
    interface StepperView {
        void allSteppers(List<Stepper> steppers);

        void noSteppers();
    }

    interface StepperPresenter {
        void getAllSteppers();
    }
}
