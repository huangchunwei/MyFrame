package com.mevv.myframe.common.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.view.WindowManager;

import com.mevv.myframe.R;
import com.mevv.myframe.common.util.ActManager;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by VV on 2016/10/22.
 */

public abstract class BaseSwipeBackActivity extends SwipeBackActivity {
    private SwipeBackLayout mSwipeBackLayout;
    private Context mContext;
    //    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarTransparent();
        ActManager.getInstances().add(this);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL,EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        //不能竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        this.mContext = this;
        getIntentData();
        setContentView(bindLayout());
//        mUnbinder = ButterKnife.bind(this);
//        EventBus.getDefault().register(this);
        init();
    }

    public abstract int bindLayout();

    public abstract void init();

    public void getIntentData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //TODO 自定义界面关闭动画
        overridePendingTransition(0, R.anim.b_exit_anim);
    }

     /*@Subscribe
    public void onEvent(EventObject eo) {
    }*/

    public void setStatusBarColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //先清除状态栏的透明化
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //在请求设置状态栏颜色标志
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            getWindow().setStatusBarColor(color);
            //底部导航栏
//            getWindow().setNavigationBarColor(this.getResources().getColor(R.color.colorAccent));
        }
    }

    /**
     * 状态栏透明
     */
    public void statusBarTransparent() {
        // API 19开始，安卓支持沉浸式项栏
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            // 为适配沉浸式顶栏,在写XML布局时,在顶部的控件加上这两个属生
            // android:fitsSystemWindows="true"
            // android:clipToPadding="true"
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏不要打开,用虚拟导航按钮的手机会有问题
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 退出透明状态栏夫恢复至Theme状态
     */
    public void quitTransparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //先清除状态栏的透明化
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //在请求设置状态栏颜色标志
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            //底部导航栏
//            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    /**
     * 设置全屏
     */
    public void setFullScreen() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(params);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 退出全屏
     */
    public void quitFullScreen() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(params);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if (mUnbinder != null)
            mUnbinder.unbind();
        EventBus.getDefault().unregister(this);*/
    }
}
