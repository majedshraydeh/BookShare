package com.fikrabd.mehna_w_amal.callbacks;

import android.view.View;

import com.fikrabd.mehna_w_amal.databinding.ItemJobBinding;


public interface JobListener {


    void onAddJob(View view, int position, ItemJobBinding itemJobBinding);

    void onRemoveJob(View view, int position, ItemJobBinding itemJobBinding);}
