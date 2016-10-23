package com.mevv.myframe.common.base;

import com.mevv.myframe.common.hybrid.WebBaseActivity;

/**
 * Created by VV on 2016/10/17.
 * 新的通用WebActivity
 */

public class CommonWebActivity extends WebBaseActivity {

    public static final String WEB_URL = "url";
    public static final String WEB_TITLE = "title";
    private String mTitle;
    private String mUrl;


    @Override
    protected void getUrlAndData() {
        mTitle = getIntent().getStringExtra(WEB_TITLE);
        mUrl = getIntent().getStringExtra(WEB_URL);
    }

    @Override
    protected String setUrl() {
        return mUrl;
    }

    @Override
    protected String setTitle() {
        return mTitle;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
