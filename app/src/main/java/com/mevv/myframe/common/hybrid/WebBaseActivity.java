package com.mevv.myframe.common.hybrid;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;

import com.mevv.myframe.BuildConfig;
import com.mevv.myframe.R;
import com.mevv.myframe.common.base.BaseActivity;
import com.mevv.myframe.common.util.UIUtils;

import java.lang.reflect.Field;
import java.util.HashMap;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by VV on 2016/10/17.
 */

public abstract class WebBaseActivity extends BaseActivity implements View.OnClickListener {
    protected String url;
    protected WebView mWebView;
    protected String mFinalTitle;
    //维护整个webview交互处理事件的WebHandler对象
    private HashMap<String, IWebHandler> mWebHandlerHashMap = new HashMap<>();
    private ProgressBar progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        initViews();
        initEvents();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_base_web_activity;
    }

    @Override
    public void init() {
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    private void initViews() {
        mWebView = (WebView) findViewById(R.id.base_webView);
        initWebView();
        setActivityTitle(setTitle());
        //web设置加载进度条
        progressbar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        ClipDrawable d = new ClipDrawable(new ColorDrawable(this.getResources().getColor(R.color.colorPrimary))
                , Gravity.LEFT,
                ClipDrawable.HORIZONTAL);
        progressbar.setProgressDrawable(d);
        progressbar.setLayoutParams(new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.MATCH_PARENT, UIUtils.dip2px(this, 2), 0, 0));
        mWebView.addView(progressbar);
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.setInitialScale(80);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.getSettings().setJavaScriptEnabled(true);//启用Js支持
        mWebView.addJavascriptInterface(new JSInterface(this), "app");//自定义Js注入接口
        mWebView.loadUrl(setUrl());
        VWebChromeClient client = new VWebChromeClient(this);
        //设置加载进度监听
        client.setPageChangeListener(new VWebChromeClient.PageChangeListener() {
            @Override
            public void onPageChange(WebView view, int progress) {
                if (progress == 100) {
                    progressbar.setVisibility(GONE);
                } else {
                    if (progressbar.getVisibility() == GONE)
                        progressbar.setVisibility(VISIBLE);
                    progressbar.setProgress(progress);
                }
            }
        });
        //设置获取title监听
        client.setTitleReceiverListener(new VWebChromeClient.TitleReceiverListener() {
            @Override
            public void onTitleReceiver(WebView webView, String title) {
                setWebReceiveTitle(title);
            }
        });
        mWebView.setWebChromeClient(client);
        mWebView.setWebViewClient(new VWebViewClient(this));
    }

    protected abstract void getUrlAndData();

    protected abstract String setUrl();

    protected abstract String setTitle();

    private void initEvents() {
    }

    /**
     * 设置Activity标题
     *
     * @param title
     */
    protected void setActivityTitle(String title) {
        if (title == null)
            return;
        this.mFinalTitle = title;
    }

    /**
     * 设置Web返回的标题,如果已经手动设置过标题则不覆盖
     *
     * @param title
     */
    public void setWebReceiveTitle(String title) {
        if (TextUtils.isEmpty(mFinalTitle)) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    /**
     * app 调用 js 代码
     *
     * @param url
     */
    public void App2JsCode(String url) {
        mWebView.loadUrl(url);
    }

    public WebView getWebView() {
        return mWebView;
    }

    public HashMap<String, IWebHandler> getWebHandlerMap() {
        if (mWebHandlerHashMap == null) {
            mWebHandlerHashMap = new HashMap<>();
        }
        return mWebHandlerHashMap;
    }

    public void addHybridHandler(String evenName, IWebHandler webHandler) {
        if (mWebHandlerHashMap != null) {
            mWebHandlerHashMap.put(evenName, webHandler);
        }
    }

    @Override
    public void finish() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.setVisibility(GONE);
            mWebView.removeAllViews();
            mWebView.destroy();
            releaseAllWebViewCallback();
        }
        super.onDestroy();
    }

    /**
     * 释放WebView资源,避免内存泄漏
     */
    private void releaseAllWebViewCallback() {
        if (android.os.Build.VERSION.SDK_INT < 16) {
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }
}
