package com.essam.youtubeplayer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ProjectUtils {

    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    public static String representStatistic(int num){
        String result = String.valueOf(num);
        if (num / 1000 >= 1 && num / 1000000 < 1){
            result = (num / 1000)+"K";
        }else if(num/1000000>=1){
            result = (num / 1000000)+"M";
        }
        return result;
    }
}
