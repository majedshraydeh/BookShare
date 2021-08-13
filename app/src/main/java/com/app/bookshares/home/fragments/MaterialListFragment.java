package com.app.bookshares.home.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bookshares.R;
import com.app.bookshares.adapter.MaterialListAdapter;
import com.app.bookshares.adapter.SubCategoryAdapter;
import com.app.bookshares.app.App;
import com.app.bookshares.base.BaseFragment;
import com.app.bookshares.callbacks.InternetListener;
import com.app.bookshares.callbacks.ItemClickListener;
import com.app.bookshares.callbacks.PostListener;
import com.app.bookshares.conifig.Const;
import com.app.bookshares.conifig.SharedPreferencesManager;
import com.app.bookshares.databinding.FragmentMaterialListBinding;
import com.app.bookshares.databinding.FragmentSubCategoryBinding;
import com.app.bookshares.databinding.ItemMaterialBinding;
import com.app.bookshares.home.MainInterface;
import com.app.bookshares.home.MainPresenter;
import com.app.bookshares.models.Material;
import com.app.bookshares.models.SubCategory;
import com.app.bookshares.utilities.ItemAnimation;
import com.app.bookshares.utilities.Tools;
import com.app.bookshares.utilities.Transactions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.app.bookshares.utilities.Tools.isNetworkAvailable;


public class MaterialListFragment extends BaseFragment<MainPresenter, FragmentMaterialListBinding> implements InternetListener, MainInterface.MaterialView, PostListener, SearchView.OnQueryTextListener {


    private MaterialListAdapter materialListAdapter;
//    private Uri fileUri;
    EditText subject, teacher, fileName;
    private FirebaseFirestore firebaseFirestore;


    public static MaterialListFragment newInstance() {
        return new MaterialListFragment();
    }


    public static MaterialListFragment newInstance(String title, String id) {

        MaterialListFragment materialListFragment = new MaterialListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Const.TITLE, title);
        bundle.putString(Const.ID, id);
        materialListFragment.setArguments(bundle);

        return materialListFragment;
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_material_list;
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
        viewDataBinding.search.setOnQueryTextListener(this);
        setupMaterialAdapter();

    }


    private void setupMaterialAdapter() {
        materialListAdapter = new MaterialListAdapter();
        materialListAdapter.setAnimation_type(ItemAnimation.FADE_IN);
        viewDataBinding.recyclerviewSubCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.recyclerviewSubCategories.setAdapter(materialListAdapter);
        materialListAdapter.setPostListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null)
            getActivity().setTitle(getTitle());
    }

    private String getSubCategoryId() {
        String id = "";
        if (getArguments() != null)
            id = getArguments().getString(Const.ID);

        return id;
    }

    private String getTitle() {
        String title = "";

        if (getArguments() != null)
            title = getArguments().getString(Const.TITLE);

        return title;
    }

    @Override
    public void internetConnected() {

        presenter.getMaterial(getSubCategoryId());
    }

    @Override
    public void internetNotConnected() {
        if (getFragmentManager() != null)
            Transactions.replaceFragmentWithAnimation(NoInternetConnectionFragment.newInstance(), R.id.container, true, getFragmentManager());
    }



    @Override
    public void allMaterial(List<Material> materials) {
        materialListAdapter.setMaterials(materials);
    }

    @Override
    public void materialUploaded(String message) {
        hideProgressDialog();
        Tools.showMessage(message);
    }

    public void openFileDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_file_dialog);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        subject = dialog.findViewById(R.id.subject);
        teacher = dialog.findViewById(R.id.course_teacher);
        fileName = dialog.findViewById(R.id.file_name);

        dialog.findViewById(R.id.upload).setOnClickListener(v -> {

            showProgressDialog(getString(R.string.uploading));
            presenter.addMaterial(subject.getText().toString(), teacher.getText().toString(), fileName.getText().toString(), getSubCategoryId());

            dialog.dismiss();
        });


        dialog.findViewById(R.id.cancel).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    @Override
    public void makeFavorite(View view, int position, ItemMaterialBinding itemMaterialBinding) {


        DocumentReference favorite = firebaseFirestore.collection(getString(R.string.materials)).document(materialListAdapter.getMaterial(position).getPostID());

        if (materialListAdapter.getMaterial(position).getListFavorite().contains(SharedPreferencesManager.getInstance().getUser().getUser_id())) {

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

        Material material=materialListAdapter.getMaterial(position);

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
        Material material=materialListAdapter.getMaterial(position);

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


        Material material=materialListAdapter.getMaterial(position);


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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (isNetworkAvailable(getContext())) {
            materialListAdapter.getFilter().filter(newText);
        }
        return false;
    }
}

