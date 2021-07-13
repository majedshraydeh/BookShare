package com.fikrabd.mehna_w_amal.conifig;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

/**
 * Created by Saif M Jaradat on 2/3/2021.
 */
public class LocaleHelper {
    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    private static final String TAG = "LocaleHelper";

    public static Context onAttach(Context context) {
        String lang = getPersistedData();
        return setLocale(context, lang);
    }

    public static Context setLocale(Context context, String language) {
        persist(language);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }

        return updateResourcesLegacy(context, language);
    }

    private static String getPersistedData() {
        return SharedPreferencesManager.getInstance().getLanguage();
    }

    private static void persist(String language) {
        SharedPreferencesManager.getInstance().setLanguage(language);
    }


    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale;
        locale = new Locale(language);
        Configuration config = new Configuration(context.getResources().getConfiguration());
        Locale.setDefault(locale);
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        return context.createConfigurationContext(config);
    }

    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }
}


