package com.mevv.myframe.api.core.func;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by VV on 2016/10/19.
 */

public class HttpRespFunc<T> implements Func1<Throwable, Observable<T>> {
    @Override
    public Observable<T> call(Throwable throwable) {
        //ExceptionEngine为处理异常的驱动器
        return Observable.error(throwable);
    }
}
