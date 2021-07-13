package com.fikrabd.mehna_w_amal.home.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.base.BaseFragment;
import com.fikrabd.mehna_w_amal.callbacks.InternetListener;
import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.databinding.FragmentCompanyProfileBinding;
import com.fikrabd.mehna_w_amal.home.MainInterface;
import com.fikrabd.mehna_w_amal.home.MainPresenter;
import com.fikrabd.mehna_w_amal.models.User;
import com.fikrabd.mehna_w_amal.network.fragment.NoInternetConnectionFragment;
import com.fikrabd.mehna_w_amal.utilities.Tools;
import com.fikrabd.mehna_w_amal.utilities.Transactions;
import static android.app.Activity.RESULT_OK;


public class CompanyProfileFragment extends BaseFragment<MainPresenter, FragmentCompanyProfileBinding> implements InternetListener, MainInterface.UpdateView {


//    private String flag;
//    private AlertDialog dialog;
//    private EditText filed;

    private User user;


    public static CompanyProfileFragment newInstance() {
        return new CompanyProfileFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_company_profile;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
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

//        viewDataBinding.commercialLayout.setOnClickListener(this::showAlertDialogButtonClicked);
//        viewDataBinding.commercialActivitiesLayout.setOnClickListener(this::showAlertDialogButtonClicked);
//        viewDataBinding.emailLayout.setOnClickListener(this::showAlertDialogButtonClicked);
//        viewDataBinding.phoneLayout.setOnClickListener(this::showAlertDialogButtonClicked);
//        viewDataBinding.cityLayout.setOnClickListener(this::showAlertDialogButtonClicked);


        viewDataBinding.setFragment(this);

        viewDataBinding.online.setOnToggledListener((toggleableView, isOn) -> {
            if(isOn){
               presenter.changeStatus(user.getId(),"1");
            }
            else {

                presenter.changeStatus(user.getId(),"2");
            }
        });


    }

    private User updateUser() {

        user.setImage(Tools.convertImageToBase64(viewDataBinding.userImageEdit));
        user.setCommercialActivitiesNo(viewDataBinding.commercial.getText().toString());
        user.setCommercialActivities(viewDataBinding.commercialActivities.getText().toString());
        user.setEmail(viewDataBinding.email.getText().toString());
        user.setPhone(viewDataBinding.phone.getText().toString());
        user.setStatic_phone(viewDataBinding.staticPhone.getText().toString());
        user.setWebsiteUrl(viewDataBinding.website.getText().toString());
        user.setDistrict(viewDataBinding.city.getText().toString());

        return user;
    }

    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), Const.PICKED_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == Const.PICKED_IMAGE) {
            try {
                if (resultCode == RESULT_OK && data != null) {

                    viewDataBinding.userImageEdit.setTag(getString(R.string.selected));

                    Uri selectedImage = data.getData();

                    viewDataBinding.userImageEdit.setImageURI(selectedImage);

                } else {

                    viewDataBinding.userImageEdit.setTag(getString(R.string.not_selected));
                }
            } catch (Exception exception) {

                viewDataBinding.userImageEdit.setTag(getString(R.string.not_selected));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void saveChange(){
        showProgressDialog("", getString(R.string.info_updated));
        presenter.updateUser(updateUser());

    }


    @Override
    public void internetConnected() {

        user=SharedPreferencesManager.getInstance().getUser();
        viewDataBinding.setUser(user);

    }

    @Override
    public void internetNotConnected() {
        if (getFragmentManager() != null)
            Transactions.replaceFragmentWithAnimation(NoInternetConnectionFragment.newInstance(), R.id.container, true, getFragmentManager());
    }

    @Override
    public void onUpdateSuccess(User user,String message) {
        hideProgressDialog();
        SharedPreferencesManager.getInstance().setUser(user);
        Tools.showMessage(message);
    }

    @Override
    public void onUpdateFailure(String message) {
        hideProgressDialog();
        Tools.showMessage(message);
    }

    @Override
    public void onUpdateError(String message) {
        hideProgressDialog();
        Tools.showMessage(message);
    }

    @Override
    public void onStatusChanged(String message) {
        Tools.showMessage(message);
    }


//    @SuppressLint("NonConstantResourceId")
//    public void showAlertDialogButtonClicked(View view) {
//
//
//        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_edit, null);
//        Button btnSave = customLayout.findViewById(R.id.save);
//        Button btnCancel = customLayout.findViewById(R.id.cancel);
//        filed = customLayout.findViewById(R.id.txt);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
//        builder.setView(customLayout);
//        dialog = builder.create();
//
//        switch (view.getId()) {
//
//            case R.id.commercial_layout:
//                flag = "1";
//                setTitleDialog();
//                break;
//
//            case R.id.commercial_activities_layout:
//                flag = "2";
//                setTitleDialog();
//                break;
//
//            case R.id.email_layout:
//                flag = "3";
//                setTitleDialog();
//                break;
//
//            case R.id.phone_layout:
//                flag = "4";
//                setTitleDialog();
//                break;
//
//            case R.id.city_layout:
//                flag = "5";
//                setTitleDialog();
//                break;
//        }
//
//
//        dialog.show();
//
//        btnSave.setOnClickListener(v -> update());
//
//        btnCancel.setOnClickListener(v -> dialog.dismiss());
//
//
//    }
//
//    private void update() {
//
//        viewDataBinding.commercial.setText(filed.getText().toString());
//        dialog.dismiss();
//
//
//    }
//
//    private void setTitleDialog() {
//
//
//        switch (flag) {
//
//            case "1":
//                dialog.setTitle(getString(R.string.edit_commercial_no));
//                break;
//
//            case "2":
//                dialog.setTitle(getString(R.string.edit_commercial_activities));
//                break;
//
//            case "3":
//                dialog.setTitle(getString(R.string.edit_email));
//                break;
//
//            case "4":
//                dialog.setTitle(getString(R.string.edit_phone));
//                break;
//
//            case "5":
//                dialog.setTitle(getString(R.string.edit_city));
//                break;
//
//
//        }
//
//
//    }


}
