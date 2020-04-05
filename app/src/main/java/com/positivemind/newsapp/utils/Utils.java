package com.positivemind.newsapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rajeev Tomar on 21,December,2019
 */
public class Utils {


    public static final String YMD_FORMAT = "yyyy-MM-dd";
    public static final String YMDMT_FORMAT ="yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static String getFormattedDate(String fromPattern, String toPattern, String dateStr)
    {
        try
        {
            SimpleDateFormat fromFormat = new SimpleDateFormat(fromPattern);
            SimpleDateFormat toFormat = new SimpleDateFormat(toPattern);
            Date date = fromFormat.parse(dateStr);
           return  toFormat.format(date);
        }
        catch (Exception pse)
        {

        }
      return null;
    }

    public static boolean isDeviceHasInternetConnection(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
