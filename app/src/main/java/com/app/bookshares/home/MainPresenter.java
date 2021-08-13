package com.app.bookshares.home;


import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableBoolean;

import com.app.bookshares.R;
import com.app.bookshares.app.App;
import com.app.bookshares.conifig.SharedPreferencesManager;
import com.app.bookshares.models.Category;
import com.app.bookshares.models.Material;
import com.app.bookshares.models.SubCategory;
import com.app.bookshares.models.User;
import com.app.bookshares.utilities.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainPresenter implements MainInterface.MainPresenterView {
    private DocumentReference documentReference;
    private FirebaseStorage firebaseStorage;
    private FirebaseFirestore firebaseFirestore;
    public ObservableBoolean loading;
    private MainInterface.CategoryView categoryView;
    private MainInterface.SubCategoryView subCategoryView;
    private MainInterface.MaterialView materialView;
    private MainInterface.UserView userView;
    private MainInterface.FavoriteView favoriteView;


    public MainPresenter() {

    }


    public MainPresenter(MainInterface.UserView userView) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.userView = userView;


    }

    public MainPresenter(MainInterface.CategoryView categoryView) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.categoryView = categoryView;
        loading = new ObservableBoolean();
        loading.set(true);

    }

    public MainPresenter(MainInterface.SubCategoryView subCategoryView) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.subCategoryView = subCategoryView;
        loading = new ObservableBoolean();
        loading.set(true);

    }

    public MainPresenter(MainInterface.MaterialView materialView) {
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.materialView = materialView;
        loading = new ObservableBoolean();
        loading.set(true);

    }

    public MainPresenter(MainInterface.FavoriteView favoriteView) {
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.favoriteView = favoriteView;
        loading = new ObservableBoolean();
        loading.set(true);

    }

    @Override
    public void getUserInfo() {

        documentReference = firebaseFirestore.collection(App.getAppContext().getString(R.string.users)).document(SharedPreferencesManager.getInstance().getUserID());
        documentReference.addSnapshotListener((documentSnapshot, e) -> {

            if (e != null) {
                Log.w("TAG", "Listen failed.", e);
                return;
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {

                User user = documentSnapshot.toObject(User.class);

                userView.userExists(user);
                Log.e("TAG", "Current data: " + documentSnapshot.getData());
            } else {

            }
        });

    }

    @Override
    public void getCategory() {

        firebaseFirestore.collection(App.getAppContext().getString(R.string.category))
                .addSnapshotListener((queryDocumentSnapshots, e) -> {

                    List<Category> categoryList = queryDocumentSnapshots.toObjects(Category.class);

                    categoryView.allCategories(categoryList);
                    loading.set(false);
                });
    }

    @Override
    public void getSubCategory(String categoryId) {

        Query query = firebaseFirestore.collection(App.getAppContext().getString(R.string.sub_category)).whereEqualTo("category_id", categoryId);


        query.addSnapshotListener((queryDocumentSnapshots, e) -> {

            List<SubCategory> subCategoryList = queryDocumentSnapshots.toObjects(SubCategory.class);

            subCategoryView.allSubCategory(subCategoryList);

            loading.set(false);

        });

    }

    @Override
    public void getMaterial(String subCategoryId) {
        Query query = firebaseFirestore.collection(App.getAppContext().getString(R.string.materials))
                .whereEqualTo("subId", subCategoryId);


        query.addSnapshotListener((queryDocumentSnapshots, e) -> {

            List<Material> materials=null;
            if (queryDocumentSnapshots!=null){

               materials = queryDocumentSnapshots.toObjects(Material.class);

            }

            materialView.allMaterial(materials);


            loading.set(false);

        });

//        firebaseFirestore.collection(App.getAppContext().getString(R.string.materials))
//                .orderBy("timestamp", Query.Direction.DESCENDING)
//                .whereEqualTo("subId",subCategoryId)
//                .addSnapshotListener((value, error) -> {
//
//                    if (value == null) {
//                        Log.d("TAG", "onSuccess: LIST EMPTY");
//                        return;
//                    } else {
//                        List<Material> materials = value.toObjects(Material.class);
//                        if (materials.size() != 0) {
//                            materialView.allMaterial(materials);
//                        }
//                    }
//
//                    loading.set(false);
//                });

    }

    @Override
    public void addMaterial(String subject, String teacher, String fileName, Uri file, String subCategoryId) {

        firebaseStorage.getReference().child(App.getAppContext().getString(R.string.files)).child(fileName).putFile(file).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseStorage.getReference().child(App.getAppContext().getString(R.string.files)).child(fileName).getDownloadUrl().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {

                        Uri download_uri = task1.getResult();

                        Map<String, Object> fileMap = new HashMap<>();

                        fileMap.put("file", download_uri.toString());
                        fileMap.put("subject", subject);
                        fileMap.put("file_name", fileName);
                        fileMap.put("teacher", teacher);
                        fileMap.put("subCategoryId", subCategoryId);


                        firebaseFirestore.collection(App.getAppContext().getString(R.string.materials)).document().set(fileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    materialView.materialUploaded("File was added");
                                }

                            }
                        });
                    }

                });
            }

        });
    }

    @Override
    public void addMaterial(String subject, String teacher, String fileName, String subCategoryId) {


        List<String> listFavorite = new ArrayList<>();
        Map<String, Object> fileMap = new HashMap<>();
        fileMap.put("subject", subject);
        fileMap.put("file_name", fileName);
        fileMap.put("teacher", teacher);
        fileMap.put("subId", subCategoryId);
        fileMap.put("user", SharedPreferencesManager.getInstance().getUser());
        fileMap.put("listFavorite", listFavorite);
        fileMap.put("time", java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));

        final DocumentReference documentReference =
                firebaseFirestore.collection(App.getAppContext().getString(R.string.materials)).document();

        fileMap.put("postID", documentReference.getId());

        documentReference.set(fileMap, SetOptions.merge());
        documentReference.addSnapshotListener((value, error) -> {

            if (value == null) {
                return;
            } else {

                materialView.materialUploaded("Added successfully");
            }


        });

//        firebaseFirestore.collection(App.getAppContext().getString(R.string.materials)).document().set(fileMap).addOnCompleteListener(task -> {
//
//            if (task.isSuccessful()) {
//                materialView.materialUploaded("Added successfully");
//            }
//
//        });
    }

    @Override
    public void getFavorite() {


        final List <Material> factories=new ArrayList<>();
        Task<QuerySnapshot> query = firebaseFirestore.collection(App.getAppContext().getString(R.string.materials)).get();


        query.addOnSuccessListener(queryDocumentSnapshots -> {


            List<Material> materials = null;
            if (queryDocumentSnapshots != null) {
                materials = queryDocumentSnapshots.toObjects(Material.class);
            }

            if (materials != null) {
                for (Material row :materials){

                    if (row.getListFavorite().contains(SharedPreferencesManager.getInstance().getUser().getUser_id())){

                        factories.add(row);
                    }
                }
            }

            favoriteView.allFavorite(factories);

            loading.set(false);

        });

    }


}


