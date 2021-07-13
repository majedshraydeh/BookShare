package com.fikrabd.mehna_w_amal.steppers;


import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;


import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.adapter.StepperAdapter;
import com.fikrabd.mehna_w_amal.base.BaseActivity;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.fikrabd.mehna_w_amal.databinding.ActivitySteppersBinding;

import com.fikrabd.mehna_w_amal.login.LoginActivity;
import com.fikrabd.mehna_w_amal.models.Stepper;
import com.fikrabd.mehna_w_amal.select_type.SelectActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Saif M Jaradat on 2/3/2021.
 */
public class SteppersActivity extends BaseActivity<StepperPresenter, ActivitySteppersBinding> implements StepperInterface.StepperView {

    private static final String TAG = "SteppersActivity";
    private List<Stepper> steppers = new ArrayList<>();
    private StepperAdapter stepperAdapter;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_steppers;
    }

    @Override
    protected StepperPresenter createPresenter() {
        return new StepperPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.getAllSteppers();
    }


    private void initComponent(List<Stepper> steppers) {

        bottomProgressDots(0);

        stepperAdapter = new StepperAdapter();
        viewDataBinding.viewPager.setAdapter(stepperAdapter);
        stepperAdapter.setSteppers(steppers);
        viewDataBinding.viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewDataBinding.next.setOnClickListener(v -> {
            int current = viewDataBinding.viewPager.getCurrentItem() + 1;
            if (current < steppers.size()) {
                viewDataBinding.viewPager.setCurrentItem(current);
            } else {
                openActivityWithFinish(LoginActivity.class);
                SharedPreferencesManager.getInstance().setFirstTime(false);
            }
        });
    }

    private void bottomProgressDots(int current_index) {
        ImageView[] dots = new ImageView[steppers.size()];

        viewDataBinding.layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
            viewDataBinding.layoutDots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.baseColor), PorterDuff.Mode.SRC_IN);
        }
    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);

            if (position == steppers.size() - 1) {
                viewDataBinding.next.setText(getString(R.string.sign_up));
                viewDataBinding.next.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.baseColor)));
                viewDataBinding.next.setTextColor(Color.WHITE);

            } else {
                viewDataBinding.next.setText(getString(R.string.next));
                viewDataBinding.next.setTextColor(getResources().getColor(R.color.grey_90));
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    public void allSteppers(List<Stepper> steppers) {
        this.steppers = steppers;
        initComponent(steppers);
    }

    @Override
    public void noSteppers() {

        openActivity(LoginActivity.class);
    }
}