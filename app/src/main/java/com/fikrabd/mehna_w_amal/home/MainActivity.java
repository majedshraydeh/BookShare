package com.fikrabd.mehna_w_amal.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.fikrabd.mehna_w_amal.PaymentActivity;
import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.base.BaseActivity;
import com.fikrabd.mehna_w_amal.change_password.ChangePasswordActivity;
import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.databinding.ActivityMainBinding;
import com.fikrabd.mehna_w_amal.databinding.HeaderMainBinding;
import com.fikrabd.mehna_w_amal.home.fragment.CategoryFragment;
import com.fikrabd.mehna_w_amal.home.fragment.CompanyProfileFragment;
import com.fikrabd.mehna_w_amal.home.fragment.IndividualProfileFragment;
import com.fikrabd.mehna_w_amal.home.fragment.InformationFragment;
import com.fikrabd.mehna_w_amal.home.fragment.UserProfileFragment;
import com.fikrabd.mehna_w_amal.login.LoginActivity;
import com.fikrabd.mehna_w_amal.models.User;
import com.fikrabd.mehna_w_amal.network.fragment.NoInternetConnectionFragment;
import com.fikrabd.mehna_w_amal.splash.SplashActivity;
import com.fikrabd.mehna_w_amal.utilities.Transactions;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MainActivity extends BaseActivity<MainPresenter, ActivityMainBinding> implements MainInterface.MainView, NavigationView.OnNavigationItemSelectedListener {


    private ActionBarDrawerToggle toggle;
    private HeaderMainBinding headerMainBinding;


    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initToolbar();
        bindHeader();


//        if(!SharedPreferencesManager.getInstance().isCreate())
//        {
//            if(SharedPreferencesManager.getInstance().getUser().getStatus().equalsIgnoreCase("1"))
//            {
//                Transactions.replaceFragment(CategoryFragment.newInstance(), R.id.container, true, getSupportFragmentManager());
//
//            }else{
//
//                openActivityWithFinish(PaymentActivity.class);
//            }
//        }else {
//            Transactions.replaceFragment(CategoryFragment.newInstance(), R.id.container, true, getSupportFragmentManager());
//        }

        Transactions.replaceFragment(CategoryFragment.newInstance(), R.id.container, true, getSupportFragmentManager());



    }

    private void init() {

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            try {
                Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.container);

                String fragmentName = fragmentById != null ? fragmentById.getClass().getSimpleName() : "";

                handleNavigiations(fragmentById);
                handleNavigationBack(fragmentById);

                Log.e("Back Stack ", " : " + fragmentName);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void initToolbar() {
        setSupportActionBar(viewDataBinding.homeToolbar);
        viewDataBinding.homeToolbar.setTitleTextAppearance(this, R.style.RobotoTextViewStyle);
        viewDataBinding.homeToolbar.setNavigationOnClickListener(view -> onBackPressed());
        toggle = new ActionBarDrawerToggle(this, viewDataBinding.drawerLayout, viewDataBinding.homeToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (headerMainBinding != null)
                    headerMainBinding.setUser(SharedPreferencesManager.getInstance().getUser());
            }
        };
        viewDataBinding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        viewDataBinding.navView.setNavigationItemSelectedListener(this);

    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getString(R.string.home));
       handleNavigiations(getCurrentFragment());



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setTitle(getString(R.string.home));
        handleNavigiations(getCurrentFragment());
        if (headerMainBinding != null)
            headerMainBinding.setUser(SharedPreferencesManager.getInstance().getUser());
    }


    private void bindHeader() {
        headerMainBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.header_main,
                viewDataBinding.navView,
                false);
        viewDataBinding.navView.addHeaderView(headerMainBinding.getRoot());
    }

    private void handleNavigiations(Fragment currentFragment) {
        if (currentFragment instanceof CategoryFragment) {
            viewDataBinding.navView.setCheckedItem(R.id.home);
        } else if (currentFragment instanceof UserProfileFragment) {
            viewDataBinding.navView.setCheckedItem(R.id.profile);
        }else if (currentFragment instanceof CompanyProfileFragment) {
            viewDataBinding.navView.setCheckedItem(R.id.profile);
        }else if (currentFragment instanceof IndividualProfileFragment) {
            viewDataBinding.navView.setCheckedItem(R.id.profile);
        }


    }

    private void handleNavigationBack(Fragment fragmentById) {

        if (fragmentById instanceof CategoryFragment
                || fragmentById instanceof UserProfileFragment) {

            if (getSupportActionBar() != null)
                getSupportActionBar().show();

            if (SharedPreferencesManager.getInstance().isCreate()) {
                viewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                toggle.setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toggle.syncState();
                toggle.setToolbarNavigationClickListener(view -> onBackPressed());
            } else if(!SharedPreferencesManager.getInstance().isCreate()) {
                viewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                toggle.setDrawerIndicatorEnabled(true);
              getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toggle.syncState();


            }


        } else if (fragmentById instanceof NoInternetConnectionFragment) {

            if (getSupportActionBar() != null) getSupportActionBar().hide();


        }
//        else {
//            if (getSupportActionBar() != null) getSupportActionBar().show();
//
//            viewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//            toggle.setDrawerIndicatorEnabled(false);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            toggle.syncState();
//
//            toggle.setToolbarNavigationClickListener(view -> onBackPressed());
//        }
    }

    private void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            this.viewDataBinding.drawerLayout.closeDrawer(GravityCompat.START);
            clearBackStack();
            Transactions.replaceFragment(CategoryFragment.newInstance(), R.id.container, true, getSupportFragmentManager());
        } else if (id == R.id.profile) {
            this.viewDataBinding.drawerLayout.closeDrawer(GravityCompat.START);
            Log.e("Back Stack ", " : " + SharedPreferencesManager.getInstance().getUser().getUserType());
            if (SharedPreferencesManager.getInstance().getUser().getUserType().equalsIgnoreCase("1"))
            {
                Log.e("Back Stack ", " : " + "user");
                Transactions.replaceFragment(UserProfileFragment.newInstance(), R.id.container, true, getSupportFragmentManager());

            }else if(SharedPreferencesManager.getInstance().getUser().getUserType().equalsIgnoreCase("2"))
            {
                Transactions.replaceFragment(IndividualProfileFragment.newInstance(), R.id.container, true, getSupportFragmentManager());

            }else if(SharedPreferencesManager.getInstance().getUser().getUserType().equalsIgnoreCase("3"))
            {
                Transactions.replaceFragment(CompanyProfileFragment.newInstance(), R.id.container, true, getSupportFragmentManager());

            }
        } else if (id == R.id.change_language) {
            changeLanguage();
        } else if (id == R.id.change_password) {
            openActivity(ChangePasswordActivity.class);
        } else if (id == R.id.logout) {
            signOut();
        }
        return true;
    }

    public void changeLanguage() {
        Context context = new ContextThemeWrapper(this, R.style.StyleMenuItem);

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(getString(R.string.change_language))
                .setItems(getResources().getStringArray(R.array.languages), (dialogInterface, i) -> {

                    switch (i) {
                        case 0:
                            refresh(Const.ENGLISH);
                            break;
                        case 1:
                            refresh(Const.ARABIC);
                            break;
                    }
                });
        builder.create().show();
    }

    private void refresh(String language) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Const.LANGUAGE_KEY, language);
        startActivity(intent);
        finish();
    }

    private void signOut() {
        SharedPreferencesManager.getInstance().resetFields();
        openActivityWithClearTask(LoginActivity.class);
    }
}
