package com.mevv.myframe.api.core;


import com.mevv.myframe.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by VV on 2016/9/22.
 */

public class OkHttpFactory {

    private static OkHttpClient sOkHttpClient;

    //设置缓存目录
    private static File cacheDirectory = new File(BaseApplication.getApplication().getCacheDir().getAbsolutePath(), "dataCache");
    private static Cache cache = new Cache(cacheDirectory, 100 * 1024 * 1024);//100M

    //设缓存有效期为3天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 3;

    //查询缓存的Cache-Control设置，使用缓存
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    //查询网络的Cache-Control设置。不使用缓存
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    // 请求log拦截器
    private static HttpLoggingInterceptor mLoggingInterceptor =  new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);

    /**
     * 请求头拦截器
     */
    private static Interceptor sHeaderInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
//                    .addHeader(HEADER_X_HB_Client_Type, FROM_ANDROID)
                    .build();
            return chain.proceed(request);
        }
    };



    public static OkHttpClient createClient() {
        if (sOkHttpClient == null) {
            sOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)//超时时间15秒
                    .addInterceptor(mLoggingInterceptor)//设置请求日志拦截器,release取消掉
//                    .addInterceptor(sHeaderInterceptor)//所有请求头拦截器
                    .cache(cache)//设置请求缓存
                    .build();
        }
        return sOkHttpClient;
    }
}
