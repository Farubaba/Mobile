package com.silence.rootfeature.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IntDef;

import com.silence.rootfeature.app.AppUtil;
import com.silence.rootfeature.logger.LogManager;


/**
 * @author violet
 * date :  2018/7/5 11:34
 */

public class NetworkUtil {

    @IntDef({ConnectType.TYPE_BLUETOOTH, ConnectType.TYPE_DUMMY, ConnectType.TYPE_ETHERNET, ConnectType.TYPE_MOBILE, ConnectType.TYPE_MOBILE_DUN, ConnectType.TYPE_VPN, ConnectType.TYPE_WIFI, ConnectType.TYPE_WIMAX})
    public @interface ConnectType {

        int TYPE_BLUETOOTH = ConnectivityManager.TYPE_BLUETOOTH;
        int TYPE_WIFI = ConnectivityManager.TYPE_WIFI;
        int TYPE_MOBILE = ConnectivityManager.TYPE_MOBILE;
        int TYPE_MOBILE_DUN = ConnectivityManager.TYPE_MOBILE_DUN;
        int TYPE_DUMMY = ConnectivityManager.TYPE_DUMMY;
        int TYPE_ETHERNET = ConnectivityManager.TYPE_ETHERNET;
        int TYPE_VPN = ConnectivityManager.TYPE_VPN;
        int TYPE_WIMAX = ConnectivityManager.TYPE_WIMAX;
    }

    /**
     * TODO 判断网络状态是否可用
     *
     * @return true: 网络可用 ; false: 网络不可用
     */
    public static boolean hasInternet() {
        ConnectivityManager m = (ConnectivityManager) AppUtil.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (m == null) {
            LogManager.getInstance().d("NetWorkState", "Unavailabel");
            return false;
        } else {
            NetworkInfo[] info = m.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        LogManager.getInstance().d("NetWorkState", "Availabel");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断当前网络是否是付费网络
     *
     * @return
     */
    public static boolean isActiveNetworkMetered() {
        ConnectivityManager m = (ConnectivityManager) AppUtil.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return m.isActiveNetworkMetered();
    }

    /**
     * @return 是否是wifi网络
     */
    public static boolean isWifi() {
        ConnectivityManager m = (ConnectivityManager) AppUtil.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo n = m.getActiveNetworkInfo();
        return (n != null && n.getType() == ConnectivityManager.TYPE_WIFI);
    }


}
