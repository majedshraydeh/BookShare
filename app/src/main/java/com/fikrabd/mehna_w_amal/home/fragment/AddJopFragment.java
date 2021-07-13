package com.fikrabd.mehna_w_amal.home.fragment;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.fikrabd.mehna_w_amal.PaymentActivity;
import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.adapter.JobAdapter;
import com.fikrabd.mehna_w_amal.adapter.PeopleAdapter;
import com.fikrabd.mehna_w_amal.base.BaseFragment;
import com.fikrabd.mehna_w_amal.callbacks.InternetListener;
import com.fikrabd.mehna_w_amal.callbacks.JobListener;
import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.databinding.FragmentAddJopBinding;
import com.fikrabd.mehna_w_amal.databinding.FragmentCategoryOfPepoleBinding;
import com.fikrabd.mehna_w_amal.databinding.ItemJobBinding;
import com.fikrabd.mehna_w_amal.home.MainActivity;
import com.fikrabd.mehna_w_amal.home.MainInterface;
import com.fikrabd.mehna_w_amal.home.MainPresenter;
import com.fikrabd.mehna_w_amal.models.SubCategory;
import com.fikrabd.mehna_w_amal.models.User;
import com.fikrabd.mehna_w_amal.network.fragment.NoInternetConnectionFragment;
import com.fikrabd.mehna_w_amal.utilities.Tools;
import com.fikrabd.mehna_w_amal.utilities.Transactions;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class AddJopFragment extends BaseFragment<MainPresenter, FragmentAddJopBinding> implements MainInterface.SubCategoryView, InternetListener, OnItemClickListener, JobListener {


    private JobAdapter jopAdapter;
    private SubCategory subCategory;
    private User user=new User();

    private List<String> listJob = new ArrayList<>();
    private String jobs;

    public static AddJopFragment newInstance() {
        return new AddJopFragment();
    }


    public static AddJopFragment newInstance(String categoryId, String title) {

        AddJopFragment addJopFragment = new AddJopFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Const.CATEGORY_ID, categoryId);
        bundle.putString(Const.TITLE, title);
        addJopFragment.setArguments(bundle);

        return addJopFragment;
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_add_jop;
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
        setupJobAdapter();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            getActivity().setTitle(getTitle());
        }

    }

    private String getCategoryId() {
        String categoryId = "";
        if (getArguments() != null) {
            categoryId = getArguments().getString(Const.CATEGORY_ID);
        }
        return categoryId;
    }

    private String getTitle() {
        String title = "";
        if (getArguments() != null) {
            title = getArguments().getString(Const.TITLE);
        }
        return title;
    }


    private void setupJobAdapter() {
        jopAdapter = new JobAdapter();
        viewDataBinding.recyclerviewItems.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.recyclerviewItems.setAdapter(jopAdapter);
        jopAdapter.setJobListener(this);
        jopAdapter.setOnItemClickListener(this);
    }

    @Override
    public void internetConnected() {

        user=SharedPreferencesManager.getInstance().getUser();
        presenter.getSubCategory(getCategoryId());

    }

    @Override
    public void internetNotConnected() {
        if (getFragmentManager() != null)
            Transactions.replaceFragmentWithAnimation(NoInternetConnectionFragment.newInstance(), R.id.container, true, getFragmentManager());
    }

    @Override
    public void allSubCategory(List<SubCategory> subCategories) {

        jopAdapter.setList(subCategories);
    }

    @Override
    public void noSubCategory() {

    }

    @Override
    public void onRegisterSuccess(User user) {
        hideProgressDialog();
        SharedPreferencesManager.getInstance().setCreate(false);
        SharedPreferencesManager.getInstance().setUser(user);
        goToPayment();
    }

    private void  goToPayment() {

        openActivityWithFinish(MainActivity.class);
    }


    @Override
    public void onRegisterFailure(String message) {

       hideProgressDialog();
        Tools.showMessage(message);
    }

    @Override
    public void onRegisterError(String message) {
        Log.e("errror",message);
        hideProgressDialog();
        Tools.showMessage(message);
    }

    @Override
    public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {

    }


    @Override
    public void onAddJob(View view, int position, ItemJobBinding itemJobBinding) {


        subCategory = jopAdapter.getItem(position);
        listJob.add(subCategory.getId());
        itemJobBinding.add.setVisibility(View.GONE);
        itemJobBinding.remove.setVisibility(View.VISIBLE);
        viewDataBinding.currency.setText(subCategory.getCurrency());
        double price = Double.parseDouble(viewDataBinding.price.getText().toString());
        double newPrice = Double.parseDouble(subCategory.getPrice());
        double totalPrice = price + newPrice;
        viewDataBinding.price.setText(String.valueOf(totalPrice));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRemoveJob(View view, int position, ItemJobBinding itemJobBinding) {

        subCategory = jopAdapter.getItem(position);
        listJob.remove(subCategory.getId());
        itemJobBinding.add.setVisibility(View.VISIBLE);
        itemJobBinding.remove.setVisibility(View.GONE);
        viewDataBinding.currency.setText(subCategory.getCurrency());
        double price = Double.parseDouble(viewDataBinding.price.getText().toString());
        double newPrice = Double.parseDouble(subCategory.getPrice());
        double totalPrice = price - newPrice;
        viewDataBinding.price.setText(String.valueOf(totalPrice));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void singUp(View view) {

        jobs = String.join(",", listJob);


        if (!jobs.isEmpty()) {
            showProgressDialog("", getString(R.string.authenticating));
            user.setSubCategoryId(jobs.trim());
            presenter.createUser(user);
        } else {

            Tools.showMessage(getString(R.string.choose_at_least_one_category));
        }

    }
}
