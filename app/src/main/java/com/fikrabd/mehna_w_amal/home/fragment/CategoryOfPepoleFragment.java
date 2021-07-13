package com.fikrabd.mehna_w_amal.home.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.adapter.CategoryAdapter;
import com.fikrabd.mehna_w_amal.adapter.PeopleAdapter;
import com.fikrabd.mehna_w_amal.base.BaseFragment;
import com.fikrabd.mehna_w_amal.callbacks.InternetListener;
import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.databinding.FragmentCategoryBinding;
import com.fikrabd.mehna_w_amal.databinding.FragmentCategoryOfPepoleBinding;
import com.fikrabd.mehna_w_amal.home.MainInterface;
import com.fikrabd.mehna_w_amal.home.MainPresenter;
import com.fikrabd.mehna_w_amal.models.Category;
import com.fikrabd.mehna_w_amal.models.User;
import com.fikrabd.mehna_w_amal.network.fragment.NoInternetConnectionFragment;
import com.fikrabd.mehna_w_amal.utilities.Transactions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;


public class CategoryOfPepoleFragment extends BaseFragment<MainPresenter, FragmentCategoryOfPepoleBinding> implements MainInterface.PeopleView, InternetListener, OnItemClickListener {


    private PeopleAdapter peopleAdapter;

    public static CategoryOfPepoleFragment newInstance() {
        return new CategoryOfPepoleFragment();
    }


    public static CategoryOfPepoleFragment newInstance(String subCategoryId, String title) {

        CategoryOfPepoleFragment categoryOfPepoleFragment = new CategoryOfPepoleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Const.SUB_CATEGORY_ID, subCategoryId);
        bundle.putString(Const.TITLE, title);
        categoryOfPepoleFragment.setArguments(bundle);

        return categoryOfPepoleFragment;
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_category_of_pepole;
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


        setupPeopleAdapter();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            getActivity().setTitle(getTitle());
        }

    }

    private String getSubCategoriesId() {
        String subCategoriesId = "";
        if (getArguments() != null) {
            subCategoriesId = getArguments().getString(Const.SUB_CATEGORY_ID);
        }
        return subCategoriesId;
    }

    private String getTitle() {
        String title = "";
        if (getArguments() != null) {
            title = getArguments().getString(Const.TITLE);
        }
        return title;
    }

    private void setupPeopleAdapter() {
        peopleAdapter = new PeopleAdapter();
        viewDataBinding.recyclerviewItems.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.recyclerviewItems.setAdapter(peopleAdapter);
        peopleAdapter.setOnItemClickListener(this);
    }
    @Override
    public void internetConnected() {

       presenter.getPeople(getSubCategoriesId());

    }

    @Override
    public void internetNotConnected() {
        if (getFragmentManager() != null)
            Transactions.replaceFragmentWithAnimation(NoInternetConnectionFragment.newInstance(), R.id.container, true, getFragmentManager());
    }


    @Override
    public void allPeople(List<User> subCategories) {
        peopleAdapter.setList(subCategories);
    }

    @Override
    public void noPeople() {

    }

    @Override
    public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {

        if (adapter instanceof PeopleAdapter) {
            User user = peopleAdapter.getItem(position);
            if (getFragmentManager() != null) {

                Transactions.replaceFragmentWithAnimation(InformationFragment.newInstance(user.getId()), R.id.container, true, getFragmentManager());
            }
        }

    }
}
