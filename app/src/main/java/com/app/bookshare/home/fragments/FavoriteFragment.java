package com.app.bookshare.home.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.bookshare.R;
import com.app.bookshare.adapter.FavoriteListAdapter;
import com.app.bookshare.app.App;
import com.app.bookshare.base.BaseFragment;
import com.app.bookshare.callbacks.InternetListener;
import com.app.bookshare.callbacks.PostListener;
import com.app.bookshare.conifig.Const;
import com.app.bookshare.conifig.SharedPreferencesManager;
import com.app.bookshare.databinding.FragmentCategoryBinding;
import com.app.bookshare.databinding.FragmentFavoriteBinding;
import com.app.bookshare.databinding.ItemMaterialBinding;
import com.app.bookshare.home.MainInterface;
import com.app.bookshare.home.MainPresenter;
import com.app.bookshare.models.Material;
import com.app.bookshare.utilities.Tools;
import com.app.bookshare.utilities.Transactions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class FavoriteFragment extends BaseFragment<MainPresenter, FragmentFavoriteBinding> implements InternetListener, MainInterface.FavoriteView, PostListener {


    private FavoriteListAdapter favoriteListAdapter;
    private FirebaseFirestore firebaseFirestore;


    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }



    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_favorite;
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
        firebaseFirestore = FirebaseFirestore.getInstance();
        setupCategoryAdapter();

    }



    private void setupCategoryAdapter() {
        favoriteListAdapter = new FavoriteListAdapter();
        viewDataBinding.recyclerviewCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.recyclerviewCategories.setAdapter(favoriteListAdapter);
        favoriteListAdapter.setPostListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null)
            getActivity().setTitle(getString(R.string.favorite));
    }
    @Override
    public void internetConnected() {

        presenter.getFavorite();

    }

    @Override
    public void internetNotConnected() {
        if (getFragmentManager() != null)
            Transactions.replaceFragmentWithAnimation(NoInternetConnectionFragment.newInstance(), R.id.container, true, getFragmentManager());
    }



    @Override
    public void makeFavorite(View view, int position, ItemMaterialBinding itemMaterialBinding) {


        DocumentReference favorite = firebaseFirestore.collection(getString(R.string.materials)).document(favoriteListAdapter.getFavorite(position).getPostID());

        if (favoriteListAdapter.getFavorite(position).getListFavorite().contains(SharedPreferencesManager.getInstance().getUser().getUser_id())) {

            Log.e("FAVORITE ", " : " + "REMOVE");

            favorite.update("listFavorite", FieldValue.arrayRemove(SharedPreferencesManager.getInstance().getUser().getUser_id()));

            itemMaterialBinding.favorite.setImageResource(R.drawable.un_favorite);

        } else {

            Log.e("FAVORITE ", " : " + "ADD");
            favorite.update("listFavorite", FieldValue.arrayUnion(SharedPreferencesManager.getInstance().getUser().getUser_id()));
            itemMaterialBinding.favorite.setImageResource(R.drawable.favorite);

        }

    }


    @Override
    public void goToWhatsApp(View view, int position) {

        Material material=favoriteListAdapter.getFavorite(position);

        if(material.getUser().getPhone_number()!=null){
            String url = "https://api.whatsapp.com/send?phone=+" + material.getUser().getPhone_number();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }else{

            Tools.showMessage(getString(R.string.empty_phone_number));
        }


    }

    @Override
    public void sendEmail(View view, int position) {
        Material material=favoriteListAdapter.getFavorite(position);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{material.getUser().getEmail()});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.communicate_with) + " " + material.getUser().getEmail());
        intent.setPackage("com.google.android.gm");
        if (intent.resolveActivity(App.getAppContext().getPackageManager()) != null)
            startActivity(intent);
        else
            Tools.showMessage(getString(R.string.no_gmail));

    }

    @Override
    public void makeCall(View view, int position) {


        Material material=favoriteListAdapter.getFavorite(position);


        Intent intent = new Intent(Intent.ACTION_DIAL);

        if(material.getUser().getPhone_number() != null)
        {
            if (material.getUser().getPhone_number().trim().isEmpty()) {
                Tools.showMessage(getContext().getString(R.string.empty_phone_number));
            } else {
                intent.setData(Uri.parse("tel:" + material.getUser().getPhone_number()));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.CALL_PHONE}, Const.MY_PERMISIONS_REQUEST_MAKE_CALL);

                } else {
                    startActivity(intent);
                }

            }
        }else{
            Tools.showMessage(getContext().getString(R.string.empty_phone_number));
        }

    }

    @Override
    public void allFavorite(List<Material> favorite) {
        favoriteListAdapter.setFavorite(favorite);
    }
}
