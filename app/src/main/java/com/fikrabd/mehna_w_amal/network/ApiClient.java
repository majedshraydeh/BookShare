package com.fikrabd.mehna_w_amal.network;


import com.fikrabd.mehna_w_amal.conifig.SharedPreferencesManager;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fikrabd.mehna_w_amal.conifig.Const.BASE_URL;
import static com.fikrabd.mehna_w_amal.conifig.Const.REQUEST_TIMEOUT;


/**
 * Created by Saif M Jaradat on 2/3/2021.
 */
public class ApiClient {

    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient;

    public static Retrofit getClient() {
        if (okHttpClient == null)
            initOkHttp();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(getBaseURL())
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    private static void initOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("lang", SharedPreferencesManager.getInstance().getLanguage())
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Request-Type", "Android")
                    .addHeader("Content-Type", "application/json")
                    .url(url);


            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        okHttpClient = httpClient.build();
    }

    public static void resetApiClient() {
        retrofit = null;
        okHttpClient = null;
    }

    private static String getBaseURL() {
        return BASE_URL;
    }
}
