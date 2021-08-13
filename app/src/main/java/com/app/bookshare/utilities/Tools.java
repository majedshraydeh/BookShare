package com.app.bookshare.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import com.app.bookshare.R;
import com.app.bookshare.app.App;
import com.app.bookshare.conifig.Const;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Tools {

    private static final String TAG = "Tools";

//    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    @SuppressLint("SimpleDateFormat")
    public static Timestamp getTimestamp() {
        SimpleDateFormat simpleDateFormat;
        Date date;
        Date parsedDate;

        Timestamp timestamp = null;

        try {
            simpleDateFormat = new SimpleDateFormat("h:mm a dd MMMM yyyy");
            date = new Date();

            parsedDate = simpleDateFormat.parse(simpleDateFormat.format(date));

            timestamp = new Timestamp(parsedDate);

        } catch (Exception ex) {
            Log.e(TAG, "getTimestamp: " + ex.getMessage());
        }

        return timestamp;
    }

    public static void showMessage(String message) {
        Toast.makeText(App.getAppContext(), message, Toast.LENGTH_LONG).show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSnackBar(Activity activity, String message) {
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void printHashKey(Context pContext) {
        try {
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }

    public  static void setImage(ImageView image, String url, ProgressBar progressBar) {
        if (url != null) {
            Glide.with(App.getAppContext())
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            image.setImageResource(R.drawable.ic_launcher_foreground);
                            if (progressBar != null)
                                progressBar.setVisibility(View.GONE);
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (progressBar != null)
                                progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .fitCenter()
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(Const.CORNER_IMAGE)))
                    .into(image);
        }
    }

    @SuppressLint("CheckResult")
    public  static void setImageWithView(ImageView image, String url, View view)  {
        if (url != null) {
            Glide.with(App.getAppContext())
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            image.setImageResource(R.drawable.user);
                            if (view != null)
                                view.setVisibility(View.GONE);
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (view != null)
                                view.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .fitCenter()
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(Const.CORNER_IMAGE)))
                    .into(image);
        }
    }


    public static void setRating(RatingBar ratingBar, String rate) {
        ratingBar.setRating(Float.parseFloat(rate));
    }

    public static String convertImageToBase64(ImageView imageView) {
        if (imageView == null || imageView.getDrawable() == null) {
            return "";
        }
        String data = "";
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        if (bitmap == null) {
            return "";
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageByteArray = byteArrayOutputStream.toByteArray();
            data = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
            return data;
        }
    }

    public static boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }


    public static int randInt(int min, int max) {

        Random rand = new Random();

        return rand.nextInt((max - min) + 1) + min;
    }


    public static String convertToString(Uri uri){
       String uriString = uri.toString();
        String ansValue="";
        Log.d("data", "onActivityResult: uri"+uriString);

        try {
            InputStream in = getApplicationContext().getContentResolver().openInputStream(uri);
            byte[] bytes=getBytes(in);
            Log.d("data", "onActivityResult: bytes size="+bytes.length);
            Log.d("data", "onActivityResult: Base64string="+Base64.encodeToString(bytes,Base64.DEFAULT));
            ansValue = Base64.encodeToString(bytes,Base64.DEFAULT);


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Log.d("error", "onActivityResult: " + e.toString());
        }

        return  ansValue;
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


}
