package com.mevv.myframe.common.hybrid;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.mevv.myframe.common.util.TipUtil;

/**
 * Created by VV on 2016/10/13.
 */

public class CustomJSInterface {
    private Context mContext;


    public CustomJSInterface(Context context) {
        mContext = context;
    }

    /**
     * html5 调用native App 统一的方法
     *
     * @param string
     */
    @JavascriptInterface
    public void nativeHanderTask(final String string) {
    }

    @JavascriptInterface
    public void toastMessage(String message) {//Toast接口
        TipUtil.showToast(message);
    }
}
