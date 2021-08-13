package com.app.bookshare.home.fragments;


import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.bookshare.R;
import com.app.bookshare.adapter.SubCategoryAdapter;
import com.app.bookshare.base.BaseFragment;
import com.app.bookshare.callbacks.InternetListener;
import com.app.bookshare.callbacks.ItemClickListener;
import com.app.bookshare.conifig.Const;
import com.app.bookshare.databinding.FragmentSubCategoryBinding;
import com.app.bookshare.home.MainInterface;
import com.app.bookshare.home.MainPresenter;
import com.app.bookshare.models.SubCategory;
import com.app.bookshare.utilities.ItemAnimation;
import com.app.bookshare.utilities.Transactions;
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
