package com.silence.rootfeature.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.silence.rootfeature.app.AppUtil;

/**
 * @author violet
 * @date 2018/6/23 15:03
 */

public class SystemServiceManager {

    public static ConnectivityManager getConnectivityManager(){
        //api 23 can use :  AppUtil.getAppContext().getSystemService(ConnectivityManager.class);
        ConnectivityManager connectivityManager = (ConnectivityManager) AppUtil.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager;
    }
}
