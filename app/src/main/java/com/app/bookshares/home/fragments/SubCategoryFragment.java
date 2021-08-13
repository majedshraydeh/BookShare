package com.app.bookshares.home.fragments;


import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.bookshares.R;
import com.app.bookshares.adapter.CategoryAdapter;
import com.app.bookshares.adapter.SubCategoryAdapter;
import com.app.bookshares.base.BaseFragment;
import com.app.bookshares.callbacks.InternetListener;
import com.app.bookshares.callbacks.ItemClickListener;
import com.app.bookshares.conifig.Const;
import com.app.bookshares.databinding.FragmentSubCategoryBinding;
import com.app.bookshares.home.MainInterface;
import com.app.bookshares.home.MainPresenter;
import com.app.bookshares.models.Category;
import com.app.bookshares.models.SubCategory;
import com.app.bookshares.utilities.ItemAnimation;
import com.app.bookshares.utilities.Transactions;
import java.util.List;


public class SubCategoryFragment extends BaseFragment<MainPresenter, FragmentSubCategoryBinding> implements InternetListener, MainInterface.SubCategoryView, ItemClickListener {


    private SubCategoryAdapter subCategoryAdapter;


    public static SubCategoryFragment newInstance() {
        return new SubCategoryFragment();
    }


    public static SubCategoryFragment newInstance(String title,String id) {

        SubCategoryFragment subCategoryFragment=new SubCategoryFragment();
        Bundle bundle =new Bundle();
        bundle.putString(Const.TITLE,title);
        bundle.putString(Const.ID,id);
        subCategoryFragment.setArguments(bundle);

        return  subCategoryFragment;
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_sub_category;
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

        setupSubCategoryAdapter();

    }



    private void setupSubCategoryAdapter() {
        subCategoryAdapter = new SubCategoryAdapter();
        subCategoryAdapter.setAnimation_type(ItemAnimation.FADE_IN);
        viewDataBinding.recyclerviewSubCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.recyclerviewSubCategories.setAdapter(subCategoryAdapter);
        subCategoryAdapter.setItemClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null)
            getActivity().setTitle(getTitle());
    }

    private String getCategoryId(){
        String id="";
        if (getArguments()!=null)
            id=getArguments().getString(Const.ID);

        return id;
    }

    private String getTitle() {
        String title="";

        if (getArguments()!=null)
            title=getArguments().getString(Const.TITLE);

        return title;
    }
    @Override
    public void internetConnected() {

        presenter.getSubCategory(getCategoryId());

    }

    @Override
    public void internetNotConnected() {
        if (getFragmentManager() != null)
            Transactions.replaceFragmentWithAnimation(NoInternetConnectionFragment.newInstance(), R.id.container, true, getFragmentManager());
    }



    @Override
    public void onItemClick(RecyclerView.Adapter adapter, View view, int position) {

        if (adapter instanceof SubCategoryAdapter) {
            SubCategory subCategory = subCategoryAdapter.getSubCategory(position);

            if(getFragmentManager()!=null)
                Transactions.replaceFragment(MaterialListFragment.newInstance(subCategory.getName(), subCategory.getId()),R.id.container,true,getFragmentManager());
        }
    }

    @Override
    public void allSubCategory(List<SubCategory> subCategories) {

        subCategoryAdapter.setSubCategory(subCategories);
    }

}
