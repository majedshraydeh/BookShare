package com.fikrabd.mehna_w_amal.home.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.adapter.CategoryAdapter;
import com.fikrabd.mehna_w_amal.base.BaseFragment;
import com.fikrabd.mehna_w_amal.callbacks.InternetListener;
import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.databinding.FragmentCategoryBinding;
import com.fikrabd.mehna_w_amal.databinding.FragmentInformationBinding;
import com.fikrabd.mehna_w_amal.home.MainInterface;
import com.fikrabd.mehna_w_amal.home.MainPresenter;
import com.fikrabd.mehna_w_amal.models.Category;
import com.fikrabd.mehna_w_amal.models.Rating;
import com.fikrabd.mehna_w_amal.models.User;
import com.fikrabd.mehna_w_amal.network.fragment.NoInternetConnectionFragment;
import com.fikrabd.mehna_w_amal.utilities.Tools;
import com.fikrabd.mehna_w_amal.utilities.Transactions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;


public class InformationFragment extends BaseFragment<MainPresenter, FragmentInformationBinding> implements MainInterface.UserDetailsView, InternetListener {




    User user;
    public static InformationFragment newInstance() {
        return new InformationFragment();
    }

    public static InformationFragment newInstance( String userId) {
        InformationFragment informationFragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Const.USER_ID, userId);
        informationFragment.setArguments(bundle);
        return informationFragment;
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_information;
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

        if (getArguments() != null) {
            presenter.userDetails(getArguments().getString(Const.USER_ID));
        }
    }

    @Override
    public void internetNotConnected() {
        if (getFragmentManager() != null)
            Transactions.replaceFragmentWithAnimation(NoInternetConnectionFragment.newInstance(), R.id.container, true, getFragmentManager());
    }


    @Override
    public void allUserDetails(User user) {

        viewDataBinding.setUser(user);
        this.user=user;

    }

    @Override
    public void noUserDetails() {

    }

    @Override
    public void rated(String message, Rating rating) {

        hideProgressDialog();
        Tools.showSnackBar(getActivity(), message);
    }


    public void onRateClick(View view) {
            showProgressDialog("", getString(R.string.rating));
            presenter.rating(user.getId(), String.valueOf((int) viewDataBinding.rating.getRating()));
        }


}
