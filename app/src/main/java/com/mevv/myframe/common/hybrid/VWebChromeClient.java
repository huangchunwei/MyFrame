package com.mevv.myframe.common.hybrid;

import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by VV on 2016/9/12.
 */
public class VWebChromeClient extends WebChromeClient {
    private Context mContext;
    private TitleReceiverListener mTitleReceiverListener;
    private PageChangeListener mPageChangeListener;

    public VWebChromeClient(Context context) {
        this.mContext = context;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (mTitleReceiverListener != null)
            mTitleReceiverListener.onTitleReceiver(view, title);
    }

    @Override
    public void onProgressChanged(final WebView view, int newProgress) {
        if (mPageChangeListener != null)
            mPageChangeListener.onPageChange(view, newProgress);
        super.onProgressChanged(view, newProgress);
    }

    public void setTitleReceiverListener(TitleReceiverListener titleReceiverListener) {
        this.mTitleReceiverListener = titleReceiverListener;
    }

    public void setPageChangeListener(PageChangeListener pageChangeListener) {
        this.mPageChangeListener = pageChangeListener;
    }

    public interface TitleReceiverListener {
        void onTitleReceiver(WebView webView, String title);
    }

    public interface PageChangeListener {
        void onPageChange(WebView view, int progress);
    }
}
