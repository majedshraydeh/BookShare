package com.fikrabd.mehna_w_amal.home.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.adapter.CategoryAdapter;
import com.fikrabd.mehna_w_amal.base.BaseFragment;
import com.fikrabd.mehna_w_amal.callbacks.InternetListener;
import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.databinding.FragmentCategoryBinding;
import com.fikrabd.mehna_w_amal.databinding.FragmentUserProfileBinding;
import com.fikrabd.mehna_w_amal.home.MainInterface;
import com.fikrabd.mehna_w_amal.home.MainPresenter;
import com.fikrabd.mehna_w_amal.models.Category;
import com.fikrabd.mehna_w_amal.models.User;
import com.fikrabd.mehna_w_amal.network.fragment.NoInternetConnectionFragment;
import com.fikrabd.mehna_w_amal.utilities.Tools;
import com.fikrabd.mehna_w_amal.utilities.Transactions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class UserProfileFragment extends BaseFragment<MainPresenter, FragmentUserProfileBinding> implements MainInterface.UpdateView, InternetListener {



    private User user;


    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_user_profile;
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
        viewDataBinding.setFragment(this);



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


    private User updateUser() {



        user.setImage(Tools.convertImageToBase64(viewDataBinding.userImageEdit));
        user.setName(viewDataBinding.name.getText().toString());
        user.setEmail(viewDataBinding.email.getText().toString());
        user.setPhone(viewDataBinding.phone.getText().toString());
        user.setPassword(viewDataBinding.age.getText().toString());

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

    }
}
