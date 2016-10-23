package com.mevv.myframe.api.core.api;


import com.mevv.myframe.api.core.VCallback;
import com.mevv.myframe.common.util.NetworkUtil;
import com.mevv.myframe.common.util.TipUtil;

import retrofit2.Call;
import rx.Observable;

/**
 * Created by VV on 2016/10/14.
 * API管理
 */

public class Api {

    private static final String TAG = "Api";

    public static <T> void getObject(Observable observable, final Object tag, final boolean isShowProgress, final VCallback<T> vCallback) {
        if (!NetworkUtil.isNetworkEnable()) {
            TipUtil.showToast("无网络可用.");
            return;
        }
        ApiScheduler.applySchedulersForObject(observable, tag, isShowProgress, vCallback);
    }

    public static void getString(Call<String> call, final Object tag, final boolean isShowProgress, final VCallback<String> vCallback) {
        if (!NetworkUtil.isNetworkEnable()) {
            TipUtil.showToast("无网络可用.");
            return;
        }
        ApiScheduler.applySchedulersForString(call, tag, isShowProgress, vCallback);
    }

}
