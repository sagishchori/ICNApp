package com.sagishchori.icnapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.sagishchori.icnapp.R;

public class SharedPreferencesUtils {

    SharedPreferences sharedPref;
    Context context;

    public static SharedPreferences getUserDetailsSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public static void writeValueToSP(Context context, String key, String value) {
        SharedPreferences.Editor editor = getUserDetailsSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String readValueFromSP(Context context, String key, String defaultValue) {
        return getUserDetailsSharedPreferences(context).getString(key, defaultValue);
    }

    /**
     * Clears the whole data stored on {@link SharedPreferences}
     *
     * @param context
     */
    public static void clearSharedPreferences(Context context) {
        getUserDetailsSharedPreferences(context).edit().clear().commit();
    }
}
