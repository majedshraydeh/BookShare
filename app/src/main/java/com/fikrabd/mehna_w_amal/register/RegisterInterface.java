package com.fikrabd.mehna_w_amal.register;



import com.fikrabd.mehna_w_amal.models.DefaultData;
import com.fikrabd.mehna_w_amal.models.User;
import java.util.List;


public interface RegisterInterface {

    interface RegisterView {
        void onRegisterSuccess(User user);

        void onRegisterFailure(String message);

        void onRegisterError(String message);

        void allCountries(List<DefaultData> countries);

        void noCountries();

        void allState(List<DefaultData> states);

        void noState();
    }



    interface RegisterPresenter {
        void createUser(User user);

        void getAllCountries();

        void getAllState(String countryId);
    }
}
