package com.mevv.myframe.api.core.api;


import com.mevv.myframe.api.core.VCallback;
import com.mevv.myframe.api.core.subscriber.ProgressSubscriber;
import com.mevv.myframe.api.core.transformer.Transformers;
import com.mevv.myframe.common.util.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;

/**
 * Created by VV on 2016/10/19.
 * Api调度器
 */

public class ApiScheduler {

    public static <T> void applySchedulersForObject(Observable observable, final Object tag, final boolean isShowProgress, final VCallback<T> vCallback) {
        Subscription subscription = observable
                .compose(Transformers.switchSchedulers())
                .compose(Transformers.sTransformer())
                .subscribe(new ProgressSubscriber(tag, isShowProgress, vCallback))
               /* .map(new ServiceRespFunc<T>())//拦截服务器返回的错误码
                .onErrorResumeNext(new HttpRespFunc<T>())//处理服务器返回的错误码 HttpRespFunc 为拦截onError事件的拦截器
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//切换
                .subscribe(new ProgressSubscriber<T>(tag, isShowProgress, vCallback))*/;
        if (tag != null)//添加到API请求队列中
            ApiTaskStack.getInstance().subsEnqueue(subscription, tag);
    }

    public static void applySchedulersForString(Call<String> call, final Object tag, final boolean isShowProgress, final VCallback<String> vCallback) {
        if (isShowProgress)
            LoadingDialog.showLoadingDialog("加载中", tag);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                vCallback.onSuccess(response.body());
                if (isShowProgress)
                    LoadingDialog.dismissLoadingDialog();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                vCallback.onFailure(t);
                if (isShowProgress)
                    LoadingDialog.dismissLoadingDialog();
            }
        });
        if (tag != null)//添加到Api请求队列中
            ApiTaskStack.getInstance().callEnqueue(call, tag);
    }

}
