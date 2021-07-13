package com.fikrabd.mehna_w_amal.register.fragments.individuals;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.util.FileUtil;

import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.adapter.CountryAdapter;
import com.fikrabd.mehna_w_amal.adapter.NationalityAdapter;
import com.fikrabd.mehna_w_amal.adapter.StateAdapter;
import com.fikrabd.mehna_w_amal.base.BaseFragment;
import com.fikrabd.mehna_w_amal.callbacks.InternetListener;
import com.fikrabd.mehna_w_amal.callbacks.ItemClickListener;
import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.databinding.FragmentIndividualBinding;
import com.fikrabd.mehna_w_amal.home.MainActivity;
import com.fikrabd.mehna_w_amal.models.DefaultData;
import com.fikrabd.mehna_w_amal.models.User;
import com.fikrabd.mehna_w_amal.network.fragment.NoInternetConnectionFragment;
import com.fikrabd.mehna_w_amal.register.RegisterInterface;
import com.fikrabd.mehna_w_amal.register.RegisterPresenter;
import com.fikrabd.mehna_w_amal.utilities.ItemAnimation;
import com.fikrabd.mehna_w_amal.utilities.Tools;
import com.fikrabd.mehna_w_amal.utilities.Transactions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class IndividualFragment extends BaseFragment<IndividualsPresenter, FragmentIndividualBinding> implements InternetListener, PermissionListener, OnMapReadyCallback, View.OnClickListener, ItemClickListener, IndividualsInterface.IndividualsView {



    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CHECK_SETTINGS = 43;

    private double latitude = 0;
    private double longitude = 0;
    private boolean notNext=false;
    private Context context;

    String countryId="";
    String stateId="";
    String nationalityId="";
    String genderId="";
    String permanenceId="";
    String desiredWorkId="";
    private Uri cv;
    private Uri license;

    private NationalityAdapter nationalityAdapter;
    private StateAdapter stateAdapter;
    private CountryAdapter countryAdapter;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;


    public static IndividualFragment newInstance() {
        return new IndividualFragment();
    }



    @Override
    public void allCountries(List<DefaultData> countries) {
        countryAdapter.setCountries(countries);

    }

    @Override
    public void noCountries() {

    }

    @Override
    public void allNationality(List<DefaultData> nationality) {
        nationalityAdapter.setNationality(nationality);
    }

    @Override
    public void noNationality() {

    }

    @Override
    public void allState(List<DefaultData> states) {
        stateAdapter.setState(states);
    }

    @Override
    public void noState() {

    }


    private enum State {
        FIRST,
        SECOND,
        THIRD
    }

    State[] array_state = new State[]{State.FIRST, State.SECOND, State.THIRD};
    private int idx_state = 0;


    public static User user=new User();
    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_individual;
    }

    @Override
    protected IndividualsPresenter createPresenter() {
        return new IndividualsPresenter(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
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
        displayLayout(State.FIRST);
    }

//    @SuppressLint("VisibleForTests")
//    private void initMap() {
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        if (mapFragment != null) {
//            mapFragment.getMapAsync(this);
//            fusedLocationProviderClient = new FusedLocationProviderClient(getActivity());
//        }
//    }






    private void init() {

        viewDataBinding.setFragment(this);
        viewDataBinding.imageFirst.setColorFilter(getResources().getColor(R.color.Red), PorterDuff.Mode.SRC_ATOP);
        viewDataBinding.imageSecond.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);
        viewDataBinding.imageThird.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);

        mBehavior = BottomSheetBehavior.from(viewDataBinding.bottomSheet);


        viewDataBinding.first.nameLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.first.nameLayout));
        viewDataBinding.third.mobileLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.third.mobileLayout));
        viewDataBinding.third.emailLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.third.emailLayout));
        viewDataBinding.third.passwordLayout.getEditText().addTextChangedListener(new RealTimeTextWatcher(viewDataBinding.third.passwordLayout));

        viewDataBinding.second.country.setOnClickListener(this);
        viewDataBinding.second.state.setOnClickListener(this);
        viewDataBinding.first.addImageIndividuals.setOnClickListener(this);
        viewDataBinding.first.nationality.setOnClickListener(this);
        viewDataBinding.third.cv.setOnClickListener(this);
        viewDataBinding.third.license.setOnClickListener(this);
        viewDataBinding.first.radioGroupGender.setOnCheckedChangeListener(this::selectGender);
        viewDataBinding.first.radioGroupPermanence.setOnCheckedChangeListener(this::selectPermanenceType);
        viewDataBinding.second.radioGroupDesireToWork.setOnCheckedChangeListener(this::selectDesireToWork);
    }



    private void submit() {
        viewDataBinding.nextText.setText(getString(R.string.select_your_profession));
        notNext=true;
    }

    private void next() {
        viewDataBinding.nextText.setText(getString(R.string.next));
        notNext=false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void next(View view) {
        if (!notNext) {
            if (idx_state == array_state.length - 1) {
                return;
            }
            idx_state++;
            displayLayout(array_state[idx_state]);
        } else {

            if(validate()){
                Tools.hideKeyboard(getActivity());
                SharedPreferencesManager.getInstance().setUser(generateUser());
                openActivity(MainActivity.class);
            }else{

                Tools.showMessage(getString(R.string.valid_info));
            }
        }


    }

    public void previous(View view) {
        if (idx_state < 1) {

            return;
        }
        idx_state--;
        displayLayout(array_state[idx_state]);
    }



    private void displayLayout(State state) {

        if (state.name().equalsIgnoreCase(State.FIRST.name())) {
            viewDataBinding.imageSecond.setColorFilter(getResources().getColor(R.color.grey_10));
            next();
            viewDataBinding.first.first.setVisibility(View.VISIBLE);
            viewDataBinding.second.second.setVisibility(View.GONE);
            viewDataBinding.third.third.setVisibility(View.GONE);
        } else if (state.name().equalsIgnoreCase(State.SECOND.name())) {

            viewDataBinding.imageFirst.setColorFilter(getResources().getColor(R.color.Red), PorterDuff.Mode.SRC_ATOP);
            viewDataBinding.imageSecond.setColorFilter(getResources().getColor(R.color.Red), PorterDuff.Mode.SRC_ATOP);
            viewDataBinding.imageThird.setColorFilter(getResources().getColor(R.color.grey_10));
            viewDataBinding.lineFirst.setBackgroundColor(getResources().getColor(R.color.Red));
            viewDataBinding.lineSecond.setBackgroundColor(getResources().getColor(R.color.Red));

            next();
            viewDataBinding.first.first.setVisibility(View.GONE);
            viewDataBinding.second.second.setVisibility(View.VISIBLE);
            viewDataBinding.third.third.setVisibility(View.GONE);
        } else if (state.name().equalsIgnoreCase(State.THIRD.name())) {
           // initMap();
            viewDataBinding.lineFirst.setBackgroundColor(getResources().getColor(R.color.green));
            viewDataBinding.lineSecond.setBackgroundColor(getResources().getColor(R.color.green));
            viewDataBinding.imageFirst.setColorFilter(getResources().getColor(R.color.green));
            viewDataBinding.imageSecond.setColorFilter(getResources().getColor(R.color.green));
            viewDataBinding.imageThird.setColorFilter(getResources().getColor(R.color.green));

            submit();
            viewDataBinding.first.first.setVisibility(View.GONE);
            viewDataBinding.second.second.setVisibility(View.GONE);
            viewDataBinding.third.third.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void internetConnected() {

    }

    @Override
    public void internetNotConnected() {
        if (getFragmentManager() != null)
            Transactions.replaceFragmentWithAnimation(NoInternetConnectionFragment.newInstance(), R.id.container_register, true, getFragmentManager());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        if (isPermissionGive()) {
            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            getCurrentLocation();
            getLastLocation();


            googleMap.setOnMapClickListener(latLng -> {

                googleMap.clear();

                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(getString(R.string.current_location))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pickup)))
                        .setDraggable(true);

                CameraPosition camera = new CameraPosition.Builder()
                        .target(latLng)
                        .zoom(17f)
                        .bearing(90)
                        .tilt(30)
                        .build();

                latitude = latLng.latitude;
                longitude = latLng.longitude;

                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
            });

            googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {

                }
            });

        } else {

            givePermission();
        }

    }

    private boolean isPermissionGive() {
        return (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    private void givePermission() {
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(this)
                .check();
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {
        getCurrentLocation();
    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {
        Tools.showMessage(getString(R.string.required_location));
        if (getFragmentManager() != null)
            getFragmentManager().popBackStack();
    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
        token.continuePermissionRequest();
    }

    private void getCurrentLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval((long) 10 * 1000);
        locationRequest.setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);

        LocationSettingsRequest locationSettingsRequest = builder.build();

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(Objects.requireNonNull(getActivity())).checkLocationSettings(locationSettingsRequest);

        result.addOnCompleteListener(task -> {

            try {
                LocationSettingsResponse locationSettingsResponse = task.getResult(ApiException.class);

                if (Objects.requireNonNull(locationSettingsResponse.getLocationSettingsStates()).isLocationPresent()) {
                    getLastLocation();
                }


            } catch (ApiException apiException) {
                switch (apiException.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED: {
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) apiException;
                            resolvableApiException.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);


                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE: {
                    }
                }
            }

        });
    }
    private void getLastLocation() {

        if (getActivity() != null)
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful() && task.getResult() != null) {
                        Location location = task.getResult();

                        String address = "No know address";

                        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                        List<Address> addresses = new ArrayList<>();

                        try {

                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            if (!addresses.isEmpty()) {
                                address = addresses.get(0).getAddressLine(0);
                            }

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                        googleMap.clear();

                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(getString(R.string.current_location))
                                .snippet(address)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pickup)));

                        CameraPosition camera = new CameraPosition.Builder()
                                .target(latLng)
                                .zoom(17f)
                                .bearing(90)
                                .tilt(30)
                                .build();

                        latitude = latLng.latitude;
                        longitude = latLng.longitude;

                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));

                    } else {
                        getLastLocation();
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private User generateUser() {

        User user = new User();
        if (viewDataBinding.first.imageProfileIndividuals.getTag().equals(getString(R.string.not_selected))) {
            user.setImage("");
        } else {
            user.setImage(Tools.convertImageToBase64(viewDataBinding.first.imageProfileIndividuals));
        }
        if (viewDataBinding.third.cv.getTag().equals(getString(R.string.not_selected))) {
            user.setResume("");
            user.setResumeType("");
        } else {
            user.setResume(Tools.convertToString(cv));
            user.setResumeType(MimeTypeMap.getFileExtensionFromUrl(cv.getPath()));
//            String uriString = cv.toString();
//
//            File myFile = new File(uriString);
//
//            final String path = myFile.getAbsolutePath().substring(myFile.getAbsolutePath().lastIndexOf("."));
//            user.setResumeType(path);
        }
        if (viewDataBinding.third.license.getTag().equals(getString(R.string.not_selected))) {
            user.setProfessionLicence("");
        } else {
            user.setProfessionLicence(Tools.convertImageToBase64(viewDataBinding.third.license0));

        }
        user.setName(viewDataBinding.first.name.getText().toString());
        user.setEmail(viewDataBinding.third.email.getText().toString());
        user.setPhone(viewDataBinding.third.mobile.getText().toString());
        user.setPassword(viewDataBinding.third.password.getText().toString());
        user.setCountryId(countryId);
        user.setStateId(stateId);
        user.setDistrict(viewDataBinding.second.city.getText().toString());
        user.setUserType("2");
        user.setGenderId(genderId);
        user.setNationalityId(nationalityId);
        user.setAge(viewDataBinding.first.age.getText().toString());
        user.setPermanenceTypeId(permanenceId);
        if(viewDataBinding.first.haveLicense.isChecked())
        {
            user.setHaveLicenceId("1");
        }else{
            user.setHaveLicenceId("2");
        }
        user.setDesireWork(desiredWorkId);
        user.setSalaryPerDay(viewDataBinding.second.day.getText().toString());
        user.setHourlySalary(viewDataBinding.second.clock.getText().toString());
        user.setSalaryPerMonth(viewDataBinding.second.mounth.getText().toString());
        user.setExperience(viewDataBinding.first.experience.getText().toString());
        user.setJob(viewDataBinding.first.job.getText().toString());

        return user;
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), Const.PICKED_IMAGE);
    }

    private void selectLicense() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), Const.SELECT_LICENSE);
    }

    private void selectResume() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), Const.SELECT_CV);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == Const.PICKED_IMAGE) {
            try {
                if (resultCode == RESULT_OK && data != null) {

                    viewDataBinding.first.imageProfileIndividuals.setTag(getString(R.string.selected));

                    Uri selectedImage = data.getData();

                    viewDataBinding.first.imageProfileIndividuals.setImageURI(selectedImage);

                } else {

                    viewDataBinding.first.imageProfileIndividuals.setTag(getString(R.string.not_selected));
                }
            } catch (Exception exception) {

                viewDataBinding.first.imageProfileIndividuals.setTag(getString(R.string.not_selected));
            }
        }
        if (requestCode == Const.SELECT_LICENSE) {
            try {
                if (resultCode == RESULT_OK && data != null) {

                    viewDataBinding.third.license.setTag(getString(R.string.selected));

                   license= data.getData();

                    viewDataBinding.third.license0.setImageURI(license);
                    viewDataBinding.third.license.setText(license.toString());

                } else {

                    viewDataBinding.third.license.setTag(getString(R.string.not_selected));
                }
            } catch (Exception exception) {

                viewDataBinding.third.license.setTag(getString(R.string.not_selected));
            }
        }
        if (requestCode == Const.SELECT_CV) {
            try {
                if (resultCode == RESULT_OK && data != null) {

                    cv=data.getData();



                    viewDataBinding.third.cv.setTag(getString(R.string.selected));

                    viewDataBinding.third.cv.setText(cv.getPath());

                } else {

                    viewDataBinding.first.imageProfileIndividuals.setTag(getString(R.string.not_selected));
                }
            } catch (Exception exception) {

                viewDataBinding.first.imageProfileIndividuals.setTag(getString(R.string.not_selected));
            }
        }

//        switch (requestCode) {
//            case REQUEST_CHECK_SETTINGS:
//                if (resultCode == Activity.RESULT_OK) {
//                    getCurrentLocation();
//                }
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.add_image_individuals:
                selectImage();
                break;
            case R.id.license:
                selectLicense();
                break;

            case R.id.country:
                selectCountry();
                break;

            case R.id.state:
                selectState();
                break;

            case R.id.nationality:
                selectNationality();
                break;

            case R.id.cv:
                selectResume();
                break;


        }
    }


    public void selectDesireToWork(RadioGroup radioGroup, int id) {


        if (id == viewDataBinding.second.radioLocal.getId())

            desiredWorkId = "1";
        else if (id == viewDataBinding.second.radioInside.getId())

            desiredWorkId = "2";
        else if (id == viewDataBinding.second.radioInternational.getId())

            desiredWorkId = "3";
    }
    public void selectPermanenceType(RadioGroup radioGroup, int id) {


        if (id == viewDataBinding.first.radioFull.getId())

            permanenceId = "1";
        else if (id == viewDataBinding.first.radioPart.getId())

            permanenceId = "2";
    }
    public void selectGender(RadioGroup radioGroup, int id) {


        if (id == viewDataBinding.first.radioMale.getId())

            genderId = "1";
        else if (id == viewDataBinding.first.radioFemale.getId())

            genderId = "2";

        Log.e("gender",genderId);
    }

    private void selectCountry() {

        presenter.getAllCountries();

        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.layout_bottom, null);

        TextView textView=view.findViewById(R.id.title);
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

        TextView textView=view.findViewById(R.id.title);
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
    private void selectNationality() {


        presenter.getAllNationality();
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.layout_bottom, null);

        TextView textView=view.findViewById(R.id.title);
        textView.setText(R.string.select_nationality);

        RecyclerView recycler = view.findViewById(R.id.recyclerview_bottom);

        mBottomSheetDialog = new BottomSheetDialog(getContext());
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(dialog -> mBottomSheetDialog = null);
        nationalityAdapter  = new NationalityAdapter();
//        nationalityAdapter.setAnimation_type(ItemAnimation.FADE_IN);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(nationalityAdapter);
        nationalityAdapter.setItemClickListener(this);
    }


    @Override
    public void onItemClick(RecyclerView.Adapter adapter, View view, int position) {
        if (adapter instanceof CountryAdapter) {

            DefaultData country = countryAdapter.getCountries(position);
            viewDataBinding.second.country.setText(country.getTitle());
            countryId = country.getId();
            mBottomSheetDialog.hide();
        }else  if (adapter instanceof StateAdapter) {
            DefaultData state = stateAdapter.getState(position);
            viewDataBinding.second.state.setText(state.getTitle());
            stateId = state.getId();
            mBottomSheetDialog.hide();
        }else  if (adapter instanceof NationalityAdapter) {
            DefaultData nationality = nationalityAdapter.getNationality(position);
            viewDataBinding.first.nationality.setText(nationality.getTitle());
            nationalityId = nationality.getId();
            mBottomSheetDialog.hide();
        }
    }


    public boolean validate() {

        String inputEmail = viewDataBinding.third.emailLayout.getEditText().getText().toString();
        String inputName = viewDataBinding.first.nameLayout.getEditText().getText().toString();
        String inputMobile = viewDataBinding.third.mobileLayout.getEditText().getText().toString();
        String inputPassword = viewDataBinding.third.passwordLayout.getEditText().getText().toString();

        if (inputName.isEmpty()|| inputName.length() < 5) {
            viewDataBinding.first.nameLayout.setErrorEnabled(true);
            viewDataBinding.first.nameLayout.setError(getString(R.string.valid_name));
            return false;
        } else {
            viewDataBinding.first.nameLayout.setErrorEnabled(false);
        }

        if (inputMobile.isEmpty()) {
            viewDataBinding.third.mobileLayout.setErrorEnabled(true);
            viewDataBinding.third.mobileLayout.setError(getString(R.string.valid_phone));
            return false;
        } else {
            viewDataBinding.third.mobileLayout.setErrorEnabled(false);
        }

        if (inputEmail.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {
            viewDataBinding.third.emailLayout.setErrorEnabled(true);
            viewDataBinding.third.emailLayout.setError(getString(R.string.enter_valid_emails));
            return false;
        } else {
            viewDataBinding.third.emailLayout.setErrorEnabled(false);
        }

        if (inputPassword.isEmpty() || inputPassword.length() < 3 || inputPassword.length() > 20)
        {
            viewDataBinding.third.passwordLayout.setErrorEnabled(true);
            viewDataBinding.third.passwordLayout.setError(getString(R.string.valid_pass));
        } else {
            viewDataBinding.third.passwordLayout.setErrorEnabled(false);

        }

        if (!viewDataBinding.third.checkTerms.isChecked()) {
            viewDataBinding.third.checkTerms.setError(getString(R.string.you_must_agree_terms_and_conditions));
            return false;
        } else {
            viewDataBinding.third.checkTerms.setError(null);
        }



        return true;
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
