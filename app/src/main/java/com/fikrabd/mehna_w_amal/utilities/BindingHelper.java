package com.fikrabd.mehna_w_amal.utilities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.app.App;
import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;



public class BindingHelper {

    private static final String TAG = "BindingHelper";

    @BindingAdapter(value = {"image", "progressBar"}, requireAll = false)
    public static void setImage(ImageView imageView, String imageUrl, ProgressBar progressBar) {
        if (imageUrl != null)
            Tools.setImage(imageView, imageUrl, progressBar);
    }

    @BindingAdapter(value = {"imageLoading", "loading"}, requireAll = false)
    public static void setImageLoading(ImageView imageView, String imageUrl, View view) {
        if (imageUrl != null)
            Tools.setImageWithView(imageView, imageUrl, view);
    }

//    @BindingAdapter({"bind:text", "bind:arabicText"})
//    public static void setTextByLanguage(TextView textView, String text, String arabicText) {
//        if (SharedPreferencesManager.getInstance().getLanguage().equals(Const.ENGLISH)) {
//            textView.setText(text);
//        } else {
//            textView.setText(arabicText);
//        }
//    }

    @BindingAdapter({"bind:setTextWithColor"})
    public static void setText(TextView textView, String text) {
        textView.setText(text);
       // textView.setTextColor(Color.parseColor(SharedPreferencesManager.()));
    }
//    @BindingAdapter({"bind:setTextWithPaintFlags"})
//    public static void setText(TextView textView, Item item) {
//        if (item.isOld()) {
//            textView.setText(String.format("%s%s", item.getOldsPrice(), item.getCurrenccy()));
//            textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//
//        } else {
//            textView.setText("");
//
//        }
//    }
    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @BindingAdapter({"bind:setImageColor", "bind:checkImage", "bind:position", "bind:selectedPosition"})
    public static void setImageColor(TextView textView, String color, ImageView imageView, int position, int selectedPosition) {
        textView.setBackgroundResource(R.drawable.shape_circle);
        textView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));

        if (position == selectedPosition) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @BindingAdapter({"bind:setRoundImageColor"})
    public static void setRoundImageColor(TextView textView, String color) {
        textView.setBackgroundResource(R.drawable.shape_circle);
        textView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
    }

    @BindingAdapter({"bind:setRating"})
    public static void setRating(RatingBar ratingBar, String rate) {
        ratingBar.setRating(Float.parseFloat(rate));
       // ratingBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor(SharedPreferencesManager.getInstance().getAppColor())));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @BindingAdapter({"bind:position", "bind:selectedPosition"})
    public static void selectedPosition(TextView textView, int position, int selectedPosition) {
        if (position == selectedPosition) {
            textView.setTextColor(ContextCompat.getColor(App.getAppContext(), R.color.white));
        } else {
            textView.setTextColor(ContextCompat.getColor(App.getAppContext(), R.color.white));
        }
    }

//    @SuppressLint("UseCompatLoadingForDrawables")
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @BindingAdapter({"bind:position", "bind:selectedPosition"})
//    public static void selectedPosition(CardView cardView, int position, int selectedPosition) {
//        if (position == selectedPosition) {
//            cardView.setBackgroundResource(R.drawable.border_shape);
//        } else {
//            cardView.setBackgroundResource(R.color.transparent);
//        }
//    }

    @BindingAdapter({"layout_width"})
    public static void setLayoutWidth(View view, double cutter) {

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) App.getAppContext()
                .getSystemService(Context.WINDOW_SERVICE);
        assert windowManager != null;
        windowManager.getDefaultDisplay().getMetrics(metrics);

        layoutParams.width = (int) ((metrics.widthPixels) * cutter);
        view.setLayoutParams(layoutParams);
    }
}
