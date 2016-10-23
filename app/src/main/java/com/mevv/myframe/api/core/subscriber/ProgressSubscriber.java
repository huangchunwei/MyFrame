package com.mevv.myframe.api.core.subscriber;


import com.mevv.myframe.api.core.VCallback;
import com.mevv.myframe.api.core.exception.ApiException;
import com.mevv.myframe.api.core.exception.ErrorEngine;
import com.mevv.myframe.common.util.LoadingDialog;

import rx.Subscriber;

/**
 * Created by VV on 2016/10/17.
 */

public class ProgressSubscriber<T> extends Subscriber<T> {

    private VCallback<T> mVCallback;
    private boolean mIsShowProgress = true;//默认显示加载进度
    private Object mTag;//任务tag

    public ProgressSubscriber(Object tag, boolean isShowProgress, VCallback<T> vCallback) {
        this.mVCallback = vCallback;
        this.mIsShowProgress = isShowProgress;
        this.mTag = tag;
    }

    @Override
    public void onStart() {
        if (mIsShowProgress)
            LoadingDialog.showLoadingDialog("加载中", mTag);
    }

    @Override
    public void onCompleted() {
        if (mIsShowProgress)
            LoadingDialog.dismissLoadingDialog();
    }

    @Override
    public void onError(Throwable e) {
        mVCallback.onFailure(e);
        if (!(e instanceof ApiException)){
            ErrorEngine.getInstance().processError(ErrorEngine.SERVICE_EXCEPTION, e.getMessage());
        }
        if (mIsShowProgress)
            LoadingDialog.dismissLoadingDialog();
    }

    @Override
    public void onNext(T t) {
        if (mVCallback != null)
            mVCallback.onSuccess(t);
    }
}
