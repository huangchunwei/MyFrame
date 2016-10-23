package com.mevv.myframe.common.hybrid;

import android.webkit.JavascriptInterface;

import com.mevv.myframe.common.util.TipUtil;

/**
 * Created by VV on 2016/9/12.
 * Js注入接口
 */
public class JSInterface {
    private WebBaseActivity mActivity;

    public JSInterface(WebBaseActivity activity) {
        this.mActivity = activity;
    }

    /**
     * h5 调用native App 统一的方法
     *
     * @param string
     */
    @JavascriptInterface
    public void nativeMethod(final String string) {
        IWebHandler webHandler = new WebHandlerFactory(mActivity).createHybridHandler(WebConstants.CUSTOM_MESSAGE_TASK);
        if (webHandler != null) {
            if (webHandler.handleTask(mActivity, string))
                TipUtil.showToast("处理H5任务成功");
        }else{
            TipUtil.showToast("处理H5任务失败");
        }
    }

    /**
     * h5 调用native App 统一的方法 为重载方法
     *
     * @param code
     */
    @JavascriptInterface
    public void nativeMethod(final int code) {

    }

    /**
     * h5 调用native App 弹Toast方法
     *
     * @param message
     */
    @JavascriptInterface
    public void toastMessage(final String message) {
        TipUtil.showToast(message);
    }
}
