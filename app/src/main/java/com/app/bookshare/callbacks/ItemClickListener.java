package com.app.bookshare.callbacks;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemClickListener {
    void onItemClick(RecyclerView.Adapter adapter, View view, int position);
}
