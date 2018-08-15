package com.silence.rootfeature.net;

import android.app.ActivityOptions;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.net.ConnectivityManagerCompat;

import com.silence.rootfeature.utils.SystemServiceManager;

/**
 * 网络请求管理类
 * @author violet
 * @date 2018/3/11 12:15
 */

public class NetworkManager {

    private static NetworkManager instance = new NetworkManager();
    private NetworkManager(){

    }
    public static NetworkManager getInstance(){
        return instance;
    }

    public boolean isActiveNetworkMetered(){
        return SystemServiceManager.getConnectivityManager().isActiveNetworkMetered();
    }
}
