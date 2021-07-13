package com.fikrabd.mehna_w_amal.register.fragments;


import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.adapter.CountryAdapter;
import com.fikrabd.mehna_w_amal.adapter.StateAdapter;
import com.fikrabd.mehna_w_amal.base.BaseFragment;
import com.fikrabd.mehna_w_amal.callbacks.InternetListener;
import com.fikrabd.mehna_w_amal.callbacks.ItemClickListener;
import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.databinding.FragmentUserBinding;
import com.fikrabd.mehna_w_amal.home.MainActivity;
import com.fikrabd.mehna_w_amal.models.DefaultData;
import com.fikrabd.mehna_w_amal.models.User;
import com.fikrabd.mehna_w_amal.network.fragment.NoInternetConnectionFragment;
import com.fikrabd.mehna_w_amal.register.RegisterInterface;
import com.fikrabd.mehna_w_amal.register.RegisterPresenter;
import com.fikrabd.mehna_w_amal.utilities.ItemAnimation;
import com.fikrabd.mehna_w_amal.utilities.Tools;
import com.fikrabd.mehna_w_amal.utilities.Transactions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class UserFragment extends BaseFragment<RegisterPresenter, FragmentUserBinding> implements RegisterInterface.RegisterView, View.OnClickListener, InternetListener, ItemClickListener {


    private StateAdapter stateAdapter;
    private CountryAdapter countryAdapter;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;

    String countryId="";
    String stateId="";

    public static UserFragment newInstance() {
        return new UserFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_user;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInternetListener(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }


    @Override
    public void internetConnected() {

    }

    @Override
    public void internetNotConnected() {
        if (getFragmentManager() != null)
            Transactions.replaceFragmentWithAnimation(NoInternetConnectionFragment.newInstance(), R.id.container_register, true, getFragmentManager());
    }


    private void init() {

        mBehavior = BottomSheetBehavior.from(viewDataBinding.bottomSheet);
        viewDataBinding.nameLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.nameLayout));
        viewDataBinding.mobileLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.mobileLayout));
        viewDataBinding.ageLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.ageLayout));
        viewDataBinding.emailLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.emailLayout));
        viewDataBinding.passwordLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.passwordLayout));
        viewDataBinding.signUp.setOnClickListener(this);
        viewDataBinding.addImage.setOnClickListener(this);
        viewDataBinding.country.setOnClickListener(this);
        viewDataBinding.state.setOnClickListener(this);
    }


    private void signUp() {

        if (validate()) {
            Tools.hideKeyboard(getActivity());
            showProgressDialog("", getString(R.string.authenticating));
            presenter.createUser(generateUser());

        } else {
            Tools.showMessage(getString(R.string.valid_info));
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.sign_up:
                signUp();
                break;

            case R.id.add_image:
                selectImage();
                break;

            case R.id.country:
                selectCountry();
                break;

            case R.id.state:
                selectState();
                break;


        }
    }


    private User generateUser() {

        User user = new User();
        if (viewDataBinding.imageProfile.getTag().equals(getString(R.string.not_selected))) {
            user.setImage("");
        } else {
            user.setImage(Tools.convertImageToBase64(viewDataBinding.imageProfile));
        }
        user.setName(viewDataBinding.name.getText().toString());
        user.setEmail(viewDataBinding.email.getText().toString());
        user.setPhone(viewDataBinding.mobile.getText().toString());
        user.setPassword(viewDataBinding.password.getText().toString());
        user.setAge(viewDataBinding.age.getText().toString());
        user.setCountryId(countryId);
        user.setStateId(stateId);
        user.setDistrict(viewDataBinding.city.getText().toString());
        user.setUserType("1");


        return user;
    }


    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), Const.PICKED_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.PICKED_IMAGE) {
            try {
                if (resultCode == RESULT_OK && data != null) {

                    viewDataBinding.imageProfile.setTag(getString(R.string.selected));

                    Uri selectedImage = data.getData();

                    viewDataBinding.imageProfile.setImageURI(selectedImage);

                } else {

                    viewDataBinding.imageProfile.setTag(getString(R.string.not_selected));
                }
            } catch (Exception exception) {

                viewDataBinding.imageProfile.setTag(getString(R.string.not_selected));
            }
        }
    }


    private void selectCountry() {

        presenter.getAllCountries();

        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.layout_bottom, null);
        TextView textView = view.findViewById(R.id.title);
        textView.setText(R.string.select_country);
        RecyclerView recycler = view.findViewById(R.id.recyclerview_bottom);
        mBottomSheetDialog = new BottomSheetDialog(getContext());
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        mBottomSheetDialog.show();

        mBottomSheetDialog.setOnDismissListener(dialog -> mBottomSheetDialog = null);


        countryAdapter = new CountryAdapter();
//        countryAdapter.setAnimation_type(ItemAnimation.FADE_IN);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(countryAdapter);
        countryAdapter.setItemClickListener(this);
    }

    private void selectState() {

        presenter.getAllState(countryId);

        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.layout_bottom, null);

        TextView textView = view.findViewById(R.id.title);
        textView.setText(R.string.select_governorate);

        RecyclerView recycler = view.findViewById(R.id.recyclerview_bottom);

        mBottomSheetDialog = new BottomSheetDialog(getContext());
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        mBottomSheetDialog.show();

        mBottomSheetDialog.setOnDismissListener(dialog -> mBottomSheetDialog = null);


        stateAdapter = new StateAdapter();
//        stateAdapter.setAnimation_type(ItemAnimation.FADE_IN);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(stateAdapter);
        stateAdapter.setItemClickListener(this);
    }




    public boolean validate() {

        String inputEmail = viewDataBinding.emailLayout.getEditText().getText().toString();
        String inputName = viewDataBinding.nameLayout.getEditText().getText().toString();
        String inputMobile = viewDataBinding.mobileLayout.getEditText().getText().toString();
        String inputPassword = viewDataBinding.passwordLayout.getEditText().getText().toString();

        if (inputName.isEmpty() || inputName.length() < 5) {
            viewDataBinding.nameLayout.setErrorEnabled(true);
            viewDataBinding.nameLayout.setError(getString(R.string.valid_name));
            return false;
        } else {
            viewDataBinding.nameLayout.setErrorEnabled(false);
        }

        if (inputMobile.isEmpty()) {
            viewDataBinding.mobileLayout.setErrorEnabled(true);
            viewDataBinding.mobileLayout.setError(getString(R.string.valid_phone));
            return false;
        } else {
            viewDataBinding.mobileLayout.setErrorEnabled(false);
        }

        if (inputEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {
            viewDataBinding.emailLayout.setErrorEnabled(true);
            viewDataBinding.emailLayout.setError(getString(R.string.enter_valid_emails));
            return false;
        } else {
            viewDataBinding.emailLayout.setErrorEnabled(false);
        }

        if (inputPassword.isEmpty() || inputPassword.length() < 3 || inputPassword.length() > 20) {
            viewDataBinding.passwordLayout.setErrorEnabled(true);
            viewDataBinding.passwordLayout.setError(getString(R.string.valid_pass));
        } else {
            viewDataBinding.passwordLayout.setErrorEnabled(false);

        }

        return true;
    }

    @Override
    public void onRegisterSuccess(User user) {
        hideProgressDialog();
        SharedPreferencesManager.getInstance().setLoggedIn(true);
        SharedPreferencesManager.getInstance().setUser(user);
        lunchHome();

    }

    @Override
    public void onRegisterFailure(String message) {
        hideProgressDialog();
        Tools.showMessage(message);
    }

    @Override
    public void onRegisterError(String message) {

    }

    @Override
    public void allCountries(List<DefaultData> countries) {

        countryAdapter.setCountries(countries);
    }

    @Override
    public void noCountries() {

    }

    @Override
    public void allState(List<DefaultData> states) {

        stateAdapter.setState(states);
    }

    @Override
    public void noState() {

    }


    private void lunchHome() {

        openActivityWithFinish(MainActivity.class);
    }




    @Override
    public void onItemClick(RecyclerView.Adapter adapter, View view, int position) {

        if (adapter instanceof CountryAdapter) {

            DefaultData country = countryAdapter.getCountries(position);
            viewDataBinding.country.setText(country.getTitle());
            countryId = country.getId();
            mBottomSheetDialog.hide();
        }else  if (adapter instanceof StateAdapter) {
             DefaultData state = stateAdapter.getState(position);
            viewDataBinding.state.setText(state.getTitle());
            stateId = state.getId();
            mBottomSheetDialog.hide();
        }

    }


    public class RealTimeTextWatcher implements TextWatcher {

        private View view;


        public RealTimeTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void afterTextChanged(Editable editable) {

            switch (view.getId()) {

                case R.id.name_layout:
                case R.id.mobile_layout:
                case R.id.email_layout:
                case R.id.password_layout:
                    validate();
                    break;

            }
        }
    }


}
