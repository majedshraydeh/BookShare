package com.fikrabd.mehna_w_amal.home.fragment;


import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.adapter.CountryAdapter;
import com.fikrabd.mehna_w_amal.adapter.SubCategoryAdapter;
import com.fikrabd.mehna_w_amal.base.BaseFragment;
import com.fikrabd.mehna_w_amal.callbacks.InternetListener;
import com.fikrabd.mehna_w_amal.callbacks.ItemClickListener;
import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.databinding.FragmentCategorySelectionBinding;
import com.fikrabd.mehna_w_amal.home.MainActivity;
import com.fikrabd.mehna_w_amal.home.MainInterface;
import com.fikrabd.mehna_w_amal.home.MainPresenter;
import com.fikrabd.mehna_w_amal.models.SubCategory;
import com.fikrabd.mehna_w_amal.models.User;
import com.fikrabd.mehna_w_amal.network.fragment.NoInternetConnectionFragment;
import com.fikrabd.mehna_w_amal.utilities.ItemAnimation;
import com.fikrabd.mehna_w_amal.utilities.Transactions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class CategorySelectionFragment extends BaseFragment<MainPresenter, FragmentCategorySelectionBinding> implements InternetListener, ItemClickListener, MainInterface.SubCategoryView {


    SubCategoryAdapter subCategoryAdapter;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;


    String title="";
    String subId="";

    public static CategorySelectionFragment newInstance() {
        return new CategorySelectionFragment();
    }

    public static CategorySelectionFragment newInstance(String categoryId, String title) {

        CategorySelectionFragment categorySelectionFragment = new CategorySelectionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Const.CATEGORY_ID, categoryId);
        bundle.putString(Const.TITLE, title);
        categorySelectionFragment.setArguments(bundle);

        return categorySelectionFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            getActivity().setTitle(getTitle());
        }

    }

    private String getCategoriesId() {
        String categoriesId = "";
        if (getArguments() != null) {
            categoriesId = getArguments().getString(Const.CATEGORY_ID);
        }
        return categoriesId;
    }

    private String getTitle() {
        String title = "";
        if (getArguments() != null) {
            title = getArguments().getString(Const.TITLE);
        }
        return title;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_category_selection;
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
        mBehavior = BottomSheetBehavior.from(viewDataBinding.bottomSheet);

        viewDataBinding.selectSub.setOnClickListener(view1 -> {
            selectSubCategory();
        });
    }


    private void selectSubCategory() {

        presenter.getSubCategory(getCategoriesId());

        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.layout_bottom, null);
        TextView textView = view.findViewById(R.id.title);
        textView.setText(R.string.category_selection);
        RecyclerView recycler = view.findViewById(R.id.recyclerview_bottom);
        mBottomSheetDialog = new BottomSheetDialog(getContext());
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(dialog -> mBottomSheetDialog = null);
        subCategoryAdapter = new SubCategoryAdapter();
        subCategoryAdapter.setAnimation_type(ItemAnimation.FADE_IN);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(subCategoryAdapter);
        subCategoryAdapter.setItemClickListener(this);
    }


    public void next(View view) {

        if (getFragmentManager() != null)
        Transactions.replaceFragmentWithAnimation(CategoryOfPepoleFragment.newInstance(subId,title),R.id.container,true,getFragmentManager());


    }

    @Override
    public void internetConnected() {


    }

    @Override
    public void internetNotConnected() {
        if (getFragmentManager() != null)
            Transactions.replaceFragmentWithAnimation(NoInternetConnectionFragment.newInstance(), R.id.container, true, getFragmentManager());
    }


    @Override
    public void onItemClick(RecyclerView.Adapter adapter, View view, int position) {

        if (adapter instanceof SubCategoryAdapter)
        {
            SubCategory subCategory= subCategoryAdapter.getSubCategory(position);


            title = subCategory.getTitle();
            viewDataBinding.selectSub.setText(title);
            subId=subCategory.getId();
            mBottomSheetDialog.hide();

        }

    }

    @Override
    public void allSubCategory(List<SubCategory> subCategories) {
        subCategoryAdapter.setSubCategory(subCategories);
    }

    @Override
    public void noSubCategory() {

    }

    @Override
    public void onRegisterSuccess(User user) {

    }

    @Override
    public void onRegisterFailure(String message) {

    }

    @Override
    public void onRegisterError(String message) {

    }
}
