package com.app.bookshares.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.app.bookshares.R;
import com.app.bookshares.base.BaseActivity;
import com.app.bookshares.conifig.Const;
import com.app.bookshares.conifig.SharedPreferencesManager;
import com.app.bookshares.databinding.ActivityMainBinding;
import com.app.bookshares.databinding.HeaderMainBinding;
import com.app.bookshares.home.fragments.CategoryFragment;
import com.app.bookshares.home.fragments.FavoriteFragment;
import com.app.bookshares.home.fragments.NoInternetConnectionFragment;
import com.app.bookshares.login.LoginActivity;
import com.app.bookshares.models.User;
import com.app.bookshares.utilities.Tools;
import com.app.bookshares.utilities.Transactions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.annotation.Nullable;

public class MainActivity extends BaseActivity<MainPresenter, ActivityMainBinding> implements MainInterface.MainView, NavigationView.OnNavigationItemSelectedListener {


    private ActionBarDrawerToggle toggle;
    private HeaderMainBinding headerMainBinding;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;
    private StorageReference storageReference;
    private User user;

    private Uri filePath;
    private FirebaseStorage storage;


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
        checkCurrentUser();
        bindHeader();
        Transactions.replaceFragment(CategoryFragment.newInstance(), R.id.container, true, getSupportFragmentManager());

    }

    private void init() {


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

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
                if (headerMainBinding != null) {
                    headerMainBinding.setUser(SharedPreferencesManager.getInstance().getUser());
                    headerMainBinding.setLoading(headerMainBinding.loading);

                }


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
        if (this.viewDataBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.viewDataBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void checkCurrentUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            openActivityWithFinish(LoginActivity.class);
        } else {
            initUser(firebaseAuth.getCurrentUser().getUid());
        }
    }

    private void initUser(String userID) {
        documentReference = firebaseFirestore.collection(getString(R.string.users)).document(userID);
        documentReference.addSnapshotListener((documentSnapshot, e) -> {

            if (e != null) {
                Log.w("TAG", "Listen failed.", e);
                return;
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {

                user = documentSnapshot.toObject(User.class);
                SharedPreferencesManager.getInstance().setUser(user);
                Log.e("TAG", "Current data: " + documentSnapshot.getData());
            }
        });

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
        }
        else if (currentFragment instanceof FavoriteFragment) {
            viewDataBinding.navView.setCheckedItem(R.id.favorite);
        }
//        else if (currentFragment instanceof CompanyProfileFragment) {
//            viewDataBinding.navView.setCheckedItem(R.id.profile);
//        }else if (currentFragment instanceof IndividualProfileFragment) {
//            viewDataBinding.navView.setCheckedItem(R.id.profile);
//        }


    }

    private void handleNavigationBack(Fragment fragmentById) {

        if (fragmentById instanceof CategoryFragment
          ||fragmentById instanceof FavoriteFragment) {

            if (getSupportActionBar() != null)
                getSupportActionBar().show();

                viewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                toggle.setDrawerIndicatorEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toggle.syncState();


        } else if (fragmentById instanceof NoInternetConnectionFragment) {

            if (getSupportActionBar() != null) getSupportActionBar().hide();


        } else {
            if (getSupportActionBar() != null) getSupportActionBar().show();
            viewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toggle.syncState();
            toggle.setToolbarNavigationClickListener(view -> onBackPressed());
        }
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
        }
        else if (id == R.id.favorite) {
            this.viewDataBinding.drawerLayout.closeDrawer(GravityCompat.START);
            Transactions.replaceFragment(FavoriteFragment.newInstance(), R.id.container, true, getSupportFragmentManager());
        }
        else if (id == R.id.set_Profile_image) {

            chooseImage();
        }
        else if (id == R.id.logout) {

            signOut();
        }

        return true;
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Const.PICKED_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.PICKED_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                headerMainBinding.imageProfile.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getString(R.string.uploading));
            progressDialog.show();

            final StorageReference ref = storageReference.child(Const.IMAGE_PROFILE).child(firebaseAuth.getCurrentUser().getUid());
            ref.putFile(filePath).addOnSuccessListener(taskSnapshot -> {
                progressDialog.dismiss();

                ref.getDownloadUrl().addOnSuccessListener(uri -> {

                    Uri downloadUrl = uri;
                    String fileUrl = downloadUrl.toString();

                    Log.e("TAG", "onSuccess: " + fileUrl);
                    final String userID = firebaseAuth.getCurrentUser().getUid();

                   updateProfile(fileUrl, userID);
                });

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Tools.showMessage(getString(R.string.failed) + " " + e.getMessage());
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }

    public void updateProfile(String url, String currentUserID) {

        documentReference = firebaseFirestore.collection(getString(R.string.users))
                .document(currentUserID);


        documentReference.update("image", url)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        Tools.showMessage(getString(R.string.upload_image));
                    } else {
                        Tools.showMessage(getString(R.string.try_again));
                    }

                });
    }


    private void signOut() {
        SharedPreferencesManager.getInstance().resetFields();
        openActivityWithClearTask(LoginActivity.class);
    }


}
