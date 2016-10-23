package com.mevv.myframe.common.hybrid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.webkit.JsResult;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mevv.myframe.R;
import com.mevv.myframe.common.util.UIUtils;


/**
 * Created by VV on 2016/9/12.
 * 带进度条的WebView
 */
public class ProgressWebView extends WebView {
    private ProgressBar progressbar;
    private TitleReceiverListener mListener;
    private WebRedirectListener mRedirectListener;
    private WebInterceptListener webIntercepListener;
    private WebHBShareListener mWebHBShareListener;
    private Context mContext;
    private int house;
    private long starttime;

    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.webViewStyle);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        starttime= System.currentTimeMillis();
        init();
    }

    public void setTitleReceiverListener(TitleReceiverListener listener) {
        this.mListener = listener;
    }


    public void setmRedirectListener(WebRedirectListener mRedirectListener) {
        this.mRedirectListener = mRedirectListener;
    }

    public void setWebIntercepListener(WebInterceptListener webIntercepListener) {
        this.webIntercepListener = webIntercepListener;
    }

    public void setWebHBShareListener(WebHBShareListener webHBShareListener) {
        mWebHBShareListener = webHBShareListener;
    }

    private void init() {
        progressbar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        ClipDrawable d = new ClipDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.colorPrimary))
                , Gravity.LEFT,
                ClipDrawable.HORIZONTAL);
        progressbar.setProgressDrawable(d);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, UIUtils.dip2px(mContext, 2), 0, 0));
        addView(progressbar);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new WebViewClient(){

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(final WebView view, WebResourceRequest request) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        String viewTitle = view.getTitle();
                        mListener.onTitleReceiver(view,viewTitle);
//                        System.out.println("-- viewTitle:"+viewTitle);
                    }
                });

                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {//拦截超链接


                    return super.shouldOverrideUrlLoading(view,url);
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
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
        });
    }


    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE) {
                    progressbar.setVisibility(VISIBLE);
                }
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }


        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (mListener != null) {
                mListener.onTitleReceiver(view,title);
                System.out.println("-- receive title:"+title);
            }

            super.onReceivedTitle(view, title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public interface TitleReceiverListener{
        void onTitleReceiver(WebView webView, String title);
    }

    public interface WebRedirectListener{
        void onWebRedirect(String hid);

        void onOpenHBAtc(String url);
    }

    public interface WebInterceptListener {
        void onCloseHBAtc();
    }

    public interface WebHBShareListener{
        void onHBShare(String url);
    }

}
