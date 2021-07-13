package com.fikrabd.mehna_w_amal.conifig;

import android.annotation.SuppressLint;

import android.content.SharedPreferences;
import android.util.Log;

import com.fikrabd.mehna_w_amal.app.App;
import com.fikrabd.mehna_w_amal.models.User;
import com.google.gson.Gson;

import static com.fikrabd.mehna_w_amal.conifig.Const.APP_SETTINGS;



public class SharedPreferencesManager {

    private static final String TAG = "SharedPreferencesManage";

    private static SharedPreferencesManager sharedPreferencesManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    private SharedPreferencesManager() {
        try {
            sharedPreferences = App.getAppContext().getSharedPreferences(APP_SETTINGS, Const.PRIVATE_MODE);
            editor = sharedPreferences.edit();
        } catch (Exception e) {
            Log.e(TAG, "SharedPreferencesManager: ", e);
        }
    }

    public static SharedPreferencesManager getInstance() {
        if (sharedPreferencesManager == null) {
            sharedPreferencesManager = new SharedPreferencesManager();
        }
        return sharedPreferencesManager;
    }



    public void setPhone(String phone) {
        editor.putString(Const.PHONE, phone);
        editor.commit();
    }

    public String getPhone() {
        return sharedPreferences.getString(Const.PHONE,"");
    }

    public void setEmail(String email) {
        editor.putString(Const.EMAIL, email);
        editor.commit();
    }

    public String getEmail() {
        return sharedPreferences.getString(Const.EMAIL,"");
    }



    public void setUserType(String userType) {
        editor.putString(Const.USER_TYPE, userType);
        editor.commit();
    }

    public String getUserType() {
        return sharedPreferences.getString(Const.USER_TYPE,"");
    }





    public void setUserID(String userID) {
        if (userID != null)
            editor.putString(Const.USER_ID, userID);

        editor.commit();
    }

    public String getUserID() {
        return sharedPreferences.getString(Const.USER_ID, Const.NO_USER);
    }

    public void setUser(User user) {
        if (user != null) {
            Gson gson = new Gson();
            String jsonUser = gson.toJson(user);
            editor.putString(Const.USER, jsonUser);
        }
        editor.commit();
    }

    public User getUser() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Const.USER, Const.NO_USER);

        User user = null;
        try {
            user = gson.fromJson(json, User.class);
        } catch (Exception ex) {
            new User();
        }

        return user;
    }

    public void setCreate(boolean create) {
        editor.putBoolean(Const.IS_CREATE, create);
        editor.commit();
    }

    public boolean isCreate() {
        return sharedPreferences.getBoolean(Const.IS_CREATE, false);
    }

    public void setLoggedIn(boolean loggedIn) {
        editor.putBoolean(Const.IS_LOGGED_IN, loggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(Const.IS_LOGGED_IN, false);
    }



    public void setLoginBy(String loginBy) {
        if (loginBy != null)
            editor.putString(Const.LOGIN_BY, loginBy);

        editor.commit();
    }

    public String getLoginBy() {
        return sharedPreferences.getString(Const.LOGIN_BY, Const.NO_USER);
    }



    public void resetFields() {
        editor.remove(Const.FCM_TOKEN);
        editor.remove(Const.USER);
        editor.remove(Const.IS_LOGGED_IN);
        editor.remove(Const.LOGIN_BY);
        editor.remove(Const.IS_CREATE);
        editor.commit();
    }

    public void setLanguage(String language) {
        if (language != null) {
            editor.putString(Const.LANGUAGE, language);
        }
        editor.commit();
    }

    public String getLanguage() {
        return sharedPreferences.getString(Const.LANGUAGE, Const.ENGLISH);
    }

    public boolean isFirstTime() {
        return sharedPreferences.getBoolean(Const.IS_FIRST_TIME, true);
    }

    public void setFirstTime(boolean isFirstTime) {
        editor.putBoolean(Const.IS_FIRST_TIME, isFirstTime);
        editor.commit();
    }




}