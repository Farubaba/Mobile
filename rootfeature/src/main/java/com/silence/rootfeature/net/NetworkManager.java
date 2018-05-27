package com.silence.rootfeature.net;

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

}
