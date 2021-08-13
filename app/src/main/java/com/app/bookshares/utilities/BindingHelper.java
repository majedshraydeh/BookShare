package com.app.bookshares.utilities;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.databinding.BindingAdapter;
import com.app.bookshares.app.App;


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
