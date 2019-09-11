package com.sagishchori.icnapp.utils;

import android.content.Context;

import com.sagishchori.icnapp.models.UserDetails;

public class UserUtils {

    /**
     * Checks if a user details is saved in the {@link android.content.SharedPreferences}. The check is on both
     * first and last names.
     *
     * @param context
     *
     * @return              true - is user is logged in, false - otherwise
     */
    public static boolean isUserLoggedIn(Context context) {
        String fName = SharedPreferencesUtils.readValueFromSP(context, UserDetails.KEY_FIRST_NAME, "");

        if (StringUtils.isEmpty(fName)) {
            return false;
        }

        String lName = SharedPreferencesUtils.readValueFromSP(context, UserDetails.KEY_LAST_NAME, "");

        if (StringUtils.isEmpty(lName)) {
            return false;
        }

        return true;
    }
}
