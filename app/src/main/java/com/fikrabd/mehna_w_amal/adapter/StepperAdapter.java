package com.fikrabd.mehna_w_amal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;


import com.fikrabd.mehna_w_amal.databinding.ItemStepperBinding;
import com.fikrabd.mehna_w_amal.models.Stepper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StepperAdapter extends PagerAdapter {


    private List<Stepper> steppers;


    public void setSteppers(List<Stepper> steppers) {
        this.steppers = steppers;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
        ItemStepperBinding itemStepperBinding = ItemStepperBinding.inflate(layoutInflater, container, false);

        itemStepperBinding.setStepper(steppers.get(position));

        container.addView(itemStepperBinding.getRoot());

        return itemStepperBinding.getRoot();
    }

    @Override
    public int getCount() {
        if (steppers == null) {
            return 0;
        }
        return steppers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}