package com.mevv.myframe.module.activity;


import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.mevv.myframe.R;
import com.mevv.myframe.common.base.BaseActivity;
import com.mevv.myframe.module.fragment.OneFragment;

/**
 * Created by VV on 2016/10/23.
 */

public class OneFragmentActivity extends BaseActivity {

    private FrameLayout mFrameLayout;

    @Override
    public int bindLayout() {
        return R.layout.fragment_one_activity;
    }

    @Override
    public void init() {
        mFrameLayout = (FrameLayout) findViewById(R.id.fl_one_fragment_activity);
        OneFragment oneFragment = OneFragment.newInstance();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_one_fragment_activity, oneFragment);
        ft.commit();
    }
}
