package com.fikrabd.mehna_w_amal.register.fragments.company;


import com.fikrabd.mehna_w_amal.models.DefaultData;
import com.fikrabd.mehna_w_amal.models.User;

import java.util.List;


public interface CompanyInterface {

    interface CompanyView {


        void allCountries(List<DefaultData> countries);

        void noCountries();

        void allState(List<DefaultData> states);

        void noState();
    }



    interface CompanyPresenter {


        void getAllCountries();

        void getAllState(String countryId);
    }
}
