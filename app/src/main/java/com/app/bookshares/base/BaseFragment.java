package com.app.bookshares.base;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.app.bookshares.BR;
import com.app.bookshares.callbacks.DialogListener;
import com.app.bookshares.callbacks.InternetListener;
import com.app.bookshares.utilities.Tools;

import cn.pedant.SweetAlert.SweetAlertDialog;


public abstract class BaseFragment<P , T extends ViewDataBinding> extends Fragment {

    private static final String TAG = "BaseFragment";

    protected T viewDataBinding;
    protected P presenter;
    private SweetAlertDialog dialog;
    protected InternetListener internetListener;
    private DialogListener dialogListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBuilding(inflater, container);
        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkInternet();
    }

    private void initBuilding(LayoutInflater inflater, ViewGroup container) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false);
        presenter = createPresenter();
        viewDataBinding.setVariable(BR.presenter, presenter);
        viewDataBinding.executePendingBindings();
    }

    protected abstract int getLayoutResourceId();

    protected abstract P createPresenter();


    public void setInternetListener(InternetListener internetListener) {
        this.internetListener = internetListener;
    }

    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public void showProgressDialog(String title) {
        dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#FF9D0C"));
        dialog.setTitleText(title);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void hideProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void openActivity(Class<?> calledActivity) {
        Intent myIntent = new Intent(getContext(), calledActivity);
        this.startActivity(myIntent);
    }

    public void openActivityWithFinish(Class<?> calledActivity) {
        Intent myIntent = new Intent(getContext(), calledActivity);
        this.startActivity(myIntent);
        requireActivity().finish();
    }


    public void openActivityWithNoAnimationAndClearTask(Class<?> calledActivity) {
        Intent myIntent = new Intent(getContext(), calledActivity);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        this.startActivity(myIntent);
    }

    public void showDialog(String title, String message, String positiveText, String negativeText) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveText, (dialogInterface, i) -> {

                    if (dialogListener != null) {
                        dialogListener.onPositiveClick();
                    }
                    dialogInterface.dismiss();
                })
                .setNegativeButton(negativeText, (dialogInterface, i) -> {
                    if (dialogListener != null) {
                        dialogListener.onNegativeClick();
                    }
                    dialogInterface.dismiss();
                });

        alert.show();
    }

    private void checkInternet() {
        if (internetListener != null)
            if (Tools.isNetworkAvailable(getContext())) {
                internetListener.internetConnected();
            } else {
                internetListener.internetNotConnected();
            }
    }


}
