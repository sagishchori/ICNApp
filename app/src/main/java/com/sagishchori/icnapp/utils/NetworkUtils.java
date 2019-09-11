package com.sagishchori.icnapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    /**
     * A method to get a reference ti {@link ConnectivityManager}.
     *
     * @param context
     *
     * @return              The {@link ConnectivityManager}
     */
    public static ConnectivityManager getCM(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager;
    }

    /**
     * Use this to check weather the user is connected to internet.
     *
     * @param context
     *
     * @return              true - if connected, false - otherwise
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = getCM(context).getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
