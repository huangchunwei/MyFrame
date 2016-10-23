package com.mevv.myframe.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mevv.myframe.BaseApplication;


/**
 * Created by VV on 2016/9/28.
 */

public class NetworkUtil {
    public static int NETWORK_ERROR = -16;
    public static int NETWORK_NULL = -18;



    /**
     * 网络是否可用
     *
     * @return
     */
    public static boolean isNetworkEnable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) { //判断网络连接是否打开
            return mNetworkInfo.isConnected();
        }
        return false;
    }

    public static boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected())
            return true;
        return false;
    }

}
