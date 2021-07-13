package com.fikrabd.mehna_w_amal.register.fragments.company;




import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.fikrabd.mehna_w_amal.databinding.FragmentCompanyBinding;
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


public class CompanyFragment extends BaseFragment<CompanyPresenter, FragmentCompanyBinding> implements InternetListener, View.OnClickListener, CompanyInterface.CompanyView, ItemClickListener {


    private StateAdapter stateAdapter;
    private CountryAdapter countryAdapter;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;

    String countryId="";
    String stateId="";
    public static CompanyFragment newInstance() {
        return new CompanyFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_company;
    }

    @Override
    protected CompanyPresenter createPresenter() {
        return new CompanyPresenter(this);
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

    private void init() {


        viewDataBinding.setFragment(this);
        mBehavior = BottomSheetBehavior.from(viewDataBinding.bottomSheet);
        viewDataBinding.nameCompanyLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.nameCompanyLayout));
        viewDataBinding.commercialActivitiesLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.commercialActivitiesLayout));
        viewDataBinding.commercialLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.commercialLayout));
        viewDataBinding.emailLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.emailLayout));
        viewDataBinding.passwordLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.passwordLayout));
        viewDataBinding.country.setOnClickListener(this);
        viewDataBinding.state.setOnClickListener(this);
        viewDataBinding.addImageCompany.setOnClickListener(this);

    }







    @Override
    public void internetConnected() {

    }

    @Override
    public void internetNotConnected() {
        if (getFragmentManager() != null)
            Transactions.replaceFragmentWithAnimation(NoInternetConnectionFragment.newInstance(), R.id.container_register, true, getFragmentManager());
    }


    private User generateUser() {

        User user = new User();
        if (viewDataBinding.imageProfileCompany.getTag().equals(getString(R.string.not_selected))) {
            user.setImage("");
        } else {
            user.setImage(Tools.convertImageToBase64(viewDataBinding.imageProfileCompany));
        }
        user.setName(viewDataBinding.nameCompany.getText().toString());
        user.setEmail(viewDataBinding.email.getText().toString());
        user.setPhone(viewDataBinding.mobile.getText().toString());
        user.setStatic_phone(viewDataBinding.fixedPhone.getText().toString());
        user.setPassword(viewDataBinding.password.getText().toString());
        user.setCountryId(countryId);
        user.setStateId(stateId);
        user.setDistrict(viewDataBinding.city.getText().toString());
        user.setUserType("3");
        user.setWebsite(viewDataBinding.website.getText().toString());
        user.setCommercialActivities(viewDataBinding.commercialActivities.getText().toString());
        user.setCommercialActivitiesNo(viewDataBinding.commercial.getText().toString());

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

                    viewDataBinding.imageProfileCompany.setTag(getString(R.string.selected));

                    Uri selectedImage = data.getData();

                    viewDataBinding.imageProfileCompany.setImageURI(selectedImage);

                } else {

                    viewDataBinding.imageProfileCompany.setTag(getString(R.string.not_selected));
                }
            } catch (Exception exception) {

                viewDataBinding.imageProfileCompany.setTag(getString(R.string.not_selected));
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
        String inputName = viewDataBinding.nameCompanyLayout.getEditText().getText().toString();
        String inputCommercial = viewDataBinding.commercialLayout.getEditText().getText().toString();
        String inputPassword = viewDataBinding.passwordLayout.getEditText().getText().toString();
        String inputCommercialActivities = viewDataBinding.commercialActivitiesLayout.getEditText().getText().toString();

        if (inputName.isEmpty()|| inputName.length() < 5) {
            viewDataBinding.nameCompanyLayout.setErrorEnabled(true);
            viewDataBinding.nameCompanyLayout.setError(getString(R.string.valid_name));
            return false;
        } else {
            viewDataBinding.nameCompanyLayout.setErrorEnabled(false);
        }

        if (inputCommercial.isEmpty()) {
            viewDataBinding.commercialLayout.setErrorEnabled(true);
            viewDataBinding.commercialLayout.setError(getString(R.string.valid_commercial_no));
            return false;
        } else {
            viewDataBinding.commercialLayout.setErrorEnabled(false);
        }


        if (inputCommercialActivities.isEmpty()) {
            viewDataBinding.commercialActivitiesLayout.setErrorEnabled(true);
            viewDataBinding.commercialActivitiesLayout.setError(getString(R.string.not_empty));
            return false;
        } else {
            viewDataBinding.commercialActivitiesLayout.setErrorEnabled(false);
        }


        if (inputEmail.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {
            viewDataBinding.emailLayout.setErrorEnabled(true);
            viewDataBinding.emailLayout.setError(getString(R.string.enter_valid_emails));
            return false;
        } else {
            viewDataBinding.emailLayout.setErrorEnabled(false);
        }

        if (inputPassword.isEmpty() || inputPassword.length() < 3 || inputPassword.length() > 20)
        {
            viewDataBinding.passwordLayout.setErrorEnabled(true);
            viewDataBinding.passwordLayout.setError(getString(R.string.valid_pass));
        } else {
            viewDataBinding.passwordLayout.setErrorEnabled(false);


        }


        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {



            case R.id.add_image_company:
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

    public void next(View view) {

        if (getActivity()!=null)
        if(validate()){
            Tools.hideKeyboard(getActivity());
            SharedPreferencesManager.getInstance().setUser(generateUser());
            openActivity(MainActivity.class);
        }else{

            Tools.showMessage(getString(R.string.valid_info));
        }


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

                case R.id.name_company_layout:
                case R.id.commercial_layout:
                case R.id.commercial_activities_layout:
                case R.id.email_layout:
                case R.id.password_layout:
                    validate();
                    break;

            }
        }
    }
}
