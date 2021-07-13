package com.fikrabd.mehna_w_amal.register;


import android.os.Bundle;

import com.facebook.CallbackManager;
import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.adapter.CountryAdapter;
import com.fikrabd.mehna_w_amal.adapter.StateAdapter;
import com.fikrabd.mehna_w_amal.base.BaseActivity;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.databinding.ActivityRegisterBinding;
import com.fikrabd.mehna_w_amal.register.fragments.company.CompanyFragment;
import com.fikrabd.mehna_w_amal.register.fragments.individuals.IndividualFragment;
import com.fikrabd.mehna_w_amal.register.fragments.UserFragment;
import com.fikrabd.mehna_w_amal.utilities.Transactions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class RegisterActivity extends BaseActivity<RegisterPresenter, ActivityRegisterBinding>  {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        switch (SharedPreferencesManager.getInstance().getUserType()) {
            case "1":
                if (getFragmentManager() != null)
                    Transactions.replaceFragmentWithAnimation(UserFragment.newInstance(), R.id.container_register, true, getSupportFragmentManager());
                break;
            case "2":
                if (getFragmentManager() != null)
                    Transactions.replaceFragmentWithAnimation(IndividualFragment.newInstance(), R.id.container_register, true, getSupportFragmentManager());
                break;
            case "3":
                if (getFragmentManager() != null)
                    Transactions.replaceFragmentWithAnimation(CompanyFragment.newInstance(), R.id.container_register, true, getSupportFragmentManager());
                break;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }




}