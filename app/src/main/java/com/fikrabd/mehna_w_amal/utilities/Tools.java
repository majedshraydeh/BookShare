package com.fikrabd.mehna_w_amal.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import com.fikrabd.mehna_w_amal.R;
import com.fikrabd.mehna_w_amal.app.App;
import com.fikrabd.mehna_w_amal.conifig.Const;
import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Random;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Tools {

    private static final String TAG = "Tools";

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

    public static void setImage(ImageView image, String url, ProgressBar progressBar) {
        if (url != null) {
            Glide.with(App.getAppContext())
                    .load(Const.IMAGE_URL + url)
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
    public static void setImageWithView(ImageView image, String url, View view)  {
        if (url != null) {
            Glide.with(App.getAppContext())
                    .load(Const.IMAGE_URL + url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .disallowHardwareConfig()
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            image.setImageResource(R.drawable.ic_launcher_foreground);
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

    public static void setTextWithColor(TextView textView, String text) {
        textView.setText(text);
       // textView.setTextColor(Color.parseColor(SharedPreferencesManager.getInstance().getAppColor()));
    }



//    @SuppressLint("SetTextI18n")
//    public static void setTextWithPaintFlags(TextView textView, Item item) {
//
//        if (item.isOld()) {
//            textView.setText(item.getOldsPrice() + item.getCurrenccy());
//            textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//
//        } else {
//            textView.setText("");
//        }
//    }

//    @SuppressLint("SetTextI18n")
//    public static void setTextWithPaintFlags(TextView textView, Search search) {
//
//        if (search.isOld()) {
//            textView.setText(search.getOldsPrice() + search.getCurrenccy());
//            textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//
//        } else {
//            textView.setText("");
//        }
//    }


    public static boolean isFullNameValid(TextInputLayout txt, String error){
        if (txt.getEditText().getText().toString().length()<3){
            txt.setErrorEnabled(true);
            txt.setError(error);
            return false;
        }else{
            txt.setErrorEnabled(false);
            return true;
        }
    }

//    @SuppressLint("SetTextI18n")
//    public static void setTextWithPaintFlags(TextView textView, Variation variation) {
//
//        if (variation.isOld()) {
//            textView.setText(variation.getOldsPrice() + variation.getCurrenccy());
//            textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//
//        } else {
//            textView.setText("");
//        }
//
//    }
    public static void setRating(RatingBar ratingBar, String rate) {
        ratingBar.setRating(Float.parseFloat(rate));
        //ratingBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor(SharedPreferencesManager.getInstance().getAppColor())));
    }






    public static String calculateAge(String total) {
        int year = Calendar.getInstance().get(Calendar.YEAR);

        int result = year - Integer.parseInt(total);

        return String.valueOf(result);
    }

    public static boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }

        return true;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getBase64FromPath(String path)  {

        byte[] bytesInput = path.getBytes();

        System.out.println("URL : "+path);

        //Encode URL to Base64
        java.util.Base64.Encoder base64Encoder = java.util.Base64.getUrlEncoder();
        String encodedString = base64Encoder.encodeToString(bytesInput);
        System.out.println("Encoded URL :" + encodedString);
//        String base64 = "";
//        byte[] helloBytes = hello.getBytes(StandardCharsets.UTF_8);
//        String encodedHello = DatatypeConverter.printBase64Binary(helloBytes);
//        File file = new File(path);
//        byte[]encoded = Base64Utils..encodeBase64(FileUtils.readFileToByteArray(file));
//        try(FileInputStream fileInputStream=) {
//            File file = new File(path);
//            byte[] buffer = new byte[(int) file.length() + 100];
//            @SuppressWarnings("resource")
//
//            int length = new FileInputStream(file).read(buffer);
//            base64 = Base64.encodeToString(buffer, 0, length,
//                    Base64.DEFAULT);
//            Log.e("Path",base64);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return encodedString;
    }

//    public static String encodeFileToBase64Binary(File yourFile) {
//        int size = (int) yourFile.length();
//        byte[] bytes = new byte[size];
//        try {
//            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(yourFile));
//            buf.read(bytes, 0, bytes.length);
//            buf.close();
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        String encoded = Base64.encodeToString(bytes,Base64.NO_WRAP);
//        return encoded;
//    }


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

    public static void nestedScrollTo(final NestedScrollView nested, final View targetView) {
        nested.post(new Runnable() {
            @Override
            public void run() {
                nested.scrollTo(500, targetView.getBottom());
            }
        });
    }

    public static boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    public static String insertPeriodically(String text, String insert, int period) {
        StringBuilder builder = new StringBuilder(text.length() + insert.length() * (text.length() / period) + 1);
        int index = 0;
        String prefix = "";
        while (index < text.length()) {
            builder.append(prefix);
            prefix = insert;
            builder.append(text.substring(index, Math.min(index + period, text.length())));
            index += period;
        }
        return builder.toString();
    }

    public static void setSystemBarColor(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //window.setStatusBarColor(Color.parseColor(SharedPreferencesManager.getInstance().getAppColor()));
        }
    }

    public static void setSystemBarColor(Activity act, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }


    public static void setSystemBarLight(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = act.findViewById(android.R.id.content);
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
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
