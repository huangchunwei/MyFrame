package com.mevv.myframe;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.mevv.myframe.common.util.ActManager;

/**
 * Created by VV on 2016/9/23.
 */

public class BaseApplication extends Application {
    private static Handler sMainHandler;
    private static Looper sMainLooper;
    private static Thread sMainThread;
    private static int sMainThreadId;

    private static BaseApplication sBaseApplication;

    @Override
    public void onCreate() {
//        MultiDex.install(this);
        super.onCreate();
        sBaseApplication = this;
        //内存泄露检测
//        LeakCanary.install(this);
        initConfiguration();
        Log.i("hate", "应用已启动.......");
//        Intent intent = new Intent(sBaseApplication, AppService.class);
//        startService(intent);
    }

    private void initConfiguration() {
        initConstants();
//        LogUtil.isDebug = true;
//        TipUtil.isShow = true;
//        配置程序异常退出处理
//        Thread.setDefaultUncaughtExceptionHandler(new LocalExceptionHelper(this));
    }

    private void initConstants() {
        BaseApplication.sMainHandler = new Handler();
        BaseApplication.sMainLooper = getMainLooper();
        BaseApplication.sMainThread = Thread.currentThread();
        BaseApplication.sMainThreadId = android.os.Process.myTid();


    }

    public static BaseApplication getApplication() {
        return sBaseApplication;
    }

    public static Handler getsMainHandler() {
        return sMainHandler;
    }

    public static Looper getsMainLooper() {
        return sMainLooper;
    }

    public static Thread getsMainThread() {
        return sMainThread;
    }

    public static int getsMainThreadId() {
        return sMainThreadId;
    }


    @Override
    protected void attachBaseContext(Context base) {
//        MultiDex.install(this);
        super.attachBaseContext(base);
    }

    public void exit() {
        ActManager.getInstances().finishAll();
//        System.exit(0);
    }
}
