package com.fikrabd.mehna_w_amal.callbacks;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Saif M Jaradat on 2/3/2021.
 */
public interface ItemClickListener {
    void onItemClick(RecyclerView.Adapter adapter, View view, int position);
}
