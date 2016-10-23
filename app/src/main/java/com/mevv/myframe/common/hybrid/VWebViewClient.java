package com.mevv.myframe.common.hybrid;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by VV on 2016/9/12.
 */
public class VWebViewClient extends WebViewClient {

    private WebBaseActivity act;

    public VWebViewClient(WebBaseActivity act) {
        this.act = act;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // 根据url做相应事件处理
        IWebHandler mHybridHandler = new WebHandlerFactory(act).createHybridHandler(WebConstants.URL_TASK);
        if (mHybridHandler != null) {
            return mHybridHandler.handleTask(act, url);
        }
        //交给父类处理
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
    }


    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        view.stopLoading();
        view.clearView();
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                act.getWebView().loadUrl("404");//TODO 自定义错误处理
            }
        });
    }
}
