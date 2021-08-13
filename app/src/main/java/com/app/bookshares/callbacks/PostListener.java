package com.app.bookshares.callbacks;

import android.view.View;

import com.app.bookshares.databinding.ItemMaterialBinding;


public interface PostListener {

    void makeFavorite(View view, int position, ItemMaterialBinding itemMaterialBinding);

    void goToWhatsApp(View view, int position);

    void sendEmail(View view, int position);

    void makeCall(View view, int position);



}