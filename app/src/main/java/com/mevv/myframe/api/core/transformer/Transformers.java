package com.mevv.myframe.api.core.transformer;


import com.mevv.myframe.api.core.exception.ApiException;
import com.mevv.myframe.api.Resp;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by VV on 2016/10/19.
 */

public class Transformers {

    public static <T> Observable.Transformer<Resp<T>, T> sTransformer() {

        return new Observable.Transformer<Resp<T>, T>() {
            @Override
            public Observable<T> call(Observable<Resp<T>> respObservable) {

                return respObservable
                        /*.map(new ServiceRespFunc<T>()) new HttpRespFunc()
                        .onErrorResumeNext(new HttpRespFunc<T>());*/

                        .flatMap(new Func1<Resp<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Resp<T> tResp) {
                                if (!tResp.isStatus())
                                    throw new ApiException(tResp.getErrcode(), tResp.getInfo());
                                return Observable.just(tResp.getData());
                            }
                        })
                        .onErrorResumeNext(new Func1<Throwable, Observable<T>>() {
                            @Override
                            public Observable<T> call(Throwable throwable) {
                                return Observable.error(throwable);
                            }
                        });
            }
        };
    }

    public static <T> Observable.Transformer<T, T> switchSchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
