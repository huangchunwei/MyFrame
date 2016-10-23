package com.mevv.myframe.api.service;


import com.mevv.myframe.api.Resp;
import com.mevv.myframe.bean.BannerListBean;
import com.mevv.myframe.constants.AppURL;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by VV on 2016/10/14.
 * 返回结果为指定对象
 */

public interface ObjectApi {

    @GET(AppURL.URL_BANNER_LIST)
    Observable<Resp<List<BannerListBean>>> getBannerList(@QueryMap Map<String, String> params);

}
