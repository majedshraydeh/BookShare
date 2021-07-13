package com.fikrabd.mehna_w_amal.register.fragments.individuals;


import com.fikrabd.mehna_w_amal.models.DefaultData;

import java.util.List;


public interface IndividualsInterface {

    interface IndividualsView {


        void allCountries(List<DefaultData> countries);

        void noCountries();

        void allNationality(List<DefaultData> nationality);

        void noNationality();

        void allState(List<DefaultData> states);

        void noState();
    }



    interface IndividualsPresenter {


        void getAllCountries();

        void getAllNationality();

        void getAllState(String countryId);
    }
}
